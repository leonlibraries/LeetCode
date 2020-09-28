/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        dfs(root,p,q,queue);
        return queue.poll();
    }


    boolean dfs(TreeNode node, TreeNode p , TreeNode q , Queue<TreeNode> queue){

        if(isLeafNode(node)){
            return containVal(node,p,q);
        }
        int itself = containVal(node,p,q)?1:0;
        int leftExist = node.left != null && dfs(node.left,p,q,queue) ? 1:0;
        int rightExist = node.right != null && dfs(node.right,p,q,queue) ?1:0;
        int sum = itself + leftExist + rightExist;
        if(sum >= 2){
            queue.add(node);
        }
        return sum >= 1; 
    }

  
    boolean containVal(TreeNode node,TreeNode p , TreeNode q){
        return p.val == node.val || q.val == node.val;
    }

    boolean isLeafNode(TreeNode node){
        return node.left == null && node.right == null;
    }
}