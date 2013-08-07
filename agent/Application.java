import java.util.Arrays;

public class Application {
	public static void main(String[] args) {
		int[] a = {0, 1, 2, 3, 4};
		int[] b = {4, 3, 2, 1, 0};

		int[] c = vectorAddition(a, b);

		System.out.println(Arrays.toString(c));
	}

	public static int[] vectorAddition(int[] a, int[] b) {
		int[] c = new int[a.length];

		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] + b[i];
		}

		return c;
	}
}