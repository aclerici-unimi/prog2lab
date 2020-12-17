/*
 * TODO: rewrite class overview
 *
 * RI draft: eq, partitions not null; no partition is empty
 * */

/** Simple implementation of a {@link PartitionedSet}. */
public class SimplePartitionedSet<E> extends PartitionedSet<E, PartitionedSet.Partition<E>> {

	public SimplePartitionedSet(Equiparator<E> partitioner) {
		super(partitioner);
	}

	@Override
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException("can't add a null element");
		PartitionedSet.Partition<E> found = findPartition(e);
		if (found == null) {
			partitions.add(new SimplePartition<E>(e));
			return true;
		}
		if (!found.els.contains(e)) {
			found.els.add(e);
			return true;
		}
		return false;
	}

	/** Minimal implementation of a {@link Partition} */
	public static class SimplePartition<E> extends PartitionedSet.Partition<E> {

		public SimplePartition(E element) {
			super(element);
		}

	}

}
