package se.nefalas.engine.common.math.core.expressionParsing;

import se.nefalas.engine.common.math.core.Expression;
import se.nefalas.engine.common.math.core.Symbol;
import se.nefalas.engine.common.math.core.operations.*;
import se.nefalas.engine.common.math.core.utils.ListUtils;
import se.nefalas.engine.common.utils.CharUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExpressionParser {

    private static List<Expression> expressionStack;

    private static char[] buffer;
    private static Expression parsedExpression;

    public static Expression parse(String expression) throws ExpressionParsingError {
        reset();

        List<String> symbols = splitStringSymbols(expression);
        List<SymbolPlaceholder> placeholders = convertToPlaceholders(symbols);

        insertMissingMultiplications(placeholders);
        replaceAllParenthesis(placeholders);

        for (SymbolPlaceholder placeholder : placeholders) {
            System.out.print(placeholder.toString() + " ");
        }
        System.out.println();

        return parsedExpression;
    }

    private static List<String> splitStringSymbols(String expression) {
        List<String> symbols = new ArrayList<>();

        char[] chars = expression.toCharArray();
        for (int i = 0; i < expression.length(); i++) {
            char c = chars[i];
            if (CharUtils.isOneOf(c, Symbol.getSymbolsChars())) {
                symbols.add(String.valueOf(c));
            } else if (Character.isDigit(c)) {
                StringBuilder builder = new StringBuilder();
                builder.append(c);
                while (i + 1 < chars.length && (Character.isDigit(chars[i + 1]) || chars[i + 1] == '.')) {
                    builder.append(chars[i + 1]);
                    i++;
                }
                symbols.add(builder.toString());
            } else if (Character.isAlphabetic(c)) {
                StringBuilder builder = new StringBuilder();
                builder.append(c);
                while (i + 1 < chars.length && Character.isAlphabetic(chars[i + 1])) {
                    builder.append(chars[i + 1]);
                    i++;
                }
                symbols.add(builder.toString());
            }
        }

        return symbols;
    }

    private static List<SymbolPlaceholder> convertToPlaceholders(List<String> symbols) {
        return symbols.stream().map(SymbolPlaceholder::parseSymbol).collect(Collectors.toList());
    }

    private static void reset() {
        expressionStack = new ArrayList<>();
        buffer = new char[100];
        parsedExpression = new Expression();
    }

    private static void insertMissingMultiplications(List<SymbolPlaceholder> symbols) {
        if (symbols.size() < 2) {
            return;
        }

        for (int i = 1; i < symbols.size(); i++) {
            Symbol.Type firstSymbolType = symbols.get(i - 1).getType();
            Symbol.Type secondSymbolType = symbols.get(i).getType();

            if (firstSymbolType != Symbol.Type.OPEN_PARENTHESIS && secondSymbolType != Symbol.Type.CLOSE_PARENTHESIS &&
                    Symbol.isValueContainer(firstSymbolType) && Symbol.isValueContainer(secondSymbolType)) {
                symbols.add(i, new SymbolPlaceholder(Symbol.Type.MULTIPLICATION));
            }
        }
    }

    private static void replaceAllParenthesis(List<SymbolPlaceholder> symbols) {
        int parenthesisCount = countSymbols(symbols, Symbol.Type.OPEN_PARENTHESIS);
        for (int i = 0; i < parenthesisCount; i++) {
            replaceParenthesis(symbols);
        }
    }

    private static void replaceParenthesis(List<SymbolPlaceholder> symbols) {
        int openParenthesisIndex = -1;
        for (int i = 0; i < symbols.size(); i++) {
            SymbolPlaceholder symbol = symbols.get(i);
            if (symbol.getType() == Symbol.Type.OPEN_PARENTHESIS) {
                openParenthesisIndex = i;
            } else if (symbol.getType() == Symbol.Type.CLOSE_PARENTHESIS) {
                List<SymbolPlaceholder> parenthesisContent = ListUtils.removeRange(symbols, openParenthesisIndex, i + 1);
                ListUtils.trim(parenthesisContent, 1); // remove outer parenthesis
                Expression expression = parseExpression(parenthesisContent);
                symbols.add(openParenthesisIndex, new SymbolPlaceholder(expression));

                return;
            }
        }
    }

    private static Expression parseExpression(List<SymbolPlaceholder> symbols) {
        Expression expression = new Expression();
        replaceAllHighPriorityOperations(symbols);
        replaceAllLowPriorityOperations(symbols);
//
//        for (SymbolPlaceholder placeholder : symbols) {
//            System.out.print(placeholder.toString());
//        }

        return expression;
    }

    private static void replaceAllHighPriorityOperations(List<SymbolPlaceholder> symbols) {
        int operationsCount = countSymbols(symbols, Operation.getHighPriorityOperationsTypes());
        for (int i = 0; i < operationsCount; i++) {
            replaceOperation(symbols, Operation::isHighPriorityOperation);
        }
    }

    private static void replaceAllLowPriorityOperations(List<SymbolPlaceholder> symbols) {
        int operationsCount = countSymbols(symbols, Operation.getLowPriorityOperationsTypes());
        for (int i = 0; i < operationsCount; i++) {
            replaceOperation(symbols, Operation::isLowPriorityOperation);
        }
    }

    private static void replaceOperation(List<SymbolPlaceholder> symbols, Predicate<Symbol.Type> isPriority) {
        for (int i = 1; i < symbols.size() - 1; i++) {
            SymbolPlaceholder symbol = symbols.get(i);
            if (isPriority.test(symbol.getType())) {
                Operation operation = parseOperation(ListUtils.removeRange(symbols, i - 1, i + 2));
                symbols.add(i - 1, new SymbolPlaceholder(operation));
            }
        }
    }

    private static Operation parseOperation(List<SymbolPlaceholder> symbols) {
        if (symbols.size() != 3) {
            throw new RuntimeException("Could not parse operation, list contains " + symbols.size() + " symbols");
        }

        Symbol firstOperand = symbols.get(0).getSymbol();
        Symbol secondOperand = symbols.get(2).getSymbol();

        switch (symbols.get(1).getType()) {
            case MULTIPLICATION:
                return new Multiplication(firstOperand, secondOperand);
            case DIVISION:
                return new Division(firstOperand, secondOperand);
            case ADDITION:
                return new Addition(firstOperand, secondOperand);
            case SUBTRACTION:
                return new Subtraction(firstOperand, secondOperand);
        }

        return null;
    }

    private static int countSymbols(List<SymbolPlaceholder> symbols, Symbol.Type type) {
        int count = 0;
        for (SymbolPlaceholder symbol : symbols) {
            if (symbol.getType() == type) {
                count++;
            }
        }

        return count;
    }

    private static int countSymbols(List<SymbolPlaceholder> symbols, Symbol.Type[] types) {
        int count = 0;
        for (SymbolPlaceholder symbol : symbols) {
            if (Arrays.asList(types).contains(symbol.getType())) {
                count++;
            }
        }

        return count;
    }

}

