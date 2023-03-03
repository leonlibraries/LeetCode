public class Solution3 {

    public static void main(String[] args) {
        Solution3 s = new Solution3();
        int[] nums = new int[]{5, 6, 4, 4, 6, 9, 4, 4, 7, 4, 4, 8, 2, 6, 8, 1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6, 8, 8, 4, 4, 2, 0, 3, 8, 5};
        System.out.println(s.jump(nums));
    }

    /**
     * 证明：逆向跳跃最远的节点作为下一次跳跃的终点，就可以算出最小步长。（注意，这里的最远是相对于目标终点而言，因为这里是逆向跳跃）
     *
     * 证：
     *
     * 根据上面的命题，只需要证明：我们选取 "非最远节点" 的跳跃路径 "不可能" 取得更小的跳跃次数。
     *
     * 设 非最远节点为 x,最远节点为 y, x>y
     * 设 lJump(x) 表示 x 的上一条的跳跃次数
     *
     * 因为 x>y, 能跳跃到 x 的节点就一定能跳跃到 y
     * 所以 lJump(x)>=lJump(y)
     *
     * 设从x点跳跃到终点的跳跃次数为 jump(x) = lJump(x) + 1
     *
     * lJump(x) + 1 >= lJump(y) + 1
     *
     * 即 jump(x) >= jump(y)
     *
     * 因此，最远节点 y 一定是最小跳跃值，以此递归可以求得最小值。
     *
     * (这里特别注意，求最小值，并不需要列出所有的跳跃路径，这是区别贪心算法和动态规划的重要依据)
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        // 继续逆向，贪心+递归算法，追求最远距离跳跃
        return greedy(nums, nums.length - 1);
    }

    private int greedy(int[] nums, int target) {
        if (target == 0) {
            return 0;
        }
        for (int i = 0; i < target; i++) {
            if (nums[i] + i >= target) {
                return greedy(nums, i) + 1;
            }
        }
        return 0;
    }
}
