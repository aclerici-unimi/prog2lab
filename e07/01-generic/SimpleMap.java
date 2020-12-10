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
	 * Representation Invariant: q is not null; there aren't two entries e1 and e2
	 * such that e1.key==e2.key.
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
			throw new NullPointerException("the map can't have null keys or values");
		Map.Entry<K, V> found = findEntry(key);
		V retVal;
		if (found == null) {
			entries.add(new SimpleEntry<K, V>(key, value));
			retVal = null;
		} else {
			retVal = found.getValue();
			found.setValue(value);
		}
		assert repOk();
		return retVal;
	}

	@Override
	public V remove(Object key) {
		Map.Entry<K, V> found = findEntry(key);
		if (found == null)
			return null;
		else {
			V retval = found.getValue();
			entries.remove(found);
			assert repOk();
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
				throw new NullPointerException("can't instantiate an entry with null arguments");
			this.key = key;
			this.value = value;
			assert repOk();
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
			assert repOk();
			return retVal;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof SimpleEntry))
				return false;
			SimpleEntry<?, ?> other = (SimpleEntry<?, ?>) obj;
			return this.key.equals(other.key) && this.value == other.value;
		}

		@Override
		public int hashCode() {
			return 31 * key.hashCode() + value.hashCode();
		}

		@Override
		public String toString() {
			return key + " -> " + value;
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SimpleMap))
			return false;
		SimpleMap<?, ?> other = (SimpleMap<?, ?>) obj;
		if (entries.size() != other.entries.size())
			return false;
		for (Entry<K, V> e : entries) {
			Entry<K, V> otherEntry = findEntry(e.getKey());
			if (otherEntry == null || !e.equals(otherEntry))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int res = 31;
		for (Entry<?, ?> e : entries)
			res = res * 31 + e.hashCode();
		return res;
	}

	@Override
	public String toString() {
		String res = "{ ";
		int size = this.size();
		if (size > 0) {
			Iterator<Entry<K, V>> it = entries.iterator();
			Entry<K, V> p = it.next();
			while (it.hasNext()) {
				res += p.toString() + ", ";
				p = it.next();
			}
			res += p.toString() + " ";
		}
		return res + "}";
	}

}
