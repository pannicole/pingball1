package Pingball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import physics.*;

/**
 * PingballClient represents a single client playing Pingball.
 * 
 * Thread Safety Argument: Each client represents a single client playing
 * Pingball. The clients interact with each other a bit, but for the most part,
 * there is no shared data between the clients so we do not have to worry too
 * much about thread safety. This is because most data
 * is confined within a PingballClient. For example, each client will have their own
 * private Board, with its own BoardElements and their own balls. The only time
 * a client interacts with another client is when a ball passes from one Board
 * to another. This is done through message passing and is threadsafe. Finally,
 * actions like processing newly entering balls and then adjusting the board are processed
 * sequentially, so they will not go out of order and behave strangely.
 * 
 */
public class PingballClient implements Runnable {
	
    private static final int PORTMAX = 65535;
    private static final int PORTDEFAULT = -1;
    private static final String HOSTDEFAULT = "l";
    private Board board;
    private String host;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    
    /**
     * Standard constructor
     * @param host - host name
     * @param port - port number
     * @param board - board (parsed from file)
     * @throws UnknownHostException
     * @throws IOException
     */
    public PingballClient (String host, int port, Board board) throws UnknownHostException, IOException {
    	 this.board = board;
    	 this.host = host;
    	 this.port = port;
		 if (port != PORTDEFAULT) {
	    	 socket = new Socket(host, port);
			 out = new PrintWriter(socket.getOutputStream(), true);
			 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 }
    }   
    
    /**
     * Parses a File using ANTLR.
     * @param file - File representing Board to be parsed
     * @return - the board the file represents
     * @throws IOException
     */
    public static Board parse(File file) throws IOException {
        // Create a stream of tokens using the lexer.
        FileInputStream fis = new FileInputStream(file);
        CharStream stream = new ANTLRInputStream(fis);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        // Feed the tokens into the parser.
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();

        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.input(); // "file" is the starter rule.

        // *** debugging option #1: print the tree to the console
        // System.err.println(tree.toStringTree(parser));

        // *** debugging option #2: show the tree in a window
//        RuleContext rootContext = (RuleContext) tree;
//        rootContext.inspect(parser);

        // *** debugging option #3: walk the tree with a listener
        // new ParseTreeWalker().walk(new PrintEverything(), tree);

        // Construct the board by walking over the parse tree with a listener.
        ParseTreeWalker walker = new ParseTreeWalker();
        MakeExpression listener = new MakeExpression();
        walker.walk(listener, tree);

        listener.board = new Board(50, listener.name, listener.gravity, listener.friction1, listener.friction2, listener.balls, listener.elements);
        

        for(String name: listener.triggersAndActions.keySet()){
            ComplexBoardElement trigger = null;
            ComplexBoardElement action = null;
            
            for(ComplexBoardElement e: listener.board.getElements()){
                if(e.getName().equals(name)){
                    trigger = e;
                }
                if (e.getName().equals(listener.triggersAndActions.get(name))){
                    action = e;
                }
            }
            listener.board.addTrigger(trigger, action);
        }
        // return the value that the listener created
//        System.out.println ("Heyo");
//        System.out.println(listener.board.toString());
        return listener.board;
    }

    /**
     * Another ANTLR related method for parsing a file and creating the board.
     *
     */
    private static class MakeExpression extends ExpressionBaseListener {
        Board board;
        List<Ball> balls = new ArrayList<Ball>();
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>();
        HashMap<String,String> triggersAndActions = new HashMap<String, String>();
        String name;
        double friction1;
        double friction2;
        double gravity;

        public void exitOpener(ExpressionParser.OpenerContext ctx) {
            if (ctx.parameter().size() == 4) {
                name = ctx.parameter().get(0).value().getText();
                gravity = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                friction1 = Double.parseDouble(ctx.parameter().get(2).value()
                        .getText());
                friction2 = Double.parseDouble(ctx.parameter().get(3).value()
                        .getText());

            }
            else if (ctx.parameter().size() == 3) {
                name = null;
                gravity = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                friction1 = Double.parseDouble(ctx.parameter().get(2).value()
                        .getText());
                friction2 = Double.parseDouble(ctx.parameter().get(3).value()
                        .getText());
            }
            else if (ctx.parameter().size() == 2) {
                name = ctx.parameter().get(0).value().getText();
                gravity = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                friction1 = 0.025;
                friction2 = 0.025;
            }

            else if (ctx.parameter().size() == 1) {
                name = ctx.parameter().get(0).value().getText();
                gravity = 25.0;
                friction1 = 0.025;
                friction2 = 0.025;
            }
            else {
                name = null;
                gravity = 25.0;
                friction1 = 0.025;
                friction2 = 0.025;
            }
        }

        public void exitDefinition(ExpressionParser.DefinitionContext ctx) {
            String type = ctx.type().getText();

            if (type.equals("ball")) {

                String ballName = ctx.parameter().get(0).value().getText();
                double x = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                double y = Double.parseDouble(ctx.parameter().get(2).value()
                        .getText());
                double xV = Double.parseDouble(ctx.parameter().get(3).value()
                        .getText());
                double yV = Double.parseDouble(ctx.parameter().get(4).value()
                        .getText());

                balls.add(new Ball(ballName, new Circle(x, y, 1.0), new Vect(
                        xV, yV)));
            }
            else if (type.equals("squareBumper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                elements.add(new SquareBumper((double) x, (double) y, name));
            }
            else if (type.equals("circleBumper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                elements.add(new ComplexCircleBumper(new CircleBumper(
                        new Circle(x, y, 1.0)), name));
            }

