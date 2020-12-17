import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
// use this command to build documentation: javadoc -d docs -tag repInv:class:"Representation Invariant" -tag absFun:class:"Abstraction Function" *.java

/**
 * Abstraction of a partitioned set. Equivalent elements belong to the same
 * partition. A given {@link Equiparator} is used to choose if a new element
 * belongs to an existing partition. If it doesn't, a new partition containing
 * the element is created.
 *
 * @absFun AF(eq, partitions) = Set of partitions {@code partitions} (see
 *         AF({@link Partition})), which are determined according to the
 *         Equiparator {@code eq}.
 * @repInv {@code eq} and {@code partitions} are not null; there is no P in
 *         {@code partitions} such that {@code P.repOk()==false}.
 */
public abstract class PartitionedSet<E, P extends PartitionedSet.Partition<E>> implements Set<E> {
	private final Equiparator<E> eq;
	protected final List<P> partitions;

	/**
	 * Constructs a new PartitionedSet which will be partitioned according to the
	 * given {@link Equiparator}.
	 * 
	 * @param partitioner {@link Equiparator} used to determine the membership to a
	 *                    partition.
	 */
	protected PartitionedSet(Equiparator<E> partitioner) {
		if (partitioner == null)
			throw new NullPointerException("you must choose a partitioner Equiparator");
		partitions = new LinkedList<>();
		eq = partitioner;
		assert repOk();
	}

	/**
	 * Implementation of the representation invariant. Returns true if the
	 * representation respects all its requirements. Used in assertions.
	 * 
	 * @return true if the representation of this is ok; false otherwise.
	 */
	public boolean repOk() {
		if (eq == null || partitions == null)
			return false;
		for (P part : partitions)
			if (!part.repOk())
				return false;
		return true;
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

	/**
	 * Returns the partition which matches the given element, or null if no such
	 * partition exists.
	 * 
	 * @param e the element used to match the partition.
	 * @return the found partition or {@code null}.
	 */
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
			assert repOk();
			return true;
		}
		return false;
	}

	/**
	 * Abstraction of a partition. Partitions should not be modifiable by the client
	 * but only from the owning set.
	 *
	 * @absFun AF(els) = partition of elements els.
	 * @repInv els is not empty or null.
	 */
	public abstract static class Partition<E> implements Iterable<E> {
		protected final List<E> els;

		/**
		 * Constructs a {@code Partition} containing {@code element}.
		 * 
		 * @param element the only element of the new {@code Partition}.
		 */
		protected Partition(E element) {
			els = new LinkedList<>();
			els.add(element);
		}

		/**
		 * Implementation of the representation invariant. Returns true if the
		 * representation respects all its requirements. Used in assertions.
		 * 
		 * @return true if the representation of this is ok; false otherwise.
		 */
		public boolean repOk() {
			return els != null && !els.isEmpty();
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
