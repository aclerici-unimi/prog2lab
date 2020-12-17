import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Abstraction of a partitioned set. Equivalent elements belong to the same
 * partition. A given {@link Equiparator} is used to choose if a new element
 * belongs to an existing partition. If it doesn't, a new partition containing
 * the element is created.
 */
public abstract class PartitionedSet<E, P extends PartitionedSet.Partition<E>> implements Set<E> {
	private final Equiparator<E> eq;
	protected final List<P> partitions;

	public PartitionedSet(Equiparator<E> partitioner) {
		if (partitioner == null)
			throw new NullPointerException("you must choose a partitioner Equiparator");
		partitions = new LinkedList<>();
		eq = partitioner;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<>() {
			Iterator<P> partition = partitions.iterator();
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
	public Iterator<P> partitions() {
		return partitions.iterator();
	}

	@Override
	public int size() {
		int res = 0;
		for (P part : partitions)
			res += part.size();
		return res;
	}

	@Override
	public boolean isEmpty() {
		return partitions.isEmpty();
	}

	@Override
	public boolean contains(E e) {
		P found = findPartition(e);
		if (found != null && found.els.contains(e))
			return true;
		return false;
	}

	@Override
	public E choose() {
		if (partitions.isEmpty())
			throw new NoSuchElementException("the set is empty");
		return partitions.get(0).els.get(0);
	}

	protected P findPartition(E e) {
		for (P part : partitions)
			if (eq.equiparate(e, part.els.get(0)))
				return part;
		return null;
	}

	@Override
	public boolean remove(E e) {
		P found = findPartition(e);
		if (found != null && found.els.contains(e)) {
			found.els.remove(e);
			if (found.els.isEmpty())
				partitions.remove(found);
			return true;
		}
		return false;

	}

	public abstract static class Partition<E> implements Iterable<E> {
		protected final List<E> els;

		protected Partition(E element) {
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
	}

	@Override
	public String toString() {
		String res = "";
		for (P part : partitions)
			res += part;
		return res;
	}

}
