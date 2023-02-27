import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = new int[]{533593, 39242, 396330, 210143, 363784, 103836, 215757, 601236, 706901, 940118, 453982, 620138, 203549, 516268};
        System.out.println(s.maxScore(nums));
    }

    /**
     * 你必须对这个数组执行 n 次操作。
     *
     * 在第 i 次操作时（操作编号从 1 开始），你需要：
     *
     * 选择两个元素 x 和 y 。
     * 获得分数 i * gcd(x, y) 。
     * 将 x 和 y 从 nums 中删除。
     * 请你返回 n 次操作后你能获得的分数和最大为多少。
     *
     * 函数 gcd(x, y) 是 x 和 y 的最大公约数。
     *
     * 1 <= n <= 7
     * nums.length == 2 * n
     * 1 <= nums[i] <= 106
     *
     * @param nums 大小为 2 * n 的正整数数组
     * @return
     */
    public int maxScore(int[] nums) {
        int n = nums.length / 2;
        // n -> (mask->score)
        int[][] dp = new int[n + 1][1 << nums.length];
        Map<Integer, Set<Integer>> maskMembers = new HashMap<>();
        init(dp, nums, maskMembers);
        for (int i = 2; i <= n; i++) {
            dfs(dp, i, nums, maskMembers);
        }
        return dp[n][(1 << nums.length) - 1];
    }

    private void init(int[][] dp, int[] nums, Map<Integer, Set<Integer>> maskMembers) {
        Set<Integer> members = new HashSet<>();
        for (int j = 0; j < nums.length; j++) {
            for (int k = j + 1; k < nums.length; k++) {
                int mask = (1 << j) + (1 << k);
                members.add(mask);
                dp[1][mask] = gcd(nums[j], nums[k]);
            }
        }
        maskMembers.put(1, members);
    }

    private void dfs(int[][] dp, int i, int[] nums, Map<Integer, Set<Integer>> maskMembers) {
        Set<Integer> lastMembers = maskMembers.get(i - 1);
        Set<Integer> currMaskMembers = new HashSet<>();
        for (int currMask : lastMembers) {
            for (int j = 0; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    int nMask = (1 << j) + (1 << k);
                    if ((currMask & nMask) == 0) {
                        int mask = currMask + nMask;
                        currMaskMembers.add(mask);
                        dp[i][mask] = Math.max(i * gcd(nums[j], nums[k]) + dp[i - 1][currMask], dp[i][mask]);
                    }
                }
            }
        }
        maskMembers.put(i, currMaskMembers);
    }


    /**
     * 欧几里得算法
     *
     * @param x
     * @param y
     * @return
     */
    private int gcd(int x, int y) {
        int m = x % y;
        while (m != 0) {
            x = y;
            y = m;
            m = x % y;
        }
        return y;
    }
}