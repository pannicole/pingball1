package Pingball;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * PingballServer represents the server on which Pingball games are played. Clients can connect to this server. 
 * The server is connected to regular clients through a NetworkClient. That is, the server stores NetworkClients,
 * which can pass messages through their sockets to PingballClients. Messaging passing is used to communicate
 * between the server, the NetworkClients, and the PingballClients.The server, we store a few things - a list
 * of the NetworkClients, a map that maps names of NetworkClients to NetworkClients, and finally, a map that maps
 * NetworkClient names to maps of Integers to NetworkClient names. This is useful because we want NetworkClient to
 * map to up to 4 different NetworkClient names which represent the four boards that the NetworkClient may be
 * connected to. A better implementation may be to use an enumerated type instead of integers between 1 and 4 though.
 * 
 * The server reads in user input for joining boards together,
 * 
 * The Server is not complete as of now.
 * Currently, the server is not fully able to join boards together. There appears to be an issue with communicating
 * between PingballClient and NetworkClient through socket.getInputStream() and socket.getOutputStream(). This is
 * discussed more in those classes.
 * Currently, when a user disconnects, the action of the server has not been implemented yet.
 * 
 * THREAD SAFETY ARGUMENT: The server stores data that is shared by all NetworkClients, as all NetworkClients store
 * an instance of PingballServer. However, every single NetworkClient that can access PingballServer modifies it
 * through one of two ways - updateName and passBall. These methods do not run into any problems with interleaving,
 * as updateName just adds a name to a dictionary, while passBall just calls a method of one of the stored NetworkClients.
 * There are no concurrency issues here. If the "disconnect" feature was fully implemented, then it would be prudent
 * to lock on the Server or the map that connects NetworkClient names. This is because then it is possible, for example,
 * in passBall, for a NetworkClient to be found and then for it to disconnect, only for a ball to be sent toward it. Thus,
 * it is best to lock the method on the server (which won't deadlock because these methods will occur very quickly
 * and everything only needs this one lock). Thus, I have synchronized them here just in case.
 * All other data is not shared by different threads, so they are confined and thus threadsafe.
 */
public class PingballServer {
    private final ServerSocket serverSocket;
    private static final int DEFAULTPORT = 10987; // default port
    private static final int PORTMAX = 65535;
    private final Map<String, Map<Integer, String>> linkages;
    private final Map<String, NetworkClient> names;
    private final List<NetworkClient> netClients;
    
    private final int LEFT = 1;
    private final int TOP  = 2;
    private final int RIGHT = 3;
    private final int BOTTOM = 4;
  //Note for future adjustments:
  //1 = left
  //2 = top
  //3 = right
  //4 = bottom

