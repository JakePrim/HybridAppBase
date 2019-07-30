package algorithm.array;

/**
 * 求两个数组的交集
 */
public class ArrayIntersection {
    public int[] f(int[] num1,int[] num2){
        if (num1 == null || num2 == null){
            return new int[0];
        }
        int length = 0;
        //nums1 = [4,9,5], nums2 = [9,4,9,8,4] [4,9]
        int l = 0;
        if (num1.length < num2.length){
            l = num1.length;
        }else {
            l = num2.length;
        }
        //先对数组排序和去重
        int[] result = new int[l];

        for (int i = 0; i < num1.length; i++) {
            int com = num1[i];
            for (int j = 0; j < num2.length; j++) {
                if (com == num2[j]){
                    result[length] = com;
                    length++;
                    break;
                }
            }
        }
        return result;
    }
}
