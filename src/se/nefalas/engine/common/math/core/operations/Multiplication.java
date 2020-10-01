package se.nefalas.engine.common.math.core.operations;

import se.nefalas.engine.common.math.core.Constant;
import se.nefalas.engine.common.math.core.Symbol;
import se.nefalas.engine.common.math.core.utils.SymbolUtils;

public class Multiplication implements Operation {

    private Symbol firstOperand;
    private Symbol secondOperand;

    public Multiplication(Symbol firstOperand, Symbol secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    @Override
    public Symbol apply(Symbol firstOperand, Symbol secondOperand) throws OperationException {
        if (SymbolUtils.areConstants(firstOperand, secondOperand)) {
            return multiplyConstants((Constant) firstOperand, (Constant) secondOperand);
        }

        throw new OperationException(String.format("Could not multiply Symbol %s and Symbol %s",
                firstOperand.getSymbolName(),secondOperand.getSymbolName()));
    }

    private Constant multiplyConstants(Constant firstConstant, Constant secondConstant) {
        double result = firstConstant.getValue() * secondConstant.getValue();

        return new Constant(result);
    }

    @Override
    public boolean isValueContainer() {
        return false;
    }

    @Override
    public String getSymbolName() {
        return "Multiplication";
    }

    @Override
    public String toString() {
        return String.valueOf(Symbol.C_MULTIPLY);
    }
}
