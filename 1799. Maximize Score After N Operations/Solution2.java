public class Solution2 {

    public static void main(String[] args) {
        Solution2 s = new Solution2();
        int[] nums = new int[]{533593, 39242, 396330, 210143, 363784, 103836, 215757, 601236, 706901, 940118, 453982, 620138, 203549, 516268};
        System.out.println(s.maxScore(nums));
    }

    int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

    /**
     * 深度优先 + 递归
     *
     * @param n
     * @param dp
     * @param i
     * @param mask
     * @return
     */
    int dfs(int[] n, int[][] dp, int i, int mask) {
        if (i > n.length / 2) {
            return 0;
        }
        if (dp[i][mask] == 0) {
            for (int j = 0; j < n.length; ++j) {
                for (int k = j + 1; k < n.length; ++k) {
                    // 这里限定了 n 的最大值为 14 ，所以才可以用 bit 作为 mask 表示 j,k 已经被选取
                    int new_mask = (1 << j) + (1 << k);
                    if ((mask & new_mask) == 0) {
                        dp[i][mask] = Math.max(dp[i][mask], i * gcd(n[j], n[k]) + dfs(n, dp, i + 1, mask + new_mask));
                    }
                }
            }
        }
        return dp[i][mask];
    }

    int maxScore(int[] n) {
        return dfs(n, new int[n.length / 2 + 1][1 << n.length], 1, 0);
    }
}
