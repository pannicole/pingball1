package Pingball;
import physics.*;

/**
 * 
 * Ball is a mutable class that represents a Ball in Pingball. It has two instance
 * variables - a circle and a velocity. The circle represents the ball's location,
 * and it stores an x-location, a y-location, and a radius. The velocity of the ball
 * is stored as a Vect.
 * 
 * @author Nicole, Kai, Victor
 *
 */
public class Ball {
    private Circle circle;
    private Vect velocity;
    private final String name;
    
    /**
     * Ball constructor
     * @param circle a Circle object that describes the position and size of the ball
     * @param velocity a Vect that describes the speed and direction of the ball
     */
    public Ball (String name, Circle circle, Vect velocity) {
        this.circle = circle;
        this.velocity = velocity;
        this.name = name;
    }
    
    /**
     * getter method for the name
     * @return a String representing the name of the ball
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * gets the X coordinate of the ball
     * @return a double that represents the X coordinate of the ball
     */
    public double getX()    {
        return circle.getCenter().x();
    }
    
    /**
     * places the ball at a new position
     */
    public void place(double X, double Y) {
        Circle newcircle = new Circle(X, Y, this.circle.getRadius());
        this.circle = newcircle;
    }
    
    /**
     * gets the Y coordinate of the ball
     * @return a double that represents the Y coordinate of the ball
     */
    public double getY()    {
        return circle.getCenter().y();
    }
    
    /**
     * gets the velocity of the ball
     * @return a Vect that represents the velocity of the ball
     */
    public Vect getVelocity()   {
        return velocity;
    }
    
    /**
     * sets the velocity of the ball
     */
    public void setVelocity(Vect velocity)   {
        this.velocity = velocity;
    }
    
    /**
     * Updates the position of the circle after a time step. Assumes that there will be no collisions in this
     * time step. Accounts for gravity and friction
     */
    public void step(double time, double gravity, double mu1, double mu2)  {
        double newX = getX() + velocity.x()*time;
        double newY = getY() + velocity.y()*time;
        double newVY = velocity.y() + gravity*time;
        circle = new Circle(newX, newY, circle.getRadius());
        Vect tempV = new Vect(velocity.x(), newVY);
        velocity = tempV.times(1.0 - mu1*time - mu2*time*tempV.length());
    }
    
    /**
     * 
     * @return returns the circle object of the ball
     */
    public Circle getCircle()   {
        return circle;
    }
    
    /**
     * creates a string representation of the ball
     * @return String that contains all the information of the ball in question
     */
    public String toString () {
    	return name + " " + getX() + " " + getY() + " " + velocity.x() + " " + velocity.y();
    }
}

