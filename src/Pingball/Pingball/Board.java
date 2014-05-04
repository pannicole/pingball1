package Pingball;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import physics.Angle;
import physics.LineSegment;

/**
 * The class Board. It stores a list of its balls, a list of its gadgets (stored as ComplexBoardElements),
 * references to its neighboring Boards (and their names), and a map that describes all the triggers in the Board.
 * @author kaixiao
 *
 */
public class Board {
    private int fps;
    private List<Ball> balls;
    private Board leftboard = null;
    private Board rightboard = null;
    private Board topboard = null;
    private Board bottomboard = null;
    private String leftName;
    private String rightName;
    private String topName;
    private String bottomName;
    private String name;
    private List<ComplexBoardElement> elements;
    private Map<ComplexBoardElement, List<ComplexBoardElement>> triggers;
    private final double gravity;
    private final double mu1;
    private final double mu2;
    private List<BallMessage> exitBalls;

    /**
     * 
     * Constructor for a board, creates a new board object with fields for each
     * of the parameters
     * 
     * @param balls
     *            : the list of balls on the board
     * @param name
     *            : the name of the board
     * @param elements
     *            : the board elements located on the board
     * @param fps the framerate of the board
     * @param l the standard unit of measurement on the board
     */
    public Board(int fps, String name, double gravity, double friction1, double friction2, List<Ball> balls, List<ComplexBoardElement> elements) {
        triggers = new HashMap<ComplexBoardElement, List<ComplexBoardElement>>();
        this.fps = fps;
        this.balls = balls;
        this.name = name;
        this.elements = elements;
        this.gravity = gravity;
        this.mu1 = friction1;
        this.mu2 = friction2;

        ComplexBoardElement leftWall = new ComplexWall(new LineSegment(0.0, 0.0, 0.0, 20.0), "leftWall", "left");
        ComplexBoardElement rightWall = new ComplexWall(new LineSegment(20.0, 0.0, 20.0, 20.0), "rightWall", "right");
        ComplexBoardElement topWall = new ComplexWall(new LineSegment(0.0, 0.0, 20.0, 0.0), "topWall", "top");
        ComplexBoardElement bottomWall = new ComplexWall(new LineSegment(0.0, 20.0, 20.0, 20.0), "bottomWall", "bottom");

        elements.add(leftWall);
        elements.add(rightWall);
        elements.add(topWall);
        elements.add(bottomWall);
    }

    /**
     * gets the elements in this board, such as bumpers and absorbers
     * @return a List of ComplexBoardElements that make up the board
     */
    public List<ComplexBoardElement> getElements(){
        return this.elements;
    }
    
    public Board getLeftBoard() {
        return leftboard;
    }
    public Board getRightBoard() {
        return rightboard;
    }
    public Board getUpperBoard() {
        return topboard;
    }
    public Board getLowerBoard() {
        return bottomboard;
    }
    
    /**
     * toggles the visibility of a wall based on an integer input
     * if the integer is 0 mod 4, it toggles the visibility of the right wall
     * 1 mod 4 - top
     * 2 mod 4 - left
     * 3 mod 4 - bottom
     * easy way to remember - it goes in the same order as angles in math
     * @param index the index of the wall, can be any integer because it will be modded 4
     * Note: The numbers were changed up a bit, so now left is 1, and it increases going
     * counterclockwise up to 4
     */
    public void toggleInvis(int index, String otherBoardName)   {
        //left = 1, top = 2, right = 3, bottom = 4
    	int ind = 3-index % 4;
        for (ComplexBoardElement cbe : elements)    {
            if (cbe.isComplexWall())    {
                String direction = ((ComplexWall) cbe).getDirection();
                if(direction.equals("right") && ind == 0)   {
                    ((ComplexWall) cbe).toggleVisible();
                    setRight(otherBoardName);
                }
                else if(direction.equals("top") && ind == 1)    {
                    ((ComplexWall) cbe).toggleVisible();
                    setTop(otherBoardName);
                }
                else if(direction.equals("left") && ind == 2)    {
                    ((ComplexWall) cbe).toggleVisible();
                    setLeft(otherBoardName);
                }
                else if(direction.equals("bottom") && ind == 3)    {
                    ((ComplexWall) cbe).toggleVisible();
                    setBottom(otherBoardName);
                }
            }
        }
    }
    /**
     * connects this board to another board on the right
     * @param rightName the name of the board to be connected on the right
     */
    public void setRight(String rightName)   {
        this.rightName = rightName;
    }
    
