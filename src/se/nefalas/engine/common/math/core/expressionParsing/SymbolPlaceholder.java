package se.nefalas.engine.common.math.core.expressionParsing;

import se.nefalas.engine.common.math.core.Constant;
import se.nefalas.engine.common.math.core.Expression;
import se.nefalas.engine.common.math.core.Symbol;
import se.nefalas.engine.common.math.core.Variable;
import se.nefalas.engine.common.math.core.operations.Operation;

class SymbolPlaceholder {

    private final Symbol.Type type;
    private Symbol symbol;

    SymbolPlaceholder(Symbol.Type type) {
        this.type = type;
    }

    private SymbolPlaceholder(Symbol.Type type, Symbol symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    SymbolPlaceholder(Expression expression) {
        this(Symbol.Type.EXPRESSION, expression);
    }

    SymbolPlaceholder(Operation operation) {
        this(Symbol.Type.OPERATION, operation);
    }

    boolean containsSymbol() {
        return symbol != null;
    }

    Symbol getSymbol() {
        return symbol;
    }

    Symbol.Type getType() {
        return type;
    }

    public String toString() {
        if (containsSymbol()) {
            return symbol.toString();
        } else {
            return String.valueOf(type.symbol);
        }
    }

    static SymbolPlaceholder parseSymbol(String symbol) {
        if (symbol.length() > 0) {
            char firstChar = symbol.charAt(0);
            switch (firstChar) {
                case Symbol.C_OPEN_PARENTHESIS:
                    return new SymbolPlaceholder(Symbol.Type.OPEN_PARENTHESIS);
                case Symbol.C_CLOSE_PARENTHESIS:
                    return new SymbolPlaceholder(Symbol.Type.CLOSE_PARENTHESIS);
                case Symbol.C_ADD:
                    return new SymbolPlaceholder(Symbol.Type.ADDITION);
                case Symbol.C_SUBTRACT:
                    return new SymbolPlaceholder(Symbol.Type.SUBTRACTION);
                case Symbol.C_MULTIPLY:
                    return new SymbolPlaceholder(Symbol.Type.MULTIPLICATION);
                case Symbol.C_DIVIDE:
                    return new SymbolPlaceholder(Symbol.Type.DIVISION);
                default:
                    return parseValue(symbol);
            }
        }

        return null;
    }

    private static SymbolPlaceholder parseValue(String value) {
        try {
            // If value is numeric, store it as a constant
            double constantValue = Double.parseDouble(value);
            Constant constant = new Constant(constantValue);

            return new SymbolPlaceholder(Symbol.Type.CONSTANT, constant);
        } catch (NumberFormatException e) {
            // If value is a non numeric string, create a variable with that name
            Variable variable = new Variable(value);

            return new SymbolPlaceholder(Symbol.Type.VARIABLE, variable);
        }
    }
}
