package me.sulaxan.zombies.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Built on top of ArrayList, ConcurrentList provides the ability
 * to use this List concurrently.
 *
 * @param <E> The type of element to store.
 */
public class ConcurrentList<E> extends ArrayList<E> {

    /**
     * Creates a new List that will prevent concurrency issues.
     *
     * @return A concurrent safe instance of the List.
     */
    public List<E> getConcurrentSafe() {
        return new ArrayList<>(this);
    }
}