    /**
     * connects this board to another board on the left
     * @param rightName the name of the board to be connected on the left
     */
    public void setLeft(String leftName)   {
        this.leftName = leftName;
    }
    
    /**
     * connects this board to another board on the top
     * @param rightName the name of the board to be connected on the top
     */
    public void setTop(String topName)   {
        this.topName = topName;
    }
    
    /**
     * connects this board to another board on the bottom
     * @param rightName the name of the board to be connected on the bottom
     */
    public void setBottom(String bottomName)   {
        this.bottomName = bottomName;
    }
    
    /**
     * gets the name of this board
     * @return a String representing the name of this board, can't be the same as any other boards on this server
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a ball to the list of balls in play on this board
     * 
     * @param ball
     *            : the ball that is coming into the board
     */
    public synchronized void enterBall(BallMessage message) {
        if (!this.name.equals(message.getDestination()))   {
            throw new RuntimeException("this ball got put in the wrong board! it was headed for " + message.getDestination() + " but it got put in " + this.name + "!!!");
        }
        Ball newball = message.getBall();
        if (message.getDirection().equals("left")) {
            //if it left through the left wall of the previous board, it should come in the rightmost side of the current board
            newball.place(20, newball.getY());
        }
        else if (message.getDirection().equals("right")) {
            //if it left through the right wall of the previous board, it should come in the leftmost side of the current board
            newball.place(1, newball.getY());
        }
        else if (message.getDirection().equals("top")) {
            //if it left through the top wall of the previous board, it should come in the bottom-most side of the current board
            newball.place(newball.getX(), 20);
        }
        else if (message.getDirection().equals("bottom")) {
            //if it left through the bottom wall of the previous board, it should come in the topmost side of the current board
            newball.place(newball.getX(), 1);
        }
        else  {
            throw new RuntimeException("you messed up in your declarations! uh oh! getDirection was something other than left right top or bottom!!!");
        }
        balls.add(newball);
    }

    /**
     * Removes a ball from the list of balls in play on this board
     * 
     * @param ball
     *            : the ball that is leaving the board
     */
    public synchronized Ball exitBall(Ball ball) {
        balls.remove(ball);
        return ball;
    }
    
