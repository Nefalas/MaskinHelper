package se.nefalas.engine.common.math.core;

public class Constant implements Symbol {

    private double value;

    public Constant(double value) {
        this.value = value;
    }

    public Constant(float value) {
        this((double) value);
    }

    public Constant(int value) {
        this((double) value);
    }

    @Override
    public boolean isValueContainer() {
        return true;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValue(float value) {
        setValue((double) value);
    }

    public void setValue(int value) {
        setValue((double) value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String getSymbolName() {
        return "Constant";
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
