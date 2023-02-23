import java.util.Arrays;


public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = new int[]{415, 230, 471, 705, 902, 87};
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
     * @param nums 大小为 2 * n 的正整数数组
     * @return
     */
    public int maxScore(int[] nums) {
        // 推出状态转移公式，max_score(n-1) = max_score(n) + n * max_gcd(x,y)
        int n = nums.length / 2;
        int[] dp = new int[n + 1];
        for (; n > 0; n--) {
            MaxScoreInfo info = maxScore(dp, n, nums);
            dp[n - 1] = info.currMaxScore;
            nums = removeArrayElements(info.xIdx, info.yIdx, nums);
        }
        System.out.println(Arrays.toString(dp));
        return dp[0];
    }

    /**
     * max_score(n-1) = max_score(n) + n * max_gcd(x,y)
     *
     * @param dp
     * @param n      base1 的 N 值
     * @param nums
     * @return
     */
    public MaxScoreInfo maxScore(int[] dp, int n, int[] nums) {
        int[] currList = Arrays.copyOf(nums, nums.length);
        int maxGcd = 0, xIdx = -1, yIdx = -1;
        for (int i = 0; i < currList.length; i++) {
            int x = currList[i];
            for (int k = i + 1; k < currList.length; k++) {
                int y = currList[k];
                int gcd = gcd(x, y);
                if (gcd >= maxGcd) {
                    maxGcd = gcd;
                    xIdx = i;
                    yIdx = k;
                }
            }
        }
        return MaxScoreInfo.of(xIdx, yIdx, n * maxGcd + dp[n]);
    }

    private int[] removeArrayElements(int xIdx, int yIdx, int[] nums) {
        int[] result = new int[nums.length - 2];
        for (int i = 0, k = 0; i < nums.length; i++) {
            if (xIdx == i || yIdx == i) {
                continue;
            }
            result[k++] = nums[i];
        }
        return result;
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

    static class MaxScoreInfo {

        public int xIdx;

        public int yIdx;

        public int currMaxScore;

        public static MaxScoreInfo of(int xIdx, int yIdx, int currMaxScore) {
            MaxScoreInfo info = new MaxScoreInfo();
            info.xIdx = xIdx;
            info.yIdx = yIdx;
            info.currMaxScore = currMaxScore;
            return info;
        }
    }
}