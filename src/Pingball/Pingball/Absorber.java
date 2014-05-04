package Pingball;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;
/**
 * Absorber is a mutable class that implements ComplexBoardElement and represents an absorber in Pingball.
 * An absorber is referenced to by its upper left hand coordinates, and has a length and a width as well as a name.
 * @author Nicole
 *
 */
public class Absorber implements ComplexBoardElement {
    private final double x;
    private final double y;
    private String name;
    private final double length;
    private final double height;
    private Ball storedball;
    private final double l = 1;
    private List<Wall> walls = new ArrayList<Wall>();
    private List<CircleBumper> circles = new ArrayList<CircleBumper>();
    private final double launchx;
    private final double launchy;
    private final double storex;
    private final double storey;
    private boolean storing;

    /**
     * Constructs a new Absorber object
     * 
     * @param x
     *            the x coordinate of the top left corner
     * @param y
     *            the y coordinate of the top left corner
     * @param name
     *            the name of the absorber, can't have the same name as any
     *            other BoardElements that it shares a board with
     * @param length
     *            the length of the absorber
     * @param height
     *            the height of the absorber
     */
    public Absorber(double x, double y, String name, double length,
            double height) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.length = length;
        this.height = height;
        walls.add(new Wall(new LineSegment(x, y, x + length * l, y), 1.0, 1.0));
        walls.add(new Wall(new LineSegment(x + length * l, y, x + length * l, y
                + height * l), 1.0, 1.0));
        walls.add(new Wall(new LineSegment(x, y + height * l, x + length * l, y
                + height * l), 1.0, 1.0));
        walls.add(new Wall(new LineSegment(x, y, x, y + height * l), 1.0, 1.0));
        circles.add(new CircleBumper(new Circle(x, y, 0.01)));
        circles.add(new CircleBumper(new Circle(x + length * l, y, 0.01)));
        circles.add(new CircleBumper(new Circle(x + length * l, y + height * l,
                0.01)));
        circles.add(new CircleBumper(new Circle(x, y + height * l, 0.01)));
        this.launchx = x + length-1.25;
        this.launchy = y-0.25;
        this.storex = x + length-1.25;
        this.storey = y = height-0.25;
        this.storing = false;
        this.storedball = null; // replace with noneBall type or something,
                                // don't use null
    }

    @Override
    /**
     * Getter method to retrieve the horizontal location of the upper-left corner of the absorber.
     * 
     * @return a doubles that represents the X coordinate of the board element's upper-left position
     */
    public double getX() {

        return x;
    }

    @Override
    /**
     * Getter method to retrieve the vertical location of the upper-left corner of the absorber.
     * 
     * @return a vector that represents the Y coordinate of the board element's upper-left position
     */
    public double getY() {
        return y;
    }

    /**
     * Getter method to retrieve height of the absorber
     * @return a double that describes the height of the absorber
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Getter method to retrieve length of the absorber
     * 
     * @return a double that describes the length of the absorber
     */
    public double getLength() {
        return this.length;
    }

    @Override
    /**
     * Action is called when a there is a trigger.
     * When triggered, the absorber will capture the ball and store it at the bottom right hand corner of the absorber.
     * If the absorber has a ball, it will shoot the ball at 50L/sec.
     * Otherwise, there is no action.
     * 
     */
    public void action() {
        // Place ball at the top of the absorber first
        if (this.storing) {
            this.storedball.place(launchx, launchy);
            this.storedball.setVelocity(new Vect(0, -50 * l));
            this.storing = false;

        }
    }

    /**
     * Method to be used when a ball is going to hit the absorber If the
     * absorber already has a ball stored in it, it will bounce it off like a
     * normal bumper or wall If not, it stores the ball in the bottom right
     * corner and sets the storing variable to true
     * 
     * @param ball
     *            the ball to be bounced
     */
    @Override
    public void bounce(Ball ball) {
        if (!this.storing) {
            ball.place(this.storex, this.storey);
            this.storing = true;
            this.storedball = ball;
        }
        else {
            BoardElement collider = this.timeUntilCollisionElement(ball);
            collider.bounce(ball);
        }
    }

    @Override
    /**
     * Getter method to retrieve the board element's coefficient of reflection 
     * 
     * @return a double the represents the board element's CoR
     */
    public double getCoefficientOfReflection() {
        return 1.0;
    }

    /**
     * returns the string representation of a Absorber
     */
    @Override
    public String toString() {
        return "=";

    }

    /**
     * figures out which of the elements of the absorber the ball is going to
     * hit first
     */
    public BoardElement timeUntilCollisionElement(Ball ball) {
        BoardElement minTimeElement = walls.get(0);
        for (Wall wall : walls) {
            if (minTimeElement.timeUntilCollision(ball) > wall
                    .timeUntilCollision(ball))
                minTimeElement = wall;
        }
        for (CircleBumper circle : circles) {
            if (minTimeElement.timeUntilCollision(ball) > circle
                    .timeUntilCollision(ball))
                minTimeElement = circle;
        }
        return minTimeElement;
    }
    /**
     * @param ball the ball for which we calculate the time until collision to this bumper
     * @return the BoardElement that represents the component of this square bumper which the ball will hit - must be either a wall or a circle bumper
     */
    public double timeUntilCollision(Ball ball) {
        double minTime = 40000.0;
        for (Wall wall: walls) {
        	BoardElement wallCast = wall;
            if (wallCast.timeUntilCollision(ball) < minTime)
                minTime = wallCast.timeUntilCollision(ball);
        }
        for (CircleBumper circle: circles) {
        	BoardElement circleCast = circle;
            if (circleCast.timeUntilCollision(ball) < minTime)
                minTime = circleCast.timeUntilCollision(ball);
        }
        return minTime;
    }

    @Override
    /**
     * @return false, is not a flipper
     */
    public boolean isFlipper() {
        return false;
    }

    @Override
    /**
     * @return, is not an absorber
     */
    public boolean isAbsorber() {
        return true;
    }

    @Override
    /**
     * @return a String, the name of the absorber
     */
    public String getName() {
        return this.name;
    }


    /**
     * returns false, not an invisible wall
     */
    public boolean checkRemove() {
        return false;

    }

	@Override
	public boolean isComplexWall() {
		return false;
	}

}
