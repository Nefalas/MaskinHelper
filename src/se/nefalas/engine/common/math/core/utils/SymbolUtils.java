package se.nefalas.engine.common.math.core.utils;

import se.nefalas.engine.common.math.core.Constant;
import se.nefalas.engine.common.math.core.Symbol;

public class SymbolUtils {

    public static boolean areConstants(Symbol ...symbols) {
        for (Symbol symbol : symbols) {
            if (!(symbol instanceof Constant)) {
                return  false;
            }
        }

        return true;
    }
}
