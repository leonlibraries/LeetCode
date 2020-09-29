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
class Solution2 {
    public List<Integer> postorderTraversal(TreeNode node) {
        List<Integer> data = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque();
        Set<TreeNode>  visited = new HashSet<>();
        
        while(node != null){   
            if(!visited.contains(node)){
                stack.addFirst(node);  
                data.add(node.val);
                visited.add(node);
            }
            if(node.right != null && !visited.contains(node.right)){
                // 右子节点
                node = node.right;
                continue;
            }
            if(node.left != null && !visited.contains(node.left)){
                // 左子节点
                node = node.left;
                continue;
            }
            // 叶子节点 or 左右子节点都已经访问过
            node = stack.isEmpty() ? null: stack.pop();
        }
        Collections.reverse(data);
        return data;
    }
}