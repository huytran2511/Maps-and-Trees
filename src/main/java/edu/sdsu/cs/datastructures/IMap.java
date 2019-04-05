package edu.sdsu.cs.datastructures;

/**
 * The interface for a basic Map/Dictionary Abstract Data Type. This structure
 * associates keys and values.
 *
 * @param <K> The object type to use as the Key
 * @param <V> The generic object type to use as Values
 */
public interface IMap<K extends Comparable<K>, V> {

    boolean contains(K key);
    /**
     * Indicates if the map contains the object identified by the key inside.
     *
     * @param key The object to compare against
     * @return true if the parameter object appears in the structure
     */

    boolean add(K key, V value);
    /**
     * Adds the given key/value pair to the dictionary.
     *
     * @param key
     * @param value
     * @return false if the dictionary is full, or if the key is a duplicate.
     * Returns true if addition succeeded.
     */

    V delete(K key);
    /**
     * Deletes the key/value pair identified by the key parameter.
     *
     * @param key
     * @return The previous value associated with the deleted key or null if not
     * present.
     */

    V getValue(K key);
    /**
     * Retreives, but does not remove, the value associated with the provided
     * key.
     *
     * @param key The key to identify within the map.
     * @return The value associated with the indicated key.
     */

    K getKey(V value);
    /**
     * Returns a key in the map associated with the provided value.
     *
     * @param value The value to find within the map.
     * @return The first key found associated with the indicated value.
     */

    Iterable<K> getKeys(V value);
    /**
     * Returns all keys associated with the indicated value contained within the
     * map.
     *
     * @param value The value to locate within the map.
     * @return An iterable object containing all keys associated with the
     * provided value.
     */

    int size();
    /**
     * Indicates the count of key/value entries stored inside the map.
     *
     * @return A non-negative number representing the number of entries.
     */

    boolean isEmpty();
    /**
     * Indicates if the dictionary contains any items.
     *
     * @return true if the dictionary is empty, false otherwise.
     */

    void clear();
    /***
     * Returns the map to an empty state ready to accept new entries.
     */

    Iterable<K> keyset();
    /**
     * Provides an Iterable object of the keys in the dictionary.
     * <p>
     * The keys provided by this method must appear in their natural, ascending,
     * order.
     *
     * @return An iterable set of keys.
     */

    Iterable<V> values();
    /**
     * Provides an Iterable object of the keys in the dictionary.
     * <p>
     * The values provided by this method must appear in an order matching the
     * keyset() method. This object may include duplicates if the data structure
     * includes duplicate values.
     *
     * @return An iterable object of all the dictionary's values.
     */

}