    /**
     * The constructor for the server. Initializes everything
     * @param port - the port number of the server socket.
     * @throws IOException
     */
    public PingballServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        netClients = new ArrayList<NetworkClient>();
        names = new HashMap<String, NetworkClient>();
        linkages = new HashMap<String, Map<Integer, String>>();
    }
    

    /**
     * Run the server, listening for client connections and handling them.
     * Never returns unless an exception is thrown.
     * 
     * @throws IOException if the main server socket is broken
     *                     (IOExceptions from individual clients do *not* terminate serve())
     */
    public void serve() throws IOException {
        
    	Thread userListener = new Thread() {
            public void run() {
            	try {
					handleRequest();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        };
        userListener.start();
        
        //Wait for and add new NetworkClients
        while(true) {
            Socket socket = serverSocket.accept();
            NetworkClient netClient = new NetworkClient(socket,this);
            netClients.add(netClient);
            Thread connection = new Thread(netClient);
            connection.start();
        }
    }
    
    /**
     * Handles requests from the user - that is, requests to join Boards together.
     * As of now, this method doesn't fully work, but most of it is working. There is
     * just some communication issue between PingballClient and NetworkClient.
     * @throws IOException 
     */
    private void handleRequest() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for (String line = in.readLine(); line != null; line = in.readLine()) {
            String[] tokens = line.split("\\s+");
            if (tokens.length != RIGHT) {
            	System.out.println ("You messed up your input");
            	//For debugging purposes
            	System.out.println(Arrays.toString(tokens));
            }
            String board1 = tokens[LEFT];
            String board2 = tokens[TOP];
            //The map linkages maps NetworkClient names to dictionaries of (integer, NetworkClient name).
            if (tokens[0].equals("h")) {
                Map<Integer, String> tempMap = new HashMap<Integer,String>();
                tempMap.put(RIGHT, board2);
            	linkages.put(board1, tempMap);
                Map<Integer, String> tempMap2 = new HashMap<Integer,String>();
                tempMap2.put(LEFT, board1);
            	linkages.put(board2, tempMap2);
            	//Call addLink method on the NetworkClient and pass the message down to the PingballClient
            	names.get(board1).addLink(RIGHT, board2);
            	names.get(board2).addLink(LEFT, board1);
            }
            if (tokens[0].equals("v")) {
                Map<Integer, String> tempMap = new HashMap<Integer,String>();
                tempMap.put(BOTTOM, board2);
            	linkages.put(board1, tempMap);
                Map<Integer, String> tempMap2 = new HashMap<Integer,String>();
                tempMap2.put(2, board1);
            	linkages.put(board2, tempMap2);
            	//Call addLink method on the NetworkClient and pass the message down to the PingballClient
            	names.get(board1).addLink(BOTTOM, board2);
            	names.get(board2).addLink(TOP, board1);
            }
        }
    }
    
    /**
     * Updates the name of the NetworkClient in the map stored on the server. A name is needed to
     * identify NetworkClients (and connect them).
     * @param nC - NetworkClient object
     * @param name - name of the NetworkClient
     * Threadsafe because: synchronized
     */
    public synchronized void updateName(NetworkClient nC, String name) {
    	names.put(name, nC);
    }
    
    /**
     * Passes a ball to the destination, as well as information about the direction the ball
     * was traveling as it left its previous board
     * @param destination - the name of the destination Board
     * @param ball - the ball and its data
     * @param direction - the direction the ball left the previous Board from
     */
    public synchronized void passBall(String destination, Ball ball, String direction) {
    	NetworkClient receiver = names.get(destination);
    	receiver.addBall(ball, direction);
    }

    /**
     * The main method, which runs the server. The command line arguments are used to specify
     * an optional port number. It then runs the server.
     */
    public static void main(String[] args) {
        // Command-line argument parsing is provided. Do not change this method.
        int port = DEFAULTPORT; // default port

        Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
        try {
            while ( ! arguments.isEmpty()) {
                String flag = arguments.remove();
                try {
                    if (flag.equals("--port")) {
                        port = Integer.parseInt(arguments.remove());
                        if (port < 0 || port > PORTMAX) {
                            throw new IllegalArgumentException("port " + port + " out of range");
                        }
                    } else {
                        throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
                    }
                } catch (NoSuchElementException nsee) {
                    throw new IllegalArgumentException("missing argument for " + flag);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("unable to parse number for " + flag);
                }
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: PingballServer [--port PORT]");
            return;
        }

        try {
            runPingballServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the server
     * @param port - the port number.
     * @throws IOException
     */
    public static void runPingballServer(int port) throws IOException {      
        PingballServer server = new PingballServer(port);
        server.serve();
    }
    
    /**
     * This method will be kept here just in case we use it later. Currently, it is not in use.
     */
    public void stepPhysics() {
        //while loop  - while true, update all boards
        
//      long oldtime = System.currentTimeMillis();
//      long newtime = System.currentTimeMillis();
        
      //refresh the system 20 times per second
//      newtime = System.currentTimeMillis();
//      if (newtime - oldtime > 50)    {
//          oldtime = newtime;
//          for (Board board: boardlist)    {
//              //find the balls that need to go into this board and add them
//              for (BallMessage ballmessage : messages)  {
//                  if (ballmessage.getDestination().equals(board.getName())) {
//                      board.enterBall(ballmessage);
//                      messages.remove(ballmessage);
//                  }
//              }
//              //step the board
//              board.step();
//              //get all the new balls out and put them on the list of balls to be inserted into places on the next cycle
//              List<BallMessage> exits = board.extractExitBalls();
//              for (BallMessage ballmessage : exits)   {
//                  messages.add(ballmessage);
//                  exits.remove(ballmessage);
//              }
//          }
//      }
    }

}


