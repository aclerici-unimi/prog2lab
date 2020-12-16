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
		PartitionedSet<String> anagramSet = new PartitionedSet<>(new Equiparator<String>() {

			@Override
			public boolean equiparate(String a, String b) {
				return alphabetize(a).equals(alphabetize(b));
			}

		});

		Scanner sc = new Scanner(System.in);
		while (sc.hasNext())
			anagramSet.add(sc.next());
		sc.close();

		Iterator<PartitionedSet.Partition<String>> it = anagramSet
				.sortedPartitions(new Comparator<PartitionedSet.Partition<String>>() {

					@Override
					public int compare(PartitionedSet.Partition<String> arg0,
							PartitionedSet.Partition<String> arg1) {
						if (arg0.size() < arg1.size())
							return 1;
						if (arg0.size() > arg1.size())
							return -1;
						StringComparator comp = new StringComparator();
						return comp.compare(arg0.min(comp), arg1.min(comp));
					}
				});
		while (it.hasNext()) {
			PartitionedSet.Partition<String> part = it.next();
			if (part.size() > 1)
				System.out.println(part.sortedToString(new StringComparator()));
		}
	}

	private static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
			return arg0.compareTo(arg1);
		}

	}

}
