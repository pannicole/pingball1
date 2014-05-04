package Pingball;

/**
 * Class to create a Ball message, which consists of a ball, the name of the board it is going to, and its current direction.
 * @author Nicole
 *
 */
public class BallMessage {
    
    private Ball ball;
    private String destination;
    private String direction;
    
    /**
     * Constructs a BallMessage object
     * @param ball the ball object
     * @param dest the name of the board it needs to go to
     * @param dir the direction it left the last board form
     */
    public BallMessage(Ball ball, String dest, String dir)    {
        this.ball = ball;
        this.destination = dest;
        this.direction = dir;
    }
    
    /**
     * gets the ball part of the ball message
     * @return the Ball in the ball message
     */
    public Ball getBall() {
        return this.ball;
    }
    
    /**
     * gets the name of the board the ball needs to be deposited in
     * @return a String that matches the name of a board
     */
    public String getDestination()  {
        return this.destination;
    }
    
    /**
     * gets the direction of the wall through which the ball left the last board it was in
     * @return "left" if it left through the left wall, "top" if it went out the top, etc
     */
    public String getDirection()    {
        return this.direction;
    }
    
    /**
     * @return A String representation of the message, for message passing.
     */
    public String toString() {
    	String str = "";
    	str = destination + " " + ball + " " + direction;
    	return str;
    }
    
}