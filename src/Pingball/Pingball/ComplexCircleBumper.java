package Pingball;


/**
 * The ComplexCircleBumper class represents a circle bumper that occupies one square.
 * It stores its position (as an x and a y coordinate), its diameter length
 * (which should be L), and its name. It wraps a standard CircleBumper.
 * 
 * @author Nicole, Kai, Victor
 * 
 */
public class ComplexCircleBumper implements ComplexBoardElement {
    private CircleBumper circle;
    private String name;

    /**
     * Constructs a new ComplexCircleBumper object
     * 
     * @param x - the x coordinate of the center
     * @param y - the y coordinate of the center
     * @param name - the name of the bumper, can't have the same name as any other
     * BoardElements that it shares a board with
     * @param length - the diameter length of the circle
     */
    public ComplexCircleBumper(CircleBumper circle, String name) {
        this.circle = circle;
        this.name = name;
    }

    /**
     * @return - the x coordinate
     */
    public double getX() {
        return circle.getX();
    }

    /**
     * @return - the y coordinate
     */
    public double getY() {
        return circle.getY();
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
    public String toString() {
        return "O";
    }

    /**
     * @param ball - the ball for which we calculate the time until collision to
     * this bumper
     * @return the BoardElement that represents the component of this triangle
     *         bumper which the ball will hit - must be either a wall or a
     *         circle bumper
     */
    public BoardElement timeUntilCollisionElement(Ball ball) {
        return circle;
    }
    
    @Override
    public boolean isFlipper() {
        return false;
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void bounce(Ball ball) {
        BoardElement collider = timeUntilCollisionElement(ball);
        collider.bounce(ball);

    }
    
    /**
     * @param ball the ball for which we calculate the time until collision to this bumper
     * @return the BoardElement that represents the component of this square bumper which the ball will hit - must be either a wall or a circle bumper
     */
    public double timeUntilCollision(Ball ball) {
        return circle.timeUntilCollision(ball);
    }

    @Override
    public boolean checkRemove() {
        return false;

    }

	@Override
	public boolean isComplexWall() {
		return false;
	}
}
