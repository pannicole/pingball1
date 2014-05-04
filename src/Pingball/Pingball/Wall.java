package Pingball;

import physics.*;

/**
 * The Wall class represents a line segment bumper that has length L. It stores its position (as an
 * x and a y coordinate), its coefficient of reflection, whether or not it is invisible, and its name.
 * @author Nicole, Kai, Victor
 *
 */
public class Wall implements BoardElement {
    private final LineSegment wall;
    private double CoR;
    private boolean invisible = false;
    private double length;
    
    /**
     * Constructs a new CircleBumper object
     * @param x the x coordinate of the center
     * @param y the y coordinate of the center
     * @param name the name of the bumper, can't have the same name as any other BoardElements that it shares a board with
     * @param length the diameter length of the circle
     */
    public Wall(LineSegment wall, double CoR, double length)   {
        this.wall = wall;
        this.CoR = CoR;
        this.length = length;
    }
    
    /**
     * setter method for the invisible parameter
     * @param invisible the boolean we want to set invisible to
     */
    public void setInvisibility(boolean invisible) {
        this.invisible = invisible;
    }
    
    /**
     * getter method for the invisible field
     * @return true if the wall is invisible, false otherwise
     */
    public boolean getInvisibility() {
        return invisible;
    }

    
    /**
     * @return - the x coordinate
     */
    public double getX() {
        return wall.p1().x();
    }

    /**
     * @return - the y coordinate
     */
    public double getY() {
        return wall.p1().y();
    }

    /**
     * @return - the coefficient of reflection
     */
    public double getCoefficientOfReflection() {
        return CoR;
    }
    
    /**
     * calculates the time until collision between the ball and this wall
     * @param ball the ball that is moving to collide
     * @return a double representing the time it takes for the ball to hit this wall
     */
    public double timeUntilCollision(Ball ball) {
        return Geometry.timeUntilWallCollision(wall, ball.getCircle(), ball.getVelocity());
    }

    /**
     * Performs the action of the wall, which is nothing.
     */
    public void action() {
    }
    
    /**
     * mutates the ball's velocity to reflect a bounce off of this wall
     * @param ball the ball that is bouncing on this wall
     */
    public void bounce(Ball ball) {
        if (!this.invisible)    {
            ball.setVelocity(Geometry.reflectWall(wall, ball.getVelocity(), CoR));
        }
    }

    public LineSegment getLine()   {
        return this.wall;
    }
    
    /**
     * Getter method for the length of a wall.
     * @return a double, the length of the wall.
     */
    public double getLenght(){
        return this.length;
    }
}
