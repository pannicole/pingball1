// Generated from Expression.g4 by ANTLR 4.0

package Pingball;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface ExpressionListener extends ParseTreeListener {
	void enterOpener(ExpressionParser.OpenerContext ctx);
	void exitOpener(ExpressionParser.OpenerContext ctx);

	void enterInput(ExpressionParser.InputContext ctx);
	void exitInput(ExpressionParser.InputContext ctx);

	void enterParameter(ExpressionParser.ParameterContext ctx);
	void exitParameter(ExpressionParser.ParameterContext ctx);

	void enterNewline(ExpressionParser.NewlineContext ctx);
	void exitNewline(ExpressionParser.NewlineContext ctx);

	void enterDefinition(ExpressionParser.DefinitionContext ctx);
	void exitDefinition(ExpressionParser.DefinitionContext ctx);

	void enterValue(ExpressionParser.ValueContext ctx);
	void exitValue(ExpressionParser.ValueContext ctx);

	void enterLine(ExpressionParser.LineContext ctx);
	void exitLine(ExpressionParser.LineContext ctx);

	void enterType(ExpressionParser.TypeContext ctx);
	void exitType(ExpressionParser.TypeContext ctx);

	void enterComment(ExpressionParser.CommentContext ctx);
	void exitComment(ExpressionParser.CommentContext ctx);
}