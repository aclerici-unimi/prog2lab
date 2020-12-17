import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
// use this command to build documentation: javadoc -d docs -tag repInv:class:"Representation Invariant" -tag absFun:class:"Abstraction Function" *.java

/**
 * Extension of a {@link PartitionedSet} which can be iterated on in sorted
 * order. Elements and partitions are not stored in a sorted manner: the user
 * can choose at every iterator construction which {@link Comparator} to use to
 * sort.
 *
 * @absFun see AF({@link PartitionedSet})
 * @repInv see RI({@link PartitionedSet})
 */
public class SortablePartitionedSet<E> extends PartitionedSet<E, SortablePartitionedSet.SortablePartition<E>> {

	/**
	 * Comparator which compares two {@link Comparable}s using their natural
	 * compareTo method. This can be useful when calling methods in the outer class.
	 */
	public static class NaturalComparator<T extends Comparable<T>> implements Comparator<T> {

		@Override
		public int compare(T arg0, T arg1) {
			return arg0.compareTo(arg1);
		}

	}

	/**
	 * Constructs a new SortablePartitionedSet which will be partitioned according
	 * to the given {@link Equiparator}.
	 * 
	 * @param partitioner {@link Equiparator} used to determine the membership to a
	 *                    partition.
	 */
	public SortablePartitionedSet(Equiparator<E> partitioner) {
		super(partitioner);
	}

	@Override
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException("can't add a null element");
		SortablePartition<E> found = findPartition(e);
		if (found == null) {
			partitions.add(new SortablePartition<E>(e));
			return true;
		}
		if (!found.els.contains(e)) {
			found.els.add(e);
			return true;
		}
		return false;
	}

	/**
	 * Returns an iterator over the partitions in this {@code PartitionedSet},
	 * sorted according to the order induced by the given {@code Comparator}. Does
	 * not check for modification afterwards. The user can design the comparator so
	 * that it avoids equiparable partitions, otherwise their order is unspecified.
	 * 
	 * @param comp comparator used to sort the partitions.
	 * @return the iterator.
	 */
	public Iterator<SortablePartition<E>> sortedPartitions(Comparator<SortablePartition<E>> comp) {
		partitions.sort(comp);
		return partitions.iterator();
	}

	/**
	 * Returns a String identifying this {@code SortablePartition}, elencating
	 * partitions and elements in sorted order according to the given
	 * {@code Comparator}s.
	 * 
	 * @param partComp comparator used to sort the partitions.
	 * @param elComp   comparator used to sort the elements in each partition.
	 * @return a sorted String identifying this {@code SortablePartitionedSet}.
	 */
	public String sortedToString(Comparator<SortablePartition<E>> partComp, Comparator<E> elComp) {
		String res = "";
		Iterator<SortablePartition<E>> it = sortedPartitions(partComp);
		while (it.hasNext()) {
			res += it.next().sortedToString(elComp) + "\n";
		}
		return res.trim();
	}

	/**
	 * Extension of a {@link Partition} which can be iterated on in sorted order.
	 * Elements are not stored in a sorted manner: the user can choose at every
	 * iterator construction which Comparator to use to sort.
	 *
	 * @absFun see AF({@link Partition})
	 * @repInv see RI({@link Partition})
	 */
	public static class SortablePartition<E> extends PartitionedSet.Partition<E> {

		/**
		 * Constructs a {@code SortablePartition} containing {@code element}.
		 * 
		 * @param element the only element of the new {@code SortablePartition}.
		 */
		public SortablePartition(E element) {
			super(element);
		}

		/**
		 * Returns an iterator over the elements in this {@code Partition}, sorted
		 * according to the order induced by the given {@code Comparator}. Does not
		 * check for modification afterwards.
		 * 
		 * @param comp comparator used to sort the elements.
		 * @return the iterator.
		 */
		public Iterator<E> sortedElements(Comparator<E> comp) {
			els.sort(comp);
			return els.iterator();
		}

		/**
		 * Returns the minimum of this {@code SortablePartition} according to the given
		 * {@code Comparator}.
		 * 
		 * @param comp comparator used to find the minimum.
		 * @return the minimum.
		 */
		public E min(Comparator<E> comp) {
			return Collections.min(els, comp);
		}

		/**
		 * Returns the maximum of this {@code SortablePartition} according to the given
		 * {@code Comparator}.
		 * 
		 * @param comp comparator used to find the maximum.
		 * @return the maximum.
		 */
		public E max(Comparator<E> comp) {
			return Collections.max(els, comp);
		}

		/**
		 * Returns a String identifying this {@code SortablePartition}, elencating the
		 * elements in sorted order according to the given {@code Comparator}.
		 * 
		 * @param comp comparator used to sort the elements.
		 * @return a sorted String identifying this {@code SortablePartition}.
		 */
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

}
