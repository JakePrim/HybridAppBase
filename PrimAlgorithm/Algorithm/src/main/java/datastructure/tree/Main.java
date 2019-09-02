package datastructure.tree;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }
        //      5
        //     / \
        //    3   6
        //   / \   \
        //  2   4   8
        //
        //
//        bst.preOrder();//532468
        System.out.println(bst);
//        bst.preOrderNR();
//        bst.lastOrder();//536248
//        bst.inOrder();//234568
//        bst.postOrder();//243865
//        System.out.println(bst.toString());
//        System.out.println("args = [" + bst.minimum() + "]");
//        System.out.println("args = [" + bst.maximum() + "]");
//        bst.remove(3);
//        bst.lastOrder();

//        testRemoveMin();
//        testRemoveMax();
    }

    private static void testRemoveMin() {
        BST<Integer> bst = new BST<>();

        ArrayList<Integer> list = new ArrayList<>();

        Random random = new Random();

        int n = 100;
        for (int i = 0; i < n; i++) {
            bst.add(random.nextInt(1000));
        }
        while (!bst.isEmpty()) {
            Integer min = bst.removeMin();
            list.add(min);
            System.out.println(min + " ");
        }

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) > list.get(i))
                throw new IllegalArgumentException("test remove min error");
        }
        System.out.println("test remove min success");
    }

    private static void testRemoveMax() {
        BST<Integer> bst = new BST<>();

        ArrayList<Integer> list = new ArrayList<>();

        Random random = new Random();

        int n = 100;
        for (int i = 0; i < n; i++) {
            bst.add(random.nextInt(1000));
        }
        while (!bst.isEmpty()) {
            Integer max = bst.removeMax();
            list.add(max);
            System.out.println(max + " ");
        }

        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) < list.get(i))
                throw new IllegalArgumentException("test remove max error");
        }
        System.out.println("test remove max success");
    }
}
