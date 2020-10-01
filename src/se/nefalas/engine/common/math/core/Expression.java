package se.nefalas.engine.common.math.core;

import java.util.ArrayList;
import java.util.List;

public class Expression implements Symbol {

    private List<Symbol> symbols = new ArrayList<>();

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    @Override
    public boolean isValueContainer() {
        return true;
    }

    @Override
    public String getSymbolName() {
        return "Expression";
    }

    @Override
    public String toString() {
        return symbols.stream().reduce("", (subtotal, element) -> subtotal + " " + element.toString(), String::concat);
    }
}
