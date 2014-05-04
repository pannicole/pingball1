grammar Expression;

// This puts a Java package statement at the top of the output Java files.
@header {
package Pingball;
}

// This adds code to the generated lexer and parser.
@members {
    /**
     * Call this method to have the lexer or parser throw a RuntimeException if
     * it encounters an error.
     */
    public void reportErrorsAsExceptions() {
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 * *** ANTLR requires tokens to be CAPITALIZED, like START_ITALIC, END_ITALIC, and TEXT.
 */
 
WHITESPACE : [ \t]+ -> skip ;
NEWLINE1 : '\n' ;
NEWLINE2 : '\r' ;
EQUALS : '=' ;

BOARD_WORD : 'board' ;
BALL_WORD : 'ball' ;
FIRE_WORD : 'fire' ;
BOARDELEMENT : 'squareBumper' | 'circleBumper' | 'triangleBumper' | 'rightFlipper' | 'leftFlipper' | 'absorber' ;
FIELD : 'name' | 'gravity' | 'x' | 'y' | 'friction1' | 'friction2' | 'width' | 'height' | 'trigger' | 'action' | 'xVelocity' | 'yVelocity' | 'orientation' ;
	
INTEGER : [0-9]+ ;
FLOAT: '-'?[0-9]*('.'[0-9]+);
NAME : [A-Za-z_][A-Za-z_0-9]* ;
fragment TEXT : ~(['\r'|'\n'])+ ;
COMM: '#' TEXT* ;

//NUMBER : INTEGER | FLOAT ;


/*
 * These are the parser rules. They define the structures used by the parser.
 * *** ANTLR requires grammar nonterminals to be lowercase, like html, normal, and italic.
 */

input: opener line* ;
line: definition | comment | newline;
opener: BOARD_WORD parameter+ newline ;
definition: type parameter+ newline ;
comment: COMM newline ;
newline: NEWLINE1 | NEWLINE2 ;

type: BOARDELEMENT | BALL_WORD | FIRE_WORD ;
parameter: FIELD EQUALS value ;
value: FLOAT | INTEGER | NAME ;