    /**
     * Helper method for converting this board to a String
     * @return a 2D array of Strings that represent what String should go in each cell in the final 22x22 printed grid
     */
    private String[][] toStringHelper() {
        final int WIDTH = 22;
        final int HEIGHT = 22;
        String[][] toStringHelper = new String[WIDTH][HEIGHT];

        // make the walls and initialize empty array for all other elements
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (i == 0 || i == WIDTH - 1 || j == 0 || j == HEIGHT - 1) {
                    toStringHelper[i][j] = ".";
                }
                else {
                    toStringHelper[i][j] = " ";
                }
            }
        }

        // add neighboring wall names
        if (this.getLeftBoard() != null) {
            for (int jj = 0; jj < this.getLeftBoard().getName().length(); jj++) {
                toStringHelper[jj + 1][0] = this.getLeftBoard().getName()
                        .substring(jj, jj + 1);
            }
        }

        if (this.getRightBoard() != null) {
            for (int jj = 0; jj < this.getRightBoard().getName().length(); jj++) {
                toStringHelper[jj + 1][WIDTH-1] = this.getRightBoard().getName()
                        .substring(jj, jj + 1);
            }
        }
        if (this.getUpperBoard() != null) {
            for (int jj = 0; jj < this.getUpperBoard().getName().length(); jj++) {
                toStringHelper[0][jj + 1] = this.getUpperBoard().getName()
                        .substring(jj, jj + 1);
            }
        }
        if (this.getLowerBoard() != null) {
            for (int jj = 0; jj < this.getLowerBoard().getName().length(); jj++) {
                toStringHelper[WIDTH-1][jj + 1] = this.getLowerBoard().getName()
                        .substring(jj, jj + 1);
            }
        }

        // add elements
        for (ComplexBoardElement e : elements) {
            // flipper
            if (e.isFlipper()) {
                Flipper d = (Flipper) e;
                int x = (int) (d.getX() + 0.5) + 1 ;
                int y = (int) (d.getY() + 0.5) + 1;
                if (e.toString() == "|") {
                    toStringHelper[y][x] = d.toString();
                    toStringHelper[y + 1][x] = d.toString();
                }
                else {
                    toStringHelper[y][x] = d.toString();
                    if (d.isRight()) {
                        toStringHelper[y][x - 1] = d.toString();
                    }
                    toStringHelper[y][x + 1] = d.toString();
                }

            }
            // absorber
            else if (e.isAbsorber()) {
                Absorber f = (Absorber) e;
                for (int i = (int) f.getX() + 1; i < f.getLength() + f.getX() + 1; i++) {
                    for (int j = (int) f.getY() + 1; j < f.getHeight() + f.getY() + 1; j++) {

                        toStringHelper[j][i] = e.toString();
                    }
                }
            }
            else if (e.isComplexWall()) {
            	//Special
            }
            else {
                // square, circle, triangle bumper
                int x = (int) (e.getX() + 0.5)+1;
                int y = (int) (e.getY() + 0.5)+1;
                toStringHelper[y][x] = e.toString();
            }
        }
        //add the balls into the array
        //if there is already something there, add it to the closest square that it came from
        for (Ball ball : balls) {
            int x = (int) (ball.getX() + 0.5)+1;
            int y = (int) (ball.getY() + 0.5)+1;
            if (toStringHelper[y][x].equals(" "))   {
                toStringHelper[y][x] = "*";
            }
            else    {
                Angle direction = ball.getVelocity().angle();
                Angle zero = new Angle(0);
                //Angle fortyfive = new Angle(0.25*Math.PI);
                Angle ninety = new Angle(0.5* Math.PI);
                //Angle onethirtyfive = new Angle(0.75*Math.PI);
                Angle oneeighty = new Angle(Math.PI);
                //Angle twotwentyfive = new Angle(1.25*Math.PI);
                Angle twoseventy = new Angle(1.5*Math.PI);
                //Angle threefifteen = new Angle(1.75*Math.PI);
                Angle threesixty = new Angle(2*Math.PI);
                //if it's coming from the top right, move it to the top right
                if (direction.compareTo(zero) == 1 && direction.compareTo(ninety) <= 0) {
                    toStringHelper[y-1][x+1] = "*";
                }
                //if it's coming from the top left, move it to the top left
                else if (direction.compareTo(ninety) == 1 && direction.compareTo(oneeighty) <= 0)   {
                    toStringHelper[y-1][x-1] = "*";
                }
                //if it's coming from the bottom left, move it to the bottom left
                else if (direction.compareTo(oneeighty) == 1 && direction.compareTo(twoseventy) <= 0)   {
                    toStringHelper[y+1][x-1] = "*";
                }
                //if it's coming from the bottom right, move it to the bottom right
                else if (direction.compareTo(twoseventy) == 1 && direction.compareTo(threesixty) <= 0)   {
                    toStringHelper[y+1][x+1] = "*";
                }
            }
        }
        return toStringHelper;
    }
    

    /**
     * the toString() method
     * 
     * @return A string representation of the board
     */
    public String toString() {
        final int WIDTH = 22;
        String[][] strArr = toStringHelper();
        String str = "";
        for (int i = 0; i < WIDTH ; i++) {
            for (int j = 0; j < WIDTH; j++) {
                str += strArr[i][j];
            }
            str += "\n";
        }
        return str;
    }

    /**
     * Mutates the board and the board elements by iterating through all the components of the board 
     * and performing the correct operations
     */

    public void step() {
        //rotates any flippers that might be on the board if necessary
        List<ComplexBoardElement> actionTriggers = new ArrayList<ComplexBoardElement>();
        for (ComplexBoardElement gadget : elements) {
            if (gadget.isFlipper())   {
                gadget = (Flipper)(gadget);
                if(((Flipper) gadget).getRotating()) {
                    if (((Flipper) gadget).getRotated() == 90.0 || ((Flipper) gadget).getRotated() == 0.0)  {
                        ((Flipper) gadget).stopRotating();
                        ((Flipper) gadget).resetRotated();
                    }
                    else    {
                        ((Flipper) gadget).rotateStep(0.04/fps);
                    }
                }
            }
        }
        //Now moves the balls forward and checks for collisions
        for (Ball ball : balls) {
            boolean firstElt = true;
            ComplexBoardElement closestComplexPart = null;
            for (ComplexBoardElement elt : elements) {
            	if (firstElt || closestComplexPart.timeUntilCollision(ball) > elt.timeUntilCollision(ball)
            			&& elt.timeUntilCollision(ball) > 0.0) {
            		firstElt = false;
            		closestComplexPart = elt;
            	}
            }
            if (closestComplexPart.timeUntilCollision(ball) < 0.04 / fps) {
            	ball.step(closestComplexPart.timeUntilCollision(ball), this.gravity, this.mu1, this.mu2);
            	if (!closestComplexPart.checkRemove())
            		closestComplexPart.bounce(ball);
                //Check if the ball hit an invisible wall
                //If it did, add it to the exitBalls list then remove it from this board's list of balls
                
            	else   {
                    ComplexWall invisWall = (ComplexWall)(closestComplexPart);
                    if (invisWall.getDirection().equals("left")) {
                        exitBalls.add(new BallMessage(this.exitBall(ball), this.leftName, "left"));
                    }
                    else if (invisWall.getDirection().equals("right")) {
                        exitBalls.add(new BallMessage(this.exitBall(ball), this.rightName, "right"));
                    }
                    else if (invisWall.getDirection().equals("top")) {
                        exitBalls.add(new BallMessage(this.exitBall(ball), this.topName, "top"));
                    }
                    else if (invisWall.getDirection().equals("bottom")) {
                        exitBalls.add(new BallMessage(this.exitBall(ball), this.bottomName, "bottom"));
                    }

                    else    {
                        throw new RuntimeException("you initialized your boards wrong!");
                    }
                }
            	//The closestComlexPart was just hit, so call its trigger.
                actionTriggers.add(closestComplexPart);
            }
            //If no collisions, step forward a bit
            else {
                ball.step(0.04/fps, this.gravity, this.mu1, this.mu2);
            }
        }
        //Perform all triggered actions.
        for (ComplexBoardElement actionTrigger : actionTriggers) {
            List<ComplexBoardElement> actionables = triggers.get(actionTrigger);
            if (actionables != null) {
	            for (ComplexBoardElement actionElt : actionables) {
	                actionElt.action();
	            }
            }
        }
        
    }
    
    /**
     * Extracts the list of BallMessages for the balls that need to leave the board
     * empties exitBalls while it does so
     * @return a list of BallMessages for each of the balls that needs to leave the board
     */
    public List<BallMessage> extractExitBalls() {
        List<BallMessage> extractBalls = new ArrayList<BallMessage>();
        while(this.exitBalls != null && this.exitBalls.size() > 0)  {
            extractBalls.add(exitBalls.remove(0));
        }
        return extractBalls;
    }

    /**
     * @param side
     *            : the number that corresponds to the side that
     */
    public void setNeighbor(Board board, Side side) {
        if (side == Side.LEFT)
            leftboard = board;
        if (side == Side.RIGHT)
            rightboard = board;
        if (side == Side.TOP)
            topboard = board;
        if (side == Side.BOTTOM)
            bottomboard = board;
    }

    /**
     * Adds a trigger to the list of triggers
     * 
     * @param elt
     *            - a ComplexBoardElement that triggers something
     * @param elt1
     *            - a ComplexBoardElement that is triggered by the first
     *            element.
     */
    public void addTrigger(ComplexBoardElement elt, ComplexBoardElement elt1) {
    	List<ComplexBoardElement> elements = triggers.get(elt);
    	if (elements == null) {
    		elements = new ArrayList<ComplexBoardElement>();
    	}
    	elements.add(elt1);
        triggers.put(elt, elements);
    }
}