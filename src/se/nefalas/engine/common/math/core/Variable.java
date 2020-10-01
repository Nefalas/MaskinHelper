package se.nefalas.engine.common.math.core;

public class Variable implements Symbol {

    private String name;
    private Constant value = null;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean isValueContainer() {
        return true;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public String getSymbolName() {
        return "Variable";
    }

    @Override
    public String toString() {
        return name;
    }
}
