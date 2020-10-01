package se.nefalas.engine.common.math.core.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static <T> List<T> removeRange(List<T> list, int startIndex, int endIndex) {
        List<T> removed = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            removed.add(list.remove(startIndex));
        }

        return removed;
    }

    public static <T> void trim(List<T> list, int count) {
        if (list.size() < count * 2) {
            return;
        }

        for (int i = 0; i < count; i++) {
            list.remove(0);
            list.remove(list.size() - 1);
        }
    }
}
