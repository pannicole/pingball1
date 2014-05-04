package Pingball;

import physics.Geometry;
import physics.Vect;

/**
 * The Flipper class represents a Flipper object, which occupies a 2x2 square in space. Flippers
 * can be left or right flippers, and they rotate when triggered. When they collide with a ball,
 * their velocity must be taken into account when computing the final velocity of the ball after
 * the collision.
 * @author kaixiao, nicolepan, victorz
 *
 */
public class Flipper implements ComplexBoardElement  {
    private final double x;
    private final double y;
    private String name;
    private final double length;
    private final boolean isRight;
    private int orientation;
    private Wall wall;
    private double omega;
    private final double reflectionCoefficient;
    private boolean isRotating;
    private double rotated;

    /**
     * Constructs a new Flipper object
     * 
     * @param x the x coordinate of the top left or right corner
     * @param y the y coordinate of the top left or right corner
     * @param name the name of the flipper, can't have the same name as any other
     *            BoardElements that it shares a board with
     * @param length the side length of the flipper
     * @param isRight whether or not it is a right flipper
     * @param orientation orientation of the flipper
     */
    public Flipper(String name, boolean isRight, int orientation, Wall wall, double angularVelocity) {
        this.x = wall.getLine().p1().x();
        this.y = wall.getLine().p1().y();
        this.name = name;
        this.length = wall.getLine().length();
        this.isRight = isRight;
        this.orientation = orientation;
        this.wall = wall;
        this.omega = angularVelocity;
        this.reflectionCoefficient = 0.95;
        this.isRotating = false;
        this.rotated = 0;
    }

    @Override
    /**
     * Getter method to retrieve the horizontal location of the pivot corner of the flipper.
     * 
     * @return a double that represents the X coordinate of the board element's position
     */
    public double getX() {
        return x;
    }

    @Override
    /**
     * Getter method to retrieve the vertical location of the pivot corner of the flipper.
     * 
     * @return a double that represents the Y coordinate of the board element's position
     */
    public double getY() {
        return y;
    }

    @Override
    /**
     * Action is called when a there is a trigger.
     * 
     * Rotates the flipper 90 degrees.
     */
    
    //when we call action, it switches the orientation and sets the angular velocity to 1080 degrees per second
    //board will call rotateStep every time it steps, mutating the angle field as well as rotated
    //it only does this if isRotating is true and it isn't done rotating yet
    //if rotated reaches 90 or 0 (depending on orientation), board will stop calling rotateStep and set isRotating to false
    //it will also stop the rotation with the stop method
    //we have getter and setter methods for everything related to this operation
    public void action() {
        this.isRotating = true;
        //switch the orientation and change the angular velocity
        //operating under assumption that going 0 to 1 is positive omega
        if (this.orientation == 1)   {
            this.orientation = 0;
            this.omega = -Math.PI*6;
        }
        else    {
            this.orientation = 1;
            this.omega = Math.PI*6;
        }
    }
    
    /**
     * stops the flipper's rotation
     */
    public void stopRotating()   {
        this.isRotating = false;
    }
    
    /**
     * checks if the flipper is currently rotating
     * @return true if the flipper is moving, false otherwise
     */
    public boolean getRotating()   {
        return this.isRotating;
    }

    
    /** 
     * Mutates the rotated field to reflect how much it has rotated
     * Should be called by the Board.step thing every time it steps while rotating
     * @param timeInterval the time interval, dependent on the board's framerate
     */
    public void rotateStep(double timeInterval)    {
        //once it begins rotating, it switches its orientation
        //orientation = 1 means it's rotating FROM 0 TO 1
        //automatically prevents rotating past the limits
        if (this.orientation == 1)  {
            this.rotated = Math.min(this.rotated + timeInterval*this.omega, Math.PI*2);
        }
        else    {
            this.rotated = Math.max(this.rotated - timeInterval*this.omega, 0.0);
        }
    }
    
    /**
     * gets how far the flipper has rotated so far
     * @return the current value for the rotated field
     */
    public double getRotated()    {
        return this.rotated;
    }
    
    /**
     * resets the rotated field back to 0
     * to be called when the flipper has reached 0.0 or 90.0
     */
    public void resetRotated()  {
        this.rotated = 0.0;
    }
    
    /**
     * Gets the angular velocity of the flipper
     * @return
     */
    public double getOmega()    {
        return this.omega;
    }
    
    /**
     * sets the angular velocity back to 0.0
     */
    public void stop()  {
        this.omega = 0.0;
        this.isRotating = false;
    }

    @Override
    /**
     * Getter method to retrieve the board element's coefficient of reflection 
     * 
     * @return a double the represents the board element's CoR
     */
    public double getCoefficientOfReflection() {
        return 0.95;
    }

    /**
     * returns the string representation of a Flipper
     */
    @Override
    public String toString() {
        if (orientation == 0) {
            return "|";
        }
        else {
            return "-";
        }

    }


    @Override
    public BoardElement timeUntilCollisionElement(Ball ball) {
        return wall;
    }
    
    @Override
    public double timeUntilCollision(Ball ball)  {
        Vect center = new Vect(x, y);
        return Geometry.timeUntilRotatingWallCollision(this.wall.getLine(), center, this.omega, ball.getCircle(), ball.getVelocity());
    }
    
    /**
     * resets the balls velocity after it bounces off of a flipper
     */
    @Override
    public void bounce(Ball ball)    {
        Vect center = new Vect(x, y);
        ball.setVelocity(Geometry.reflectRotatingWall(this.wall.getLine(), center, this.omega, ball.getCircle(), ball.getVelocity(), this.reflectionCoefficient));
    }
    
    /**
     * Gets the name of the flipper
     */
    public String getName()   {
        return new String(name);
    }

    @Override
    public boolean isFlipper() {
        return true;
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    @Override
    public boolean checkRemove() {
        return false;
    }
    
    /**
     * Checks if this is a right flipper or a left flipper
     * @return True if it's a right flipper, false otherwise
     */
    public boolean isRight(){
        return this.isRight;
    }
    
    /**
     * Checks if this is a right flipper or a left flipper
     * @return True if it's a left flipper, false otherwise
     */
    public double getLength(){
        return this.length;
    }

	@Override
	public boolean isComplexWall() {
		return false;
	}
    
}