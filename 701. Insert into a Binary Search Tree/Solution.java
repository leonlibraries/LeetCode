/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode temp = root;
        while(true){
            if(root == null){
                temp = new TreeNode(val);
                break;
            }
            if(val<root.val){
                if(root.left != null){
                    root = root.left;
                }else{
                    root.left = new TreeNode(val);
                    break;
                }
            }
            if(val>root.val){
                if(root.right != null){
                    root = root.right;
                }else{
                    root.right = new TreeNode(val);
                    break;
                }
            }
        }
        return temp;
    }
}