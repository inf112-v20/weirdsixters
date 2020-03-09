package inf112.skeleton.app;

import java.util.ArrayList;

public final class ListUtils {
    private ListUtils() {}

    /**
     * Pop (remove and return) the last element of @stack.
     */
    public static <T> T pop(ArrayList<T> stack) {
        assert(!stack.isEmpty());
        int hi = stack.size() - 1;
        T item = stack.get(hi);
        stack.remove(hi);
        return item;
    }
}
