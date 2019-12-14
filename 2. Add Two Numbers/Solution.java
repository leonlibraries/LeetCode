/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int v = 0;
        ListNode headNode = null;
        ListNode currNode = null;
        for(int i=0; l1!=null || l2 !=null ;i++){
            int a = l1 != null ? l1.val:0;
            int b = l2 != null ? l2.val:0;
            int sum = a + b + v;
            int div = sum/10;
            ListNode node = new ListNode(sum % 10);
            if(currNode == null){
                currNode = node;
                headNode = node;
            }else{
                currNode.next = node;
                currNode = currNode.next;
            }

            v = div;
            // next node
            l1 = l1 != null ? l1.next:null;
            l2 = l2 != null ? l2.next:null;
        }
        if(v > 0){
            currNode.next = new ListNode(v);
        }
        return headNode;
    }
}
