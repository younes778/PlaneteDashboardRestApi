package Main;

public class test {

	public static void main(String[] args) {

		isOlympic(new int[] { 3, 2, 1 });
		isOlympic(new int[] { 2, 2, 1, 1 });
		isOlympic(new int[] { 1, 1000, 100, 10000, 2 });
		isOlympic(new int[] { 1, 2, 1, 3, 2 });
		isOlympic(new int[] { 1, 2, -1, 2, 2 });

	}

	static int isOlympic(int[] a) {
		boolean isOlympic = true;
		int i = 0;
		while (isOlympic && i < a.length) {
			int sum = 0;
			for (int j = 0; j < a.length; j++) {
				if (j == i)
					j++;
				if (j < a.length)
					if (a[j] < a[i])
						sum += a[j];
			}
			if (sum > a[i])
				isOlympic = false;
			i++;
		}
		if (isOlympic)
			return 1;
		else
			return 0;
	}

}
