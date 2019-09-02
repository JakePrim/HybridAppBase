package datastructure.array;

public class Text {
    public static void main(String[] args) {
        Array<Integer> array = new Array<Integer>();
        for (int i = 0; i < 10; i++) {
            array.addLast(i);
        }
        System.out.println(array);

        array.add(1, 100);
        array.add(2, 100);
        array.add(4, 100);
        System.out.println(array);
        array.removeElementAll(100);
        System.out.println(array);
        System.out.println(array);
        System.out.println(array);

//        array.addFirst(-1);
//        System.out.println(array);
////
//        array.remove(2);
//        System.out.println(array);
//        array.removeFirst();
//        System.out.println(array);
//
//        array.removeLast();
//        System.out.println(array);
//
//        array.removeLast();
//        System.out.println(array);
//
//        array.removeLast();
//        System.out.println(array);
    }
}
