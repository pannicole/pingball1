package Pingball;

import physics.*;

/**
 * The CircleBumper class represents a circle bumper that occupies one square. It stores its position (as an
 * x and a y coordinate), its diameter length (which should be L), and its name.
 * @author Nicole, Kai, Victor
 *
 */
public class CircleBumper implements BoardElement {
    private Circle circle;
    
    /**
     * Constructs a new CircleBumper object
     * @param x the x coordinate of the center
     * @param y the y coordinate of the center
     * @param name the name of the bumper, can't have the same name as any other BoardElements that it shares a board with
     * @param length the diameter length of the circle
     */
    public CircleBumper(Circle circle)   {
        this.circle = circle;
    }
    
    /**
     * @return - the x coordinate
     */
    public double getX() {
        return circle.getCenter().x();
    }

    /**
     * @return - the y coordinate
     */
    public double getY() {
        return circle.getCenter().y();
    }

    /**
     * @return - the coefficient of reflection
     */
    public double getCoefficientOfReflection() {
        return 1.0;
    }

    /**
     * Performs the action of the bumper, which is nothing.
     */
    public void action() {        
    }
    
    /**
     * returns the string representation of a Circle Bumper
     */
    @Override
    public String toString(){
        return "O";
    }
    
    /**
     * gets the time until the ball collides with this bumper
     * @param ball the ball to collide
     * @return a double representing the time in seconds that it will take to collide with this bumper
     */
    public double timeUntilCollision(Ball ball) {
        return Geometry.timeUntilCircleCollision(circle, ball.getCircle(), ball.getVelocity());
    }
    
    /**
     * mutates the ball's velocity to simulate a bounce
     * @param ball the ball that is bouncing
     */
    public void bounce(Ball ball) {
        ball.setVelocity(Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(), ball.getVelocity(), getCoefficientOfReflection()));
    }

}
