package Pingball;

import java.util.ArrayList;
import java.util.List;

import physics.*;

/**
 * The triangleBumper class represents a triangle bumper that occupies one triangle. It stores its position (as an
 * x and a y coordinate), its side length (which should be L), and its name.
 * @author Nicole, Kai, Victor
 *
 */
public class TriangleBumper implements ComplexBoardElement {
    private final double x;
    private final double y;
    private String name;
    private final double l = 1.0;
    private final int orientation;
    private List<Wall> walls = new ArrayList<Wall>();
    private List<CircleBumper> circles = new ArrayList<CircleBumper>();
    
    /**
     * Constructs a new triangleBumper object
     * @param x the x coordinate of the top left corner
     * @param y the y coordinate of the top right corner
     * @param name the name of the bumper, can't have the same name as any other BoardElements that it shares a board with
     * @param length the side length of the triangle
     * @param orientation 0|90|180|270 describing the orientation of the triangle bumper
     */
    public TriangleBumper(double x, double y, String name, int orientation)   {
        this.x = x;
        this.y = y;
        this.name = name;
        this.orientation = orientation;
        if (orientation == 0) {
            walls.add(new Wall(new LineSegment(x,y,x,y+l),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y+l,x+l,y),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y,x,y+l),1.0, 1.0));
            circles.add(new CircleBumper(new Circle(x,y,0.01)));
            circles.add(new CircleBumper(new Circle(x,y+l,0.01)));
            circles.add(new CircleBumper(new Circle(x+l,y,0.01)));

        }
        else if (orientation == 90) {
            walls.add(new Wall(new LineSegment(x,y,x+l,y),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y,x+l,y+l),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x+l,y,x+l,y+l),1.0, 1.0));
            circles.add(new CircleBumper(new Circle(x,y,0.01)));
            circles.add(new CircleBumper(new Circle(x+l,y,0.01)));
            circles.add(new CircleBumper(new Circle(x+l,y+l,0.01)));

        }
        else if (orientation == 180) {
            walls.add(new Wall(new LineSegment(x+l,y,x+l,y+l),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y+l,x+l,y),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y+l,x+l,y+l),1.0, 1.0));
            circles.add(new CircleBumper(new Circle(x+l,y,0.01)));
            circles.add(new CircleBumper(new Circle(x+l,y+l,0.01)));
            circles.add(new CircleBumper(new Circle(x,y+l,0.01)));

        }
        else {
            walls.add(new Wall(new LineSegment(x,y,x+l,y+l),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y,x,y+l),1.0, 1.0));
            walls.add(new Wall(new LineSegment(x,y+l,x+l,y+l),1.0, 1.0));
            circles.add(new CircleBumper(new Circle(x,y,0.01)));
            circles.add(new CircleBumper(new Circle(x,y+l,0.01)));
            circles.add(new CircleBumper(new Circle(x+l,y+l,0.01)));

        }
    }
    
    /**
     * @return - the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * @return - the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * 
     * @return the name of the triangle bumper
     */
    public String getName(){
        return name;
    }

    /**
     * @return - the coefficient of reflection
     */
    public double getCoefficientOfReflection() {
        return 1.0;
    }
    
    public int getOrientation(){
        return this.orientation;
    }

    /**
     * Performs the action of the bumper, which is nothing.
     */
    public void action() {
        
    }
    
    /**
     * returns the string representation of a triangle Bumper
     */
    @Override
    public String toString() {
        if(orientation == 90 || orientation == 270){
            return "\\";
        }
        else {
            return "/";
        }

    }
    
    /**
     * @param ball the ball for which we calculate the time until collision to this bumper
     * @return the BoardElement that represents the component of this triangle bumper which the ball will hit - must be either a wall or a circle bumper
     */
    public BoardElement timeUntilCollisionElement(Ball ball) {
        BoardElement minTimeElement = walls.get(0);
        for (Wall wall: walls) {
            if (minTimeElement.timeUntilCollision(ball) > wall.timeUntilCollision(ball))
                minTimeElement = wall;
        }
        for (CircleBumper circle: circles) {
            if (minTimeElement.timeUntilCollision(ball) > circle.timeUntilCollision(ball))
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
    public boolean isFlipper() {
        return false;
    }

    @Override
    public boolean isAbsorber() {
        return false;
    }

    @Override
    public void bounce(Ball ball) {
        BoardElement collider = timeUntilCollisionElement(ball);
        collider.bounce(ball);
        
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
