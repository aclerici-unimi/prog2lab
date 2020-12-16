import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * TODO: rewrite class overview
 *
 * RI draft: eq, partitions not null; no partition is empty
 * */

/**
 * Abstraction of a partitioned set. Equivalent elements belong to the same
 * partition. A given {@link Equiparator} is used to choose if an element
 * belongs to an existing partition. If it doesn't, a new partition containing
 * the element is created.
 */
public class PartitionedSet<E> implements Set<E> {
	private final Equiparator<E> eq;
	private final List<Partition<E>> partitions;

	public PartitionedSet(Equiparator<E> partitioner) {
		if (partitioner == null)
			throw new NullPointerException();
		partitions = new LinkedList<>();
		eq = partitioner;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<>() {
			Iterator<Partition<E>> partition = partitions.iterator();
			Iterator<E> element = null;

			@Override
			public boolean hasNext() {
				if (element == null || !element.hasNext()) {
					if (!partition.hasNext())
						return false;
					element = partition.next().iterator();
					return true;
				}
				return true;
			}

			@Override
			public E next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return element.next();
			}
		};
	}

	/**
	 * Returns an iterator over the partitions in this {@code PartitionedSet}.
	 * 
	 * @return the iterator.
	 */
	public Iterator<Partition<E>> partitions() {
		return partitions.iterator();
	}

	/**
	 * Returns an iterator over the partitions in this {@code PartitionedSet},
	 * sorted according to the order induced by the given {@code Comparator}. Does
	 * not check for modification afterwards. The user can design the comparator so
	 * that it avoids equiparable partitions, otherwise their order is unspecified.
	 * 
	 * @return the iterator.
	 */
	public Iterator<Partition<E>> sortedPartitions(Comparator<Partition<E>> comp) {
		partitions.sort(comp);
		return partitions.iterator();
	}

	@Override
	public int size() {
		int res = 0;
		for (Partition<E> part : partitions)
			res += part.size();
		return res;
	}

	@Override
	public boolean isEmpty() {
		return partitions.isEmpty();
	}

	@Override
	public boolean contains(E e) {
		Partition<E> found = findPartition(e);
		if (found != null && found.els.contains(e))
			return true;
		return false;
	}

	@Override
	public E choose() {
		if (partitions.isEmpty())
			throw new NoSuchElementException();
		return partitions.get(0).els.get(0);
	}

	private Partition<E> findPartition(E e) {
		for (Partition<E> part : partitions)
			if (eq.equiparate(e, part.els.get(0)))
				return part;
		return null;
	}

	@Override
	public boolean add(E e) {
		Partition<E> found = findPartition(e);
		if (found == null) {
			partitions.add(new Partition<>(e));
			return true;
		}
		if (!found.els.contains(e)) {
			found.els.add(e);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(E e) {
		Partition<E> found = findPartition(e);
		if (found != null && found.els.contains(e)) {
			found.els.remove(e);
			if (found.els.isEmpty())
				partitions.remove(found);
			return true;
		}
		return false;

	}

	public static class Partition<E> implements Iterable<E> {
		private final List<E> els;

		private Partition(E element) {
			els = new LinkedList<>();
			els.add(element);
		}

		/**
		 * Returns the number of elements in this partition (its cardinality).
		 * 
		 * @return the size.
		 */
		public int size() {
			return els.size();
		}

		@Override
		public Iterator<E> iterator() {
			return els.iterator();
		}

		/**
		 * Returns an iterator over the elements in this {@code Partition}, sorted
		 * according to the order induced by the given {@code Comparator}. Does not
		 * check for modification afterwards.
		 * 
		 * @return the iterator.
		 */
		public Iterator<E> sortedElements(Comparator<E> comp) {
			els.sort(comp);
			return els.iterator();
		}

		public E min(Comparator<E> comp) {
			return Collections.min(els, comp);
		}

		public E max(Comparator<E> comp) {
			return Collections.max(els, comp);
		}

		public String sortedToString(Comparator<E> comp) {
			String res = "[";
			if (els.size() > 0) {
				Iterator<E> it = sortedElements(comp);
				E el = it.next();
				while (it.hasNext()) {
					res += el + ", ";
					el = it.next();
				}
				res += el;
			}
			return res + "]";
		}

	}

	@Override
	public String toString() {
		String res = "";
		for (Partition<E> part : partitions)
			res += part;
		return res;
	}

	public String sortedToString(Comparator<Partition<E>> partComp, Comparator<E> elComp) {
		String res = "";
		Iterator<Partition<E>> it = sortedPartitions(partComp);
		while (it.hasNext()) {
			res += it.next().sortedToString(elComp) + "\n";
		}
		return res.trim();
	}

}
