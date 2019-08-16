package datastructure.tree;

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
        bst.preOrder();//532468
//        bst.inOrder();//234568
//        bst.postOrder();//243865
//        System.out.println(bst.toString());
    }
}
