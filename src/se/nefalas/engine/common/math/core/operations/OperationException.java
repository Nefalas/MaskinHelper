package se.nefalas.engine.common.math.core.operations;

class OperationException extends Exception {

    OperationException(String message, Throwable cause) {
        super("Operation Exception: " + message, cause);
    }

    OperationException(String message) {
        this(message, null);
    }
}
