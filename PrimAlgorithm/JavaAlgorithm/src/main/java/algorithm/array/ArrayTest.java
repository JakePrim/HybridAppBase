package algorithm.array;

public class ArrayTest {
    public static void main(String[] args) {
        ArrayIntersection intersection = new ArrayIntersection();
        int[] f = intersection.f(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4});
        for (int i = 0; i < f.length; i++) {
            System.out.println(f[i]);
        }
    }
}
