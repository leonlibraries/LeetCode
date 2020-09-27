/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution1 {
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = allPath(root);
        result = result.stream()
                       .filter(item->item.stream().mapToInt(e->e).sum() == sum)
                       .map(item->{
                           Collections.reverse(item);
                           return item;
                       })
                       .collect(Collectors.toList());
        return result;
    }


    List<List<Integer>> allPath(TreeNode root){
        if(root == null){
            return new ArrayList<>(2);
        }
        List<List<Integer>> list = new ArrayList<>(2);
        if(isLeafNode(root)){
            List<Integer> item = new ArrayList<>(2);
            item.add(root.val);
            list.add(item);
            return list;
        }
        if(root.left != null){
            List<List<Integer>> left = allPath(root.left);
            left.forEach(
                item->{
                    item.add(root.val);
                }
            );
            list.addAll(left);
        }
        if(root.right != null){
            List<List<Integer>> right = allPath(root.right);
            right.forEach(
                item->{
                    item.add(root.val);
                }
            );
            list.addAll(right);
        }
        return list;
    }

    boolean isLeafNode(TreeNode node){
        return node.left == null && node.right == null;
    }
}