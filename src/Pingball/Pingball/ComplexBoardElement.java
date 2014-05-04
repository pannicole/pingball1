package Pingball;

/**
 * This interface is used to represent the standard gadgest in Board. Bumpers, Flippers,
 * and Absorbers all implement this interface. The interface has standard methods for finding
 * its location, and it also has a few functions to determine how close a ball is to
 * colliding with this ComplexBoardElement. These methods are used in changing Board at
 * every step.
 * @author kaixiao, npan, victorz
 *
 */
public interface ComplexBoardElement {
    
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
     * Gets the BoardElement that the ball will collide with first
     * @param ball the ball for which we are calculating
     * @return the BoardElement the ball will collide with
     */
    public BoardElement timeUntilCollisionElement(Ball ball);
    
    /**
     * checks if this is a flipper
     * @return true if this is a flipper, false otherwise
     */
    public boolean isFlipper();
    
    /**
     * checks if this is an absorber
     * @return true if this is an absorber, false otherwise
     */
    public boolean isAbsorber();
    
    /**
     * gets the name of the BoardElement
     * @return a String representing the BoardElement's name
     */
    public String getName();
    
    /**
     * mutates the ball's velocity so as to represent a bounce against this board element
     * @param ball the ball to be bounced
     */
    public void bounce(Ball ball);
    
    /**
     * Checks to see if the ball should be removed
     * @return true if this is an invisible wall, false otherwise
     */
    public boolean checkRemove();
    
    /**
     * calculates the time until the ball collides with this element
     * @param ball the ball in question
     * @return the time it takes to collide
     */
	public double timeUntilCollision(Ball ball);
	
	/**
	 * Checks if this wall is a complex wall
	 * @return true if it is a complex wall, false if it is any other kind of ComplexBoardElement
	 */
	public boolean isComplexWall();
    
}