package Pingball;

import java.io.*;
import java.net.Socket;

import physics.*;

/**
 * NetworkClient represents an intermediary between the server and the PingballClient. It uses
 * a socket and the socket's inputStream and outputStream to communicate with the PingballClient,
 * and it stores a reference to the server so that it can call methods that are passed to the server
 * (which allows this NetworkClient to interact with other ones). The NetworkClient also stores the name
 * of the board that it is representing.
 * 
 * Thread Safety Argument: This NetworkClient is threadsafe because all data is confined here in NetworkClient.
 * There is no data stored or modified on NetworkClient that is shared between threads other than data that
 * is passed to the server. The data passed to the server is threadsafe for the reasons discussed in
 * PingballServer (confinement and synchronized).
 */
public class NetworkClient implements Runnable {
    private final Socket socket;
    private final PingballServer server;
    public String boardName;
    private BufferedReader in;
    private PrintWriter out;
   
    /**
     * Standard constructor
     * @param socket - a socket
     * @param server - a PingballServer reference, to call its methods and interact with other NetworkClients.
     * @throws IOException
     */
    public NetworkClient (Socket socket, PingballServer server) throws IOException {
    	this.socket = socket;
    	this.server = server;
    	boardName = "";
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
 
    /**
     * Runs and handles the socket connection
     */
    public void run() {
        try {
            handleConnection(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Has a listener for signals from PingballClient (through socket's inputStream
     * and outputStream). Parses messages differently based on their "tags" (N for
     * name declaration messages, B for ball passing messages, YAA for debugging).
     * @param socket - the socket being handled.
     * @throws IOException
     */
    private void handleConnection(Socket socket) throws IOException {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
      try {
          for (String line = in.readLine(); line != null; line = in.readLine()) {
        	  if (line != null) {
        		  if (line.substring(0,3).equals("YAA")) {
        			  System.out.println(line);
        		  }
        		  if (line.substring(0,1).equals("N")) {
        			  //If I get a name message
        			  boardName = line.substring(1);
        			  server.updateName(this, boardName);
        		  }
        		  else if (line.substring(0,1).equals("B")) {
        			  String [] tokens = line.split("\\s+");
        			  System.out.println(tokens);

        			  server.passBall(tokens[1], new Ball(tokens[2], 
        					  new Circle(Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), 0.5), 
        					  new Vect(Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]))), tokens[7] );
//        			  tokens[0] = "B"
//        			  tokens[1] = destination
//        			  tokens[2] = ball name
//        			  tokens[3-6] = ball attributes
//        			  tokens[7] = direction
        		  }
        	  }
          }
      } finally {
          out.close();
          in.close();
          socket.close();
      }
    }
    
    /**
     * Sends an addBall message to PingballClient through the socket.
     * @param ball - the ball being added (converted to a String when passed as a message)
     * @param direction - the direction the ball left the previous board from.
     */
    public void addBall(Ball ball, String direction) {
    	out.println("B " + ball + direction);
    }
    
    /**
     * Sends an addLink message to PingballClient through the socket.
     * @param dir - the direction the other Board should be connected from
     * @param otherBoardName - the name of the other board
     */
    public void addLink(int dir, String otherBoardName) {
    	out.println("L " + dir + " " + otherBoardName);
    }
}