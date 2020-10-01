package se.nefalas.tests.engine.common.math.core.expressionParsing;

import org.junit.Test;
import se.nefalas.engine.common.math.core.expressionParsing.ExpressionParser;

import static org.junit.Assert.*;

public class ExpressionParserTest {

    @Test
    public void parse() {
        try {
            ExpressionParser.parse("1+ 3 - 7");
            ExpressionParser.parse("-89 * a - 7 / 2");
            ExpressionParser.parse("1235bc - (9y /3)");
            ExpressionParser.parse("a - b + 2c");
            ExpressionParser.parse("96c*32x+c+v/(q-9b)");
            ExpressionParser.parse("7a(9 + 98bc + 9(a + b)) - 93");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}