package se.nefalas.engine.common.math.core.operations;

import se.nefalas.engine.common.math.core.Constant;
import se.nefalas.engine.common.math.core.Symbol;
import se.nefalas.engine.common.math.core.utils.SymbolUtils;

public class Division implements Operation {

    private Symbol firstOperand;
    private Symbol secondOperand;

    public Division(Symbol firstOperand, Symbol secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    @Override
    public Symbol apply(Symbol firstOperand, Symbol secondOperand) throws OperationException {
        if (SymbolUtils.areConstants(firstOperand, secondOperand)) {
            return divideConstants((Constant) firstOperand, (Constant) secondOperand);
        }

        throw new OperationException(String.format("Could not divide Symbol %s by Symbol %s",
                firstOperand.getSymbolName(),secondOperand.getSymbolName()));
    }

    private Constant divideConstants(Constant firstConstant, Constant secondConstant) throws OperationException {
        if (secondConstant.getValue() == 0) {
            throw new OperationException("Cannot divide by 0");
        }

        double result = firstConstant.getValue() / secondConstant.getValue();

        return new Constant(result);
    }

    @Override
    public boolean isValueContainer() {
        return false;
    }

    @Override
    public String getSymbolName() {
        return "Division";
    }

    @Override
    public String toString() {
        return String.valueOf(Symbol.C_DIVIDE);
    }
}
