package Pingball;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import physics.*;

/**
 * 
 * Main is used to test the ANTLR lexer and parser. The grammar defined
 * in this project is stored in Expression.g4. It is used to parse the different
 * types of board inputs. Uncomment specific sections of the code below to debug
 * in different modes.
 */
public class Main {

    /**
     * @param file - the file to be parsed
     * @throws IOException
     */
    public static Board parse(File file) throws IOException {
        // Create a stream of tokens using the lexer.
        FileInputStream fis = new FileInputStream(file);
        CharStream stream = new ANTLRInputStream(fis);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        // Feed the tokens into the parser.
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();

        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.input(); // "file" is the starter rule.

        // *** debugging option #1: print the tree to the console
        // System.err.println(tree.toStringTree(parser));

        // *** debugging option #2: show the tree in a window
        RuleContext rootContext = (RuleContext) tree;
        rootContext.inspect(parser);

        // *** debugging option #3: walk the tree with a listener
        // new ParseTreeWalker().walk(new PrintEverything(), tree);

        // Construct the board by walking over the parse tree with a listener.
        ParseTreeWalker walker = new ParseTreeWalker();
        MakeExpression listener = new MakeExpression();
        walker.walk(listener, tree);

        listener.board = new Board(50, listener.name, listener.gravity, listener.friction1, listener.friction2, listener.balls, listener.elements);
        

        for(String name: listener.triggersAndActions.keySet()){
            ComplexBoardElement trigger = null;
            ComplexBoardElement action = null;
            
            for(ComplexBoardElement e: listener.board.getElements()){
                if(e.getName().equals(name)){
                    trigger = e;
                }
                if (e.getName().equals(listener.triggersAndActions.get(name))){
                    action = e;
                }
            }
            listener.board.addTrigger(trigger, action);
        }
        // return the value that the listener created
        System.out.println(listener.board.toString());
        return listener.board;
    }

    /**
     * Makes the Expression from the file.
     * @author kaixiao, nicolepan, victorz
     *
     */
    private static class MakeExpression extends ExpressionBaseListener {
        Board board;
        List<Ball> balls = new ArrayList<Ball>();
        List<ComplexBoardElement> elements = new ArrayList<ComplexBoardElement>();
        HashMap<String,String> triggersAndActions = new HashMap<String, String>();
        String name;
        int width;
        int height;
        double friction1;
        double friction2;
        double gravity;

        /**
         * Parse the first line.
         */
        public void exitOpener(ExpressionParser.OpenerContext ctx) {
            if (ctx.parameter().size() == 4) {
                name = ctx.parameter().get(0).value().getText();
                gravity = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                friction1 = Double.parseDouble(ctx.parameter().get(2).value()
                        .getText());
                friction2 = Double.parseDouble(ctx.parameter().get(3).value()
                        .getText());

            }
            else if (ctx.parameter().size() == 3) {
                name = null;
                gravity = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                friction1 = Double.parseDouble(ctx.parameter().get(2).value()
                        .getText());
                friction2 = Double.parseDouble(ctx.parameter().get(3).value()
                        .getText());
            }
            else if (ctx.parameter().size() == 1) {
                name = ctx.parameter().get(0).value().getText();
                gravity = 25.0;
                friction1 = 0.025;
                friction2 = 0.025;
            }
            else {
                name = null;
                gravity = 25.0;
                friction1 = 0.025;
                friction2 = 0.025;
            }
        }

        /**
         * For every line that is a "definition" (defines a gadget), parse
         * that line.
         */
        public void exitDefinition(ExpressionParser.DefinitionContext ctx) {
            String type = ctx.type().getText();

            if (type.equals("ball")) {

                String ballName = ctx.parameter().get(0).value().getText();
                double x = Double.parseDouble(ctx.parameter().get(1).value()
                        .getText());
                double y = Double.parseDouble(ctx.parameter().get(2).value()
                        .getText());
                double xV = Double.parseDouble(ctx.parameter().get(3).value()
                        .getText());
                double yV = Double.parseDouble(ctx.parameter().get(4).value()
                        .getText());

                balls.add(new Ball(ballName, new Circle(x, y, 1.0), new Vect(
                        xV, yV)));
            }
            else if (type.equals("squareBumper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                elements.add(new SquareBumper((double) x, (double) y, name));
            }
            else if (type.equals("circleBumper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                elements.add(new ComplexCircleBumper(new CircleBumper(
                        new Circle(x, y, 1.0)), name));
            }

            else if (type.equals("triangleBumper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                Integer o = Integer.parseInt(ctx.parameter().get(3).value()
                        .getText());
                elements.add(new TriangleBumper((double) x, (double) y, name, o));
            }
            else if (type.equals("leftFlipper") || type.equals("rightFlipper")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                Integer o = Integer.parseInt(ctx.parameter().get(3).value()
                        .getText());
                if (o == 0 || o == 90) {
                    elements.add(new Flipper(name, type.equals("rightFlipper"),
                            o, new Wall(new LineSegment(x, y, x, y + 1), 1.0,
                                    2.0), 6 * Math.PI));
                }
                else {
                    elements.add(new Flipper(name, type.equals("rightFlipper"),
                            o, new Wall(new LineSegment(x, y, x + 1, y), 1.0,
                                    2.0), 6 * Math.PI));
                }
            }
            else if (type.equals("absorber")) {
                String name = ctx.parameter().get(0).value().getText();
                Integer x = Integer.parseInt(ctx.parameter().get(1).value()
                        .getText());
                Integer y = Integer.parseInt(ctx.parameter().get(2).value()
                        .getText());
                Integer width = Integer.parseInt(ctx.parameter().get(3).value()
                        .getText());
                Integer height = Integer.parseInt(ctx.parameter().get(4)
                        .value().getText());

                elements.add(new Absorber(x, y, name, width, height));
            }
            else if (type.equals("fire")){
                String trigger = ctx.parameter().get(0).value().getText();
                String action = ctx.parameter().get(1).value().getText();
                triggersAndActions.put(trigger, action);
            }

        }
    }


    public static void main(String[] args) throws IOException {
        File file = new File("sampleBoard3.pb");
        Board test = parse(file);

    }
}