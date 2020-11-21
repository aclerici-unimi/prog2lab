import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Auxiliary class used by SimpleMap
 */
class Pair {
	final String key;
	int value;

	Pair(String key, int value) { // can't be null
		this.key = key;
		this.value = value;
	}

}

/**
 * Minimal implementation of the concept of map, specific to
 * {@code String->Int}. SimpleMaps are mutable objects which represent a series
 * of Pairs key-value, the key being a {@code String} and the value being an
 * {@code int}.
 */
public class SimpleMap {
	private final LinkedList<Pair> entries;

	/*
	 * Abstraction Function: A(entries) = map{entries.get(0).key ->
	 * entries.get(0).value, ..., entries.get(entries.size()-1).key ->
	 * entries.get(entries.size()-1).value}.
	 *
	 * Representation Invariant: entries is not null, entries cannot contain two
	 * Pairs p1 and p2 such that p1.key == p2.key; no Pair p is such that p==null or
	 * p.key==null.
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
			if (p == null || p.key == null)
				return false;
			ListIterator<Pair> it = entries.listIterator(i + 1);
			while (it.hasNext())
				if (p.key.equals(it.next().key))
					return false;
			i++;
		}
		return true;
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
	 * @return a string identifying this SimpleMap.
	 */
	@Override
	public String toString() {
		String res = "{ ";
		int size = this.size();
		if (size > 0) {
			ListIterator<Pair> it = entries.listIterator();
			Pair p = it.next();
			while (it.hasNext()) {
				res += p.key + "->" + p.value + ", ";
				p = it.next();
			}
			res += p.key + "->" + p.value + " ";
		}
		return res + "}";
	}

	/**
	 * Compares the specified object with this SimpleMap for equality. Two
	 * {@code SimpleMap}s are defined to be equal if they contain the same set of
	 * keys, and same keys are mapped to the same values respectively.
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
		boolean found;
		for (Pair p : this.entries) {
			found = false;
			for (Pair q : other.entries) {
				if (p.key.equals(q.key) && p.value == q.value) {
					found = true;
					break;
				}
			}
			if (!found)
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
			res += p.key.hashCode() + p.value;
		return res;
	}

	/**
	 * Returns a shallow copy of this SimpleMap instance.
	 *
	 * @return a clone of this
	 */
	public SimpleMap clone() {
		SimpleMap m = new SimpleMap();
		for (Pair p : entries)
			m.put(p.key, p.value);
		return m;
	}

}
