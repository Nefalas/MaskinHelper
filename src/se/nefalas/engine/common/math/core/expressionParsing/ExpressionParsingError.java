package se.nefalas.engine.common.math.core.expressionParsing;

public class ExpressionParsingError extends Exception {

    ExpressionParsingError(String message, Throwable cause) {
        super("Expression Parsing Exception: " + message, cause);
    }

    ExpressionParsingError(String message) {
        this(message, null);
    }
}