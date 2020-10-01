package se.nefalas.engine.common.math.core;

import java.util.Arrays;

public interface Symbol {

    char C_OPEN_PARENTHESIS = '(';
    char C_CLOSE_PARENTHESIS = ')';
    char C_ADD = '+';
    char C_SUBTRACT = '-';
    char C_MULTIPLY = '*';
    char C_DIVIDE = '/';

    boolean isValueContainer();
    String getSymbolName();
    String toString();

    static char[] getSymbolsChars() {
        return new char[] {
                C_OPEN_PARENTHESIS,
                C_CLOSE_PARENTHESIS,
                C_ADD,
                C_SUBTRACT,
                C_MULTIPLY,
                C_DIVIDE
        };
    }

    static Type[] getValueContainerTypes() {
        return new Type[] {
                Type.OPERATION,
                Type.EXPRESSION,
                Type.CONSTANT,
                Type.VARIABLE,
                Type.OPEN_PARENTHESIS,
                Type.CLOSE_PARENTHESIS
        };
    }

    static boolean isValueContainer(Type type) {
        return Arrays.asList(getValueContainerTypes()).contains(type);
    }

    enum Type {
        OPERATION,
        EXPRESSION,
        CONSTANT,
        VARIABLE,
        OPEN_PARENTHESIS(C_OPEN_PARENTHESIS),
        CLOSE_PARENTHESIS(C_CLOSE_PARENTHESIS),
        ADDITION(C_ADD),
        SUBTRACTION(C_SUBTRACT),
        MULTIPLICATION(C_MULTIPLY),
        DIVISION(C_DIVIDE);

        public final char symbol;

        Type(char symbol) {
            this.symbol = symbol;
        }

        Type() {
            this.symbol = ' ';
        }
    }
}
