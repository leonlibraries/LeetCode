/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution1 {
    public Node connect(Node root) {
        if(root == null){
            return null;
        }
        BlockingQueue<Node> queue = new ArrayBlockingQueue(1000);
        queue.offer(root);
        while(!queue.isEmpty()){
            List<Node> list = new ArrayList<>(3);
            queue.drainTo(list);
            if(list != null && !list.isEmpty()){
               Iterator<Node> iter =  list.iterator();
               Node last = null;
               while(iter.hasNext()){
                   Node curr = iter.next();
                   if(curr.left != null){
                       queue.offer(curr.left);
                   }
                   if(curr.right != null){
                       queue.offer(curr.right);
                   }
                   if(last != null){
                      last.next =  curr;
                   }
                   last = curr;
               }
            }
        }
        return root;
    }
}