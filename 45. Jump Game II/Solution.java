class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = new int[]{5, 6, 4, 4, 6, 9, 4, 4, 7, 4, 4, 8, 2, 6, 8, 1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6, 8, 8, 4, 4, 2, 0, 3, 8, 5};
        System.out.println(s.jump(nums));
    }

    /**
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
     *
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     *
     * 0 <= j <= nums[i] 
     * i + j < n
     * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
     *
     * 示例 1:
     *
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        return dfs(nums, 0);
    }

    /**
     * 深度优先+递归
     *
     * @param nums
     * @param curr
     * @return
     */
    private int dfs(int[] nums, int curr) {
        if (curr == nums.length - 1) {
            return 0;
        }
        int step = nums[curr];
        int minStep = nums.length;
        for (int s = 1; s <= step; s++) {
            if (curr + s < nums.length) {
                int currStep = dfs(nums, curr + s) + 1;
                minStep = Math.min(currStep, minStep);
            }
        }
        return minStep;
    }
}