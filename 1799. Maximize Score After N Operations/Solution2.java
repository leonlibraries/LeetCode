import java.util.Arrays;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 s = new Solution2();
        int[] nums = new int[]{415, 230, 471, 705, 902, 87};
        System.out.println(s.maxScore(nums));
    }

    int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

    int dfs(int[] n, int[][] dp, int i, int mask) {
        if (i > n.length / 2) {
            return 0;
        }
        if (dp[i][mask] == 0) {
            for (int j = 0; j < n.length; ++j) {
                for (int k = j + 1; k < n.length; ++k) {
                    int new_mask = (1 << j) + (1 << k);
                    if ((mask & new_mask) == 0) {
                        dp[i][mask] = Math.max(dp[i][mask], i * gcd(n[j], n[k]) + dfs(n, dp, i + 1, mask + new_mask));
                    }
                }
            }
        }
        System.out.println(Arrays.toString(dp[i]));
        return dp[i][mask];
    }

    int maxScore(int[] n) {
        return dfs(n, new int[n.length / 2 + 1][1 << n.length], 1, 0);
    }
}
