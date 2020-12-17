import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class Test {
	private static String alphabetize(String str) {
		char charArray[] = str.toCharArray();
		Arrays.sort(charArray);
		return new String(charArray);
	}

	public static void main(String[] args) {
		SortablePartitionedSet<String> anagramSet = new SortablePartitionedSet<>(new Equiparator<String>() {

			@Override
			public boolean equiparate(String a, String b) {
				return alphabetize(a).equals(alphabetize(b));
			}

		});

		Scanner sc = new Scanner(System.in);
		while (sc.hasNext())
			anagramSet.add(sc.next());
		sc.close();

		Iterator<SortablePartitionedSet.SortablePartition<String>> it = anagramSet
				.sortedPartitions(new Comparator<SortablePartitionedSet.SortablePartition<String>>() {

					@Override
					public int compare(SortablePartitionedSet.SortablePartition<String> arg0,
							SortablePartitionedSet.SortablePartition<String> arg1) {
						if (arg0.size() < arg1.size())
							return 1;
						if (arg0.size() > arg1.size())
							return -1;
						Comparator<String> comp = new SortablePartitionedSet.NaturalComparator<String>();
						return comp.compare(arg0.min(comp), arg1.min(comp));
					}
				});
		while (it.hasNext()) {
			SortablePartitionedSet.SortablePartition<String> part = it.next();
			if (part.size() > 1)
				System.out.println(part.sortedToString(
						new SortablePartitionedSet.NaturalComparator<String>()));
		}
	}

}
