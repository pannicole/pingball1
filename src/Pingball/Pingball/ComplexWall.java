package Pingball;

import physics.Geometry;
import physics.LineSegment;

/**
 * This class represents the boundaries of the board. They are basically the same as normal walls,
 * except that they have to display the names of other boards they are connected to and they have to
 * sometimes be "invisible" and allow balls to pass through them (and into other clients).
 * @author kaixiao, nicolepan, victorz
 *
 */
public class ComplexWall implements ComplexBoardElement {
    private final LineSegment wall;
    private final double x;
    private final double y;
    private boolean isInvisible;
    private final boolean isHorizontal;
    private final double size;
    private final String name;
    private final double CoR = 1.0;
    private String direction;
    
    /**
     * Constructs a ComplexWall object
     * @param wall the LineSegment that represents the wall geometrically
     * @param name a String that represents the wall
     * @param direction a String that tells us if it is the left wall, the right wall, etc of the board that it is in
     */
    public ComplexWall(LineSegment wall, String name, String direction) {

        this.size = wall.length();
        this.x = wall.p1().x();
        this.y = wall.p1().y();
        this.isInvisible = false;
        this.isHorizontal = (wall.p1().toPoint2D().x == wall.p2().toPoint2D().x);
        this.wall = wall;
        this.direction = direction;
        this.name = name;
    }

    /**
     * sets the wall to be invisible
     */
    public void setInvisible() {
        this.isInvisible = true;
    }
    
    /**
     * toggles the wall's invisibility
     */
    public void toggleVisible() {
        this.isInvisible = !this.isInvisible;
    }

    /**
     * 
     * @return true if the wall is invisible
     */
    public boolean isInvisible() {
        return isInvisible;
    }

    /**
     * 
     * @return true if the wall is horizontal
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getCoefficientOfReflection() {
        return 1.0;
    }

    @Override
    public void action() {
    }

    @Override
    public BoardElement timeUntilCollisionElement(Ball ball) {
        return new Wall(wall, 1.0, size);
    }

    /**
     * @param ball the ball for which we calculate the time until collision to this bumper
     * @return the BoardElement that represents the component of this square bumper which the ball will hit - must be either a wall or a circle bumper
     */
    public double timeUntilCollision(Ball ball) {
        return Geometry.timeUntilWallCollision(wall, ball.getCircle(), ball.getVelocity());
    }
    
    @Override
    public String toString() {
        return ".";
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
        if (!this.isInvisible) {
            ball.setVelocity(Geometry.reflectWall(wall, ball.getVelocity(), CoR));
        }
        else {
        	//Special message passing is done in Board, so this clause should never
        	//be called.
        }
    }

    @Override
    public boolean checkRemove() {
        return this.isInvisible;
    }
    
    /**
     * Gets the direction of the wall
     * @return "left" if this is a left wall, "right" if this is a right wall, and so on
     */
    public String getDirection() {
        return this.direction;
    }

	@Override
	public boolean isComplexWall() {
		return true;
	}

}
