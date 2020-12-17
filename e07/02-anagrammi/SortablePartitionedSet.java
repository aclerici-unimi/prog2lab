import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Extension of a partitioned set which can be iterated on in sorted order.
 * Elements and partitions are not stored in a sorted manner: the user can
 * choose at every iterator construction which Comparator to use to sort.
 */
public class SortablePartitionedSet<E> extends PartitionedSet<E, SortablePartitionedSet.SortablePartition<E>> {

	public static class NaturalComparator<T extends Comparable<T>> implements Comparator<T> {

		@Override
		public int compare(T arg0, T arg1) {
			return arg0.compareTo(arg1);
		}

	}

	public SortablePartitionedSet(Equiparator<E> partitioner) {
		super(partitioner);
	}

	@Override
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException("can't add a null element");
		PartitionedSet.Partition<E> found = findPartition(e);
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
	 * @return the iterator.
	 */
	public Iterator<SortablePartition<E>> sortedPartitions(Comparator<SortablePartition<E>> comp) {
		partitions.sort(comp);
		return partitions.iterator();
	}

	public String sortedToString(Comparator<SortablePartition<E>> partComp, Comparator<E> elComp) {
		String res = "";
		Iterator<SortablePartition<E>> it = sortedPartitions(partComp);
		while (it.hasNext()) {
			res += it.next().sortedToString(elComp) + "\n";
		}
		return res.trim();
	}

	public static class SortablePartition<E> extends PartitionedSet.Partition<E> {

		public SortablePartition(E element) {
			super(element);
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

}
