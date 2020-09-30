/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode root = new ListNode();
        ListNode list = root;
        while(l1 != null || l2 != null){
            if(l1 == null && l2 != null){
                list = add(list,l2.val);
                l2 = l2.next;
                continue;
            }
            if(l1 != null && l2 == null){
                list = add(list,l1.val);
                l1 = l1.next;
                continue;
            }
            if(l1.val<l2.val){
                list = add(list,l1.val);
                l1 = l1.next;
            }else{
                list = add(list,l2.val);
                l2 = l2.next;
            }
        }
        return root.next;
    }

    ListNode add(ListNode list, int val){
        ListNode next = new ListNode(val);
        list.next = next;
        list = next;
        return list;
    }
}