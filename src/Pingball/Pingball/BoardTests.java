package Pingball;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

/**
 * Testing Strategy:

 * toString Method:
 * -print board with one ball 
 * -print board with circle, triangle, square
 * -print board with flipper
 * -print board with absorber
 * -print board with all corners filled
 * -print board with neighbor names
 *     left, right, top, bottom
 * 
 * Functionality of Board Elements (gravity but no friction):
 * -ball speeds up as it drops
 * -ball slows down as it goes up
 * 
 * Board Elements (friction but no gravity)
 * -ball slows down as it hits board elements
 *
 * @author Nicole
 *
 */
public class BoardTests {
    
    @Test
    public void testToStringBoardBumpers(){
        SquareBumper square = new SquareBumper(1, 1, "sq");
        ComplexCircleBumper circle = new ComplexCircleBumper(new CircleBumper(new Circle(2, 2, 1.0)), "circle");
        TriangleBumper triangle = new TriangleBumper(15, 8, "triangle", 90);
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(square, circle, triangle));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        assertTrue(board.toString().charAt(48) == '#');
        assertTrue(board.toString().charAt(72) == 'O');
        assertTrue(board.toString().charAt(223) == '\\');
    }
    
    @Test
    public void testToStringFlipper(){
        Flipper flipper = new Flipper("flipper", false, 90, new Wall(new LineSegment(5, 5, 5, 6), 1.0, 2.0), 6*Math.PI);

        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(flipper));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        
        assertTrue(board.toString().charAt(144) == '-');
        assertTrue(board.toString().charAt(145) == '-');
    }

    @Test
    public void testToStringBoardAbsorber(){
        Absorber absorber = new Absorber(1, 7, "abs", 10, 2);
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(absorber));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);

        assertTrue(board.toString().substring(186, 196).equals("=========="));
        assertTrue(board.toString().substring(209, 219).equals("=========="));

    } 
    @Test
    public void testToStringAllCorners(){
        ComplexBoardElement square1 = (ComplexBoardElement) new SquareBumper(0, 0, "sq");
        ComplexBoardElement square2 = (ComplexBoardElement) new SquareBumper(0, 19, "sq");
        ComplexBoardElement square3 = (ComplexBoardElement) new SquareBumper(19, 0, "sq");
        ComplexBoardElement square4 = (ComplexBoardElement) new SquareBumper(19, 19, "sq");
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(square1, square2, square3, square4));
        List<Ball> balls = Arrays.asList();
        
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        assertTrue(board.toString().charAt(24) == '#');
        assertTrue(board.toString().charAt(43) == '#');
        assertTrue(board.toString().charAt(461) == '#');
        assertTrue(board.toString().charAt(480) == '#');
    }
    
    @Test
    public void testToStringBall(){
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>();
        List<Ball> balls = new ArrayList<Ball>(Arrays.asList(new Ball("hi", new Circle(1, 1, 1.0), new Vect(1, 0))));
        
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        assertTrue(board.toString().charAt(48) == '*');
        
    }
    @Test
    public void testToStringNeighborLeft(){
        Flipper flipper = new Flipper("flipper", false, 90, new Wall(new LineSegment(5, 5, 5, 6), 1.0, 2.0), 6*Math.PI);
        Absorber absorber = new Absorber(1, 7, "abs", 10, 2);
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(flipper, absorber));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        
        
        List<ComplexBoardElement> elements1 = new ArrayList<ComplexBoardElement>();
        List<Ball> balls1 = Arrays.asList();
        Board board1 = new Board(50, "hello", 25.0, 0.025, 0.025, balls1, elements1);
        board.setNeighbor(board1, Side.LEFT);
        
        //test name of neighbor
        assertTrue(board.toString().charAt(23) == 'h');
        assertTrue(board.toString().charAt(46) == 'e');
        assertTrue(board.toString().charAt(69) == 'l');
        assertTrue(board.toString().charAt(92) == 'l');
        assertTrue(board.toString().charAt(115) == 'o');
    }
    
    @Test
    public void testToStringNeighborRight(){
        Flipper flipper = new Flipper("flipper", false, 90, new Wall(new LineSegment(5, 5, 5, 6), 1.0, 2.0), 6*Math.PI);
        Absorber absorber = new Absorber(1, 7, "abs", 10, 2);
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(flipper, absorber));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        
        
        List<ComplexBoardElement> elements1 = new ArrayList<ComplexBoardElement>();
        List<Ball> balls1 = new ArrayList<Ball>();
        Board board1 = new Board(50, "hello", 25.0, 0.025, 0.025, balls1, elements1);
        board.setNeighbor(board1, Side.RIGHT);
        
        //test name of neighbor
        assertTrue(board.toString().charAt(44) == 'h');
        assertTrue(board.toString().charAt(67) == 'e');
        assertTrue(board.toString().charAt(90) == 'l');
        assertTrue(board.toString().charAt(113) == 'l');
        assertTrue(board.toString().charAt(136) == 'o');

    }
    
    public void testToStringNeighborTop(){
        Flipper flipper = new Flipper("flipper", false, 90, new Wall(new LineSegment(5, 5, 5, 6), 1.0, 2.0), 6*Math.PI);
        Absorber absorber = new Absorber(1, 7, "abs", 10, 2);
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(flipper, absorber));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        
        
        List<ComplexBoardElement> elements1 = new ArrayList<ComplexBoardElement>();
        List<Ball> balls1 = new ArrayList<Ball>();
        Board board1 = new Board(50, "hello", 25.0, 0.025, 0.025, balls1, elements1);
        board.setNeighbor(board1, Side.TOP);
        
        //test name of neighbor
        assertTrue(board.toString().charAt(1) == 'h');
        assertTrue(board.toString().charAt(2) == 'e');
        assertTrue(board.toString().charAt(3) == 'l');
        assertTrue(board.toString().charAt(4) == 'l');
        assertTrue(board.toString().charAt(5) == 'o');

    }
    
    @Test
    public void testToStringNeighborBottom(){
        Flipper flipper = new Flipper("flipper", false, 90, new Wall(new LineSegment(5, 5, 5, 6), 1.0, 2.0), 6*Math.PI);
        Absorber absorber = new Absorber(1, 7, "abs", 10, 2);
        
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>(Arrays.asList(flipper, absorber));
        List<Ball> balls = Arrays.asList();
        Board board = new Board(50, "hi!", 25.0, 0.025, 0.025, balls, elements);
        
        
        List<ComplexBoardElement> elements1 = new ArrayList<ComplexBoardElement>();
        List<Ball> balls1 = new ArrayList<Ball>();
        Board board1 = new Board(50, "hello", 25.0, 0.025, 0.025, balls1, elements1);
        board.setNeighbor(board1, Side.BOTTOM);
        
        //test name of neighbor
        assertTrue(board.toString().charAt(484) == 'h');
        assertTrue(board.toString().charAt(485) == 'e');
        assertTrue(board.toString().charAt(486) == 'l');
        assertTrue(board.toString().charAt(487) == 'l');
        assertTrue(board.toString().charAt(488) == 'o');

    }
    
    
    
    //gravity, no friction
    @Test
    public void testGravityDownward(){
        //ball's velocity should be increasing as the ball drops
        List<ComplexBoardElement> elements1 = new ArrayList<ComplexBoardElement>();
        Ball hi = new Ball("hi", new Circle(1, 1, 1.0), new Vect(0, 2));
        List<Ball> balls1 = Arrays.asList(hi);
        Board board1 = new Board(50, "hello", 25.0, 0, 0, balls1, elements1);
        board1.step();
        assertTrue(hi.getVelocity().length() > 2);
        board1.step();
        assertTrue(hi.getVelocity().length() > 2.02);
        board1.step();
        assertTrue(hi.getVelocity().length() > 2.04);
        board1.step();
        assertTrue(hi.getVelocity().length() > 2.06);
        board1.step();
        assertTrue(hi.getVelocity().length() > 2.08);
        //comment about gravity

    }
    
    @Test
    public void testGravityUpward(){
        //ball's velocity should be decreasing as the ball goes upward
        List<ComplexBoardElement> elements1 = new ArrayList<ComplexBoardElement>();
        Ball hi = new Ball("hi", new Circle(1, 1, 1.0), new Vect(0, -2));
        List<Ball> balls1 = Arrays.asList(hi);
        Board board1 = new Board(50, "hello", 25.0, 0, 0, balls1, elements1);
        board1.step();
        assertTrue(hi.getVelocity().y() > -2);
        board1.step();
        assertTrue(hi.getVelocity().y() > -1.98);
        board1.step();
        assertTrue(hi.getVelocity().y() > -1.96);
        board1.step();
        assertTrue(hi.getVelocity().y() > -1.94);
        board1.step();
        assertTrue(hi.getVelocity().y() > -1.92);

    }

    
    //friction, no gravity
    public void testFriction(){
        
    }
}
