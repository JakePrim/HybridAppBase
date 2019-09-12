package datastructure.linklist;

/**
 * leetcode 相关问题
 * 203 虚拟节点的作用
 */
public class Solution {
    public ListNode removeElements(ListNode head, int val) {
        while (head != null && head.val == val) {//注意可能存在两个相等的元素
//            ListNode delNode = head;//要删除的节点为头部节点
//            head = head.next;//改变头部节点
//            delNode.next = null;
            head = head.next;
        }
        if (head == null) {//注意循环完之后 head可能为null
            return null;
        }
        ListNode perv = head;//头节点判断完毕后，判断头节点的后面的节点
        while (perv.next != null) {//循环下一个节点是否为空
            if (perv.next.val == val) {//判断下一个节点的值
//                ListNode delNode = perv.next;//要删除的节点
//                perv.next = delNode.next;
//                delNode.next = null;
                perv.next = perv.next.next;
            } else {//继续循环下一个节点
                perv = perv.next;
            }
        }
        return head;
    }

    public ListNode removeElements2(ListNode head, int val) {
        //通过虚拟头节点的作用 统一都有一个头节点 使逻辑达到统一
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode perv = dummyHead;//头节点判断完毕后，判断头节点的后面的节点
        while (perv.next != null) {//循环下一个节点是否为空
            if (perv.next.val == val) {//判断下一个节点的值
                perv.next = perv.next.next;
            } else {//继续循环下一个节点
                perv = perv.next;
            }
        }
        return dummyHead.next;
    }

    //3
    public ListNode removeElements3(ListNode head, int val) {
        if (head == null) return null;//递归的终止条件
        ListNode res = removeElements3(head.next, val);
        // 相当于一直把这个removeElements3函数放到栈中。
        // 当递归终止时，在从栈中一个一个取出，执行removeElements3函数从最后一个节点执行，例如：
        // val = 3
        // 1->3->4->5->6->null 栈的中的数据就是  a b c d e ，
        // 执行e函数 6 != 3,6->null res=null,return head=6->null
        // 执行d函数 5 !=3, res = 6->null,return head = 5->6->null
        // 执行c函数 4 != 3, res = 5->6->null,return head = 4->5->6->null
        // 执行b函数 3 == 3, res = 4->5->6->null,return res
        // 执行a函数 1 != 3, return head=1->4->5->6->null 这时栈中的函数已全部取出 返回head
        if (head.val == val)
            return res;
        else {
            head.next = res;
            return head;
        }
    }

    public ListNode removeElements4(ListNode head, int val) {
        if (head == null) return null;//递归的终止条件
        head.next = removeElements3(head.next, val);
        // 相当于一直把这个removeElements3函数放到栈中。
        // 当递归终止时，在从栈中一个一个取出，执行removeElements4函数从最后一个节点执行，例如：
        // val = 3
        // 1->3->4->5->6->null 栈的中的数据就是  a b c d e
        // 1-> a -> b -> c -> d -> e
        // 执行e函数 1 -> a -> b -> c -> d -> 6 -> null
        // 执行d函数 1 -> a -> b -> c -> 5 -> 6 -> null
        // 执行c函数 1 -> a -> b -> 4 -> 5 -> 6 -> null
        // 执行b函数 1 -> 4 -> 5 -> 6 -> null
        // 执行a函数 1 -> 4 -> 5 -> 6 -> null
        return head.val == val ? head.next : head;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(1);
        ListNode listNode1 = solution.removeElements3(listNode, 1);
        while (listNode1 != null) {
            System.out.println(listNode.val);
            listNode1 = listNode1.next;
        }
    }
}
