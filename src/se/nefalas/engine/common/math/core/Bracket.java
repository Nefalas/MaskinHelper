package se.nefalas.engine.common.math.core;

public class Bracket implements Symbol {

    @Override
    public boolean isValueContainer() {
        return false;
    }

    @Override
    public String getSymbolName() {
        return "Bracket";
    }
}
