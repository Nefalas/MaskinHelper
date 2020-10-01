package se.nefalas.engine.common.math.core.operations;

import se.nefalas.engine.common.math.core.Symbol;

import java.util.Arrays;

public interface Operation extends Symbol {

    Symbol apply(Symbol firstOperand, Symbol secondOperand) throws OperationException;

    static Type[] getHighPriorityOperationsTypes() {
        return new Type[] {
                Type.MULTIPLICATION,
                Type.DIVISION
        };
    }

    static Type[] getLowPriorityOperationsTypes() {
        return new Type[] {
                Type.ADDITION,
                Type.SUBTRACTION
        };
    }

    static boolean isHighPriorityOperation(Type type) {
        return Arrays.asList(getHighPriorityOperationsTypes()).contains(type);
    }

    static boolean isLowPriorityOperation(Type type) {
        return Arrays.asList(getLowPriorityOperationsTypes()).contains(type);
    }
}
