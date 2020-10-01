package se.nefalas.engine.common.math.core.brackets;

public class Parenthesis implements Bracket {

    @Override
    public boolean isValueContainer() {
        return false;
    }

    @Override
    public String getSymbolName() {
        return "Parenthesis";
    }
}
