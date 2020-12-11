import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Minimal implementation of the concept of map, specific to
 * {@code String->Int}. SimpleMaps are mutable objects which represent a series
 * of Pairs key-value, the key being a {@code String} and the value being an
 * {@code int}.
 */
public class SimpleMap implements Cloneable {
	private final LinkedList<Pair> entries;

	/*
	 * Abstraction Function: A(entries) = map{entries.get(0), ...,
	 * entries.get(entries.size()-1)}.
	 *
	 * Representation Invariant: entries is not null, entries cannot contain two
	 * Pairs p1 and p2 such that p1.key == p2.key;
	 *
	 * Abstraction Invariant: a map maps two equal keys into two equal values
	 */

	/** Constructs an empty SimpleMap. */
	public SimpleMap() {
		this.entries = new LinkedList<Pair>(); // not null and no (invalid) pair
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
		for (Pair p : entries) {
			if (p == null || !p.repOk())
				return false;
			ListIterator<Pair> it = entries.listIterator(i + 1);
			while (it.hasNext())
				if (p.key.equals(it.next().key))
					return false;
			i++;
		}
		return true;
	}

	private static class Pair {
		private final String key;
		private int value;

		/*
		 * Abstraction Function: A(key, value) = mapping key->value
		 *
		 * Representation Invariant: key is not null
		 */

		Pair(String key, int value) { // can't be null
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
			return key != null;
		}

		/**
		 * Returns a string identifying this Pair.
		 *
		 * @return a string identifying this Pair.
		 */
		@Override
		public String toString() {
			return this.key + "->" + this.value;
		}

		/**
		 * Compares the specified object with this Pair for equality. Two {@code Pair}s
		 * are defined to be equals if they map the same key to the same value.
		 *
		 * @param o the object to be compared with this.
		 * @return true if this and o are equals.
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof Pair))
				return false;
			Pair other = (Pair) obj;
			return this.key.equals(other.key) && this.value == other.value;
		}

		/**
		 * Returns the hash code value for this Pair.
		 *
		 * @return the hash code.
		 */
		@Override
		public int hashCode() {
			return 31 * key.hashCode() + Integer.hashCode(value);
		}

		/**
		 * Returns a shallow copy of this Pair instance.
		 *
		 * @return a clone of this.
		 */
		public Pair clone() {
			return new Pair(key, value);
		}

	}

	/**
	 * Returns true if there is an entry of key {@code k} in this map, false
	 * otherwise.
	 *
	 * @param k
	 * @return true if there's an entry of key {@code k} in this.
	 */
	public boolean isIn(String k) {
		// observer method
		for (Pair p : entries)
			if (p.key.equals(k))
				return true;
		return false;
	}

	/**
	 * Inserts the entry {@code k->v} in this map. If the key {@code k} is already
	 * present, its mapped value is changed to {@code v}.
	 *
	 * @param k key
	 * @param v value
	 * @throws IllegalArgumentException if k==null
	 */
	public void put(String k, int v) {
		// entries is not null by hypothesis
		if (k == null)
			// excludes null key
			throw new IllegalArgumentException("can't add a null key");
		for (Pair p : entries)
			if (p.key.equals(k)) {
				p.value = v;
				return; // excludes duplicate keys
			}
		entries.add(new Pair(k, v)); // excludes null Pairs
		assert repOk();
	}

	/**
	 * Removes the entry of key {@code k} from this map.
	 *
	 * @param k the key
	 * @throws NoSuchElementException if the key {@code k} is not present.
	 */
	public void remove(String k) {
		// entries is not null by hypothesis
		for (Pair p : entries)
			if (p.key.equals(k)) {
				entries.remove(p); // cannot generate duplicates or null Pairs/keys
				assert repOk();
				return;
			}
		throw new NoSuchElementException("cannot remove entry of key " + k + ", no such entry exists");
	}

	/**
	 * Returns the entry of key {@code k} from this map.
	 *
	 * @param k the key
	 * @throws NoSuchElementException if the key {@code k} is not present.
	 */
	public int get(String k) {
		// observer method
		for (Pair p : entries)
			if (p.key.equals(k))
				return p.value;
		throw new NoSuchElementException("cannot remove entry of key " + k + ", no such entry exists");
	}

	/**
	 * Returns the size (number of mappings) of this map.
	 *
	 * @return size of this.
	 */
	public int size() {
		// observer method
		return entries.size();
	}

	/**
	 * Returns a string identifying this SimpleMap and its entries.
	 *
	 * @return the string.
	 */
	@Override
	public String toString() {
		String res = "{ ";
		int size = this.size();
		if (size > 0) {
			Iterator<Pair> it = entries.iterator();
			Pair p = it.next();
			while (it.hasNext()) {
				res += p.toString() + ", ";
				p = it.next();
			}
			res += p.toString() + " ";
		}
		return res + "}";
	}

	/**
	 * Compares the specified object with this SimpleMap for equality. Two
	 * {@code SimpleMap}s are defined to be equal if they contain the same set of
	 * {@code Pair}s (that is, equals in couples).
	 *
	 * @param o the object to be compared with this.
	 * @return true if this and o are equals.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SimpleMap))
			return false;
		SimpleMap other = (SimpleMap) obj;
		if (other.size() != this.size())
			return false;
		for (Pair p : this.entries) {
			if (!other.entries.contains(p))
				return false;
		}
		return true;
	}

	/**
	 * Returns the hash code value for this SimpleMap.
	 *
	 * @return the hash code.
	 */
	@Override
	public int hashCode() {
		int res = 0;
		for (Pair p : entries)
			res += 31 * p.hashCode();
		return 31 * entries.size() + res;
	}

	/**
	 * Returns a shallow copy of this SimpleMap instance.
	 *
	 * @return a clone of this.
	 */
	public SimpleMap clone() {
		SimpleMap m = new SimpleMap();
		for (Pair p : entries)
			m.entries.add(p.clone());
		return m;
	}

}
