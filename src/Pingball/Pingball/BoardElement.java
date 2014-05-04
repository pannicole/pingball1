package Pingball;


/**
 * This interface is used to represent Walls and CircleBumpers, which are the most fundamental units o gadgets.
 * For example, a Square Bumper consists of 4 smaller Walls and 4 CircleBumpers (at the corners)
 * @author kaixiao
 *
 */
public interface BoardElement {
    
    /**
     * Getter method to retrieve the horizontal location of the board element
     * 
     * @return a vector that represents the X coordinate of the board element's position
     */
    public double getX();
    
    /**
     * Getter method to retrieve the vertical location of the board element
     * 
     * @return a vector that represents the Y coordinate of the board element's position
     */
    public double getY();
    
    /**
     * Getter method to retrieve the board element's coefficient of reflection 
     * 
     * @return a double the represents the board element's CoR
     */
    public double getCoefficientOfReflection();
        
    /**
     * Method that is called in response to the ball striking the board element
     */
    public void action();
    
    /**
     * Creates a string representation of the board element that lists all the relative fields and properties
     * @return a String representation of the board element
     */
    public String toString();
    
    
    /**
     * Changes the velocity of the ball to the appropriate value when the ball collides with the object
     * 
     * @param ball the ball that is bounced
     */
    public void bounce(Ball ball);
    
    /**
     * 
     * @param ball
     * @return a double of the time until the next collision with the element
     */
    public double timeUntilCollision(Ball ball);
    
}