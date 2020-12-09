public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

	/**
	 * Returns the number of key-value mappings in this map.
	 *
	 * @return the size.
	 */
	int size();

	/**
	 * Returns true if this map contains no key-value mappings.
	 * 
	 * @return true if the map is empty, false otherwise.
	 */
	boolean isEmpty();

	/**
	 * Returns true if this map contains a mapping for the specified key. (such that
	 * the keys are {@code equals}).
	 * 
	 * @param key key whose presence in this map is to be tested.
	 * @return true if this map contains a mapping for the specified key, false
	 *         otherwise.
	 */
	boolean containsKey(Object key);

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key.
	 * 
	 * @param key the key whose associated value is to be returned.
	 * @return the value to which the specified key is mapped, or null if this map
	 *         contains no mapping for the key.
	 */
	V get(Object key);

	/**
	 * Associates the specified value with the specified key in this map. If the map
	 * previously contained a mapping for the key, the old value is replaced by the
	 * specified value.
	 * 
	 * @param key   the key
	 * @param value the value
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key.
	 */
	V put(K key, V value);

	/**
	 * Removes the mapping for a key from this map if it is present
	 * 
	 * @param key key whose mapping is to be removed from the map.
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key.
	 */
	V remove(Object key);

	interface Entry<K, V> {

		K getKey();

		V getValue();

		V setValue(V value);
	}

}