            else if (type.equals("triangleBumper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                Integer o = Integer.parseInt(ctx.parameter().get(3).value()
                        .getText());
                elements.add(new TriangleBumper((double) x, (double) y, name, o));
            }
            else if (type.equals("leftFlipper") || type.equals("rightFlipper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                Integer o = Integer.parseInt(ctx.parameter().get(3).value()
                        .getText());
                if (o == 0 || o == 90) {
                    elements.add(new Flipper(name, type.equals("rightFlipper"),
                            o, new Wall(new LineSegment(x, y, x, y + 1), 1.0,
                                    2.0), 6 * Math.PI));
                }
                else {
                    elements.add(new Flipper(name, type.equals("rightFlipper"),
                            o, new Wall(new LineSegment(x, y, x + 1, y), 1.0,
                                    2.0), 6 * Math.PI));
                }
            }
            else if (type.equals("absorber")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                Integer width = Integer.parseInt(ctx.parameter().get(3).value()
                        .getText());
                Integer height = Integer.parseInt(ctx.parameter().get(4)
                        .value().getText());

                elements.add(new Absorber(x, y, name, width, height));
            }
            else if (type.equals("fire")){
                String trigger = ctx.parameter().get(0).value().getText();
                String action = ctx.parameter().get(1).value().getText();
                triggersAndActions.put(trigger, action);
            }

        }
    }
    
    /**
     * The main method of PingballClient. Waits for optional arguments relating
     * to the port number or host name. Requests a file (not optional) to be used
     * as the Board for this PingballClient
     * @param args - hostname, port number, file with Board in it.
     * @throws IOException
     */
    public static void main (String[]args) throws IOException {
    	int port = PORTDEFAULT;
    	String host = HOSTDEFAULT;
    	String fileName = "";
    	
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
                    } else if (flag.equals("--host")) {
                    	host = arguments.remove();
                    } else {
                        fileName = flag;
                    }
                } catch (NoSuchElementException nsee) {
                    throw new IllegalArgumentException("missing argument for " + flag);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("unable to parse number for " + flag);
                }
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: PingballClient [--host HOST] [--port PORT] FILE");
            return;
        }
        File file = new File(fileName);
        Board board = parse(file);
        runPingballClient(host, port, board);

    }

    /**
     * Listens to and handles requests that it hears through socket. This method is not working
     * for some reason, as it appears that this method is not working even when the board-printing part
     * of PingballClient is working.
     * @throws IOException
     */
    private void handleRequest() throws IOException {
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for (String line = in.readLine(); line != null; line = in.readLine()) {
            String[] tokens = line.split("\\s+");
            //System.out.println(tokens);
            if (tokens[0].equals("B")) { //Ball coming in
            	Ball ball = new Ball(tokens[2], new Circle(Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), 0.5), 
  					  new Vect(Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6])));
  				board.enterBall(new BallMessage(ball, "randomDestination", tokens[7]));
            }
            if (tokens[0].equals("L")) { //Link being established between boards.
            	board.toggleInvis(Integer.parseInt(tokens[1]), tokens[2]);
            }
        }
    }

    
    /**
     * Runs the client.
     * @param host - host name
     * @param port - port number
     * @param board - board
     * @throws UnknownHostException
     * @throws IOException
     */
	private static void runPingballClient(String host, int port, Board board) throws UnknownHostException, IOException {
		PingballClient client = new PingballClient(host, port, board);
		Thread thread = new Thread(client);
		thread.start();
	}

	/**
	 * Starts a request handler (doesn't work properly) and a while loop that updates the board every 2 milliseconds
	 * and then prints out the board every 50 milliseconds. Makes the board transition from state to state,
	 * takes balls that left the Board and passes them as messages to NetworkClient, which passes them to the
	 * server to be sent to other PingballClients.
	 */
    public void run() {
        Thread NetworkListener = new Thread(new Runnable() {
            public void run() {
            	try {
					handleRequest();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        NetworkListener.start();
    	if (port != PORTDEFAULT)
    		out.println("N" + board.getName());
        System.out.println(board);
    	long oldTime = System.currentTimeMillis();
    	long oldTimePhysics = System.currentTimeMillis();
    	long newTime = System.currentTimeMillis();
        while (true) {
        	newTime = System.currentTimeMillis();
        	if (newTime - oldTimePhysics >= 2) {
        		oldTimePhysics = newTime;
        		board.step();
            	if (port != PORTDEFAULT) {
	        		List<BallMessage> exitBalls = board.extractExitBalls();
	        		if (exitBalls != null) {
	        			for (BallMessage eBall: exitBalls) {
	        				out.println("B "+eBall);
	        			}
	        		}
            	}
        	}
        	if (newTime - oldTime > 50) {
        		oldTime = newTime;
        		System.out.println(board);
        	}
        }
    }
}
