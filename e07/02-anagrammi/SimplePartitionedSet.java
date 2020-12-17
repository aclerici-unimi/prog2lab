/**
 * Simple implementation of a {@link PartitionedSet}.
 *
 * @absFun see AF({@link PartitionedSet})
 * @repInv see RI({@link PartitionedSet})
 */
public class SimplePartitionedSet<E> extends PartitionedSet<E, SimplePartitionedSet.SimplePartition<E>> {

	/**
	 * Constructs a SimplePartitionedSet which will be partitioned according to the
	 * given {@link Equiparator}.
	 * 
	 * @param partitioner {@link Equiparator} used to determine the membership to a
	 *                    partition.
	 */
	public SimplePartitionedSet(Equiparator<E> partitioner) {
		super(partitioner);
	}

	@Override
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException("can't add a null element");
		SimplePartition<E> found = findPartition(e);
		if (found == null) {
			partitions.add(new SimplePartition<E>(e));
			assert repOk();
			return true;
		}
		if (!found.els.contains(e)) {
			found.els.add(e);
			assert repOk();
			return true;
		}
		return false;
	}

	/**
	 * Minimal implementation of a {@link Partition}.
	 *
	 * @absFun see AF({@link Partition})
	 * @repInv see RI({@link Partition})
	 */
	public static class SimplePartition<E> extends PartitionedSet.Partition<E> {

		/**
		 * Constructs a {@code SimplePartition} containing {@code element}.
		 * 
		 * @param element the only element of the new {@code SimplePartition}.
		 */
		public SimplePartition(E element) {
			super(element);
		}

	}

}
