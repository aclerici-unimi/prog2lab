import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SimpleMap<K, V> implements Map<K, V> {
	private final List<Map.Entry<K, V>> entries;

	/*
	 * Abstraction Function: AF(entries) = entries.get(0), ...,
	 * entries.get(entries.size()-1) If entries.isEmpty(), AF(entries) is the empty
	 * map.
	 *
	 * Representation Invariant: q is not null.
	 */

	/** Constructs an empty SimpleMap. */
	public SimpleMap() {
		this.entries = new LinkedList<Map.Entry<K, V>>();
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation is ok; false otherwise.
	 */
	public boolean repOk() {
		if (entries == null)
			return false;
		int i = 0;
		for (Entry<K, V> p : entries) {
			if (p == null || !(((SimpleEntry<K, V>) p).repOk()))
				return false;
			ListIterator<Entry<K, V>> it = entries.listIterator(i + 1);
			while (it.hasNext())
				if (p.getKey().equals(it.next().getKey()))
					return false;
			i++;
		}
		return true;
	}

	@Override
	public Iterator<Map.Entry<K, V>> iterator() {
		return entries.iterator();
	}

	@Override
	public int size() {
		return entries.size();
	}

	@Override
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * Returns the entry of key k, or null if no such entry exists.
	 */
	private Map.Entry<K, V> findEntry(Object key) {
		for (Map.Entry<K, V> entry : entries)
			if (entry.getKey().equals(key))
				return entry;
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		return findEntry(key) == null ? false : true;
	}

	@Override
	public V get(Object key) {
		Map.Entry<K, V> found = findEntry(key);
		return found == null ? null : found.getValue();
	}

	@Override
	public V put(K key, V value) {
		if (key == null || value == null)
			throw new NullPointerException();
		Map.Entry<K, V> found = findEntry(key);
		if (found == null) {
			entries.add(new SimpleEntry<K, V>(key, value));
			return null;
		} else {
			V retVal = found.getValue();
			found.setValue(value);
			return retVal;
		}
	}

	@Override
	public V remove(Object key) {
		Map.Entry<K, V> found = findEntry(key);
		if (found == null)
			return null;
		else {
			V retval = found.getValue();
			entries.remove(found);
			return retval;
		}
	}

	public static class SimpleEntry<K, V> implements Map.Entry<K, V> {
		private final K key;
		private V value;

		/*
		 * Abstraction Function: AF(key, value) = key -> value.
		 *
		 * Representation Invariant: key and value are not null.
		 */

		/**
		 * Constructs a SimpleMap entry of key {@code key} and value {@code value}.
		 * 
		 * @param key   the key.
		 * @param value the value.
		 */
		public SimpleEntry(K key, V value) {
			if (key == null || value == null)
				throw new NullPointerException();
			this.key = key;
			this.value = value;
		}

		/**
		 * Implementation of the representation invariant. Returns true if the
		 * representation respects all its requirements. Used in assertions.
		 * 
		 * @return true if the representation is ok; false otherwise.
		 */
		public boolean repOk() {
			return key != null && value != null;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V retVal = this.value;
			this.value = value;
			return retVal;
		}
	}
}
