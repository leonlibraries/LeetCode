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
class Solution1 {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> data = new ArrayList<>(3);

        if(root == null){
            return data;
        }

        if(isLeafNode(root)){
            data.add(root.val);
            return data;
        }
        // 左子节点
        if(root.left != null){
            List<Integer> left = postorderTraversal(root.left);
            data.addAll(left);
        }
        // 右子节点
        if(root.right != null){
            List<Integer> right = postorderTraversal(root.right);
            data.addAll(right);
        }
        // 父节点
        data.add(root.val);
        return data;
    }

    boolean isLeafNode(TreeNode node){
        return node.left == null && node.right == null;
    }

}