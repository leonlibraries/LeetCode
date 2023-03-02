class Solution {

    private static final long MOD = 1_000_000_000L + 7;

    public static void main(String[] args) {
        Solution s = new Solution();
        int n = 30;
        int[] rollMax = new int[]{2, 3, 1, 2, 1, 2};
        System.out.println("sum=" + s.dieSimulator(n, rollMax));
    }

    /**
     * 有一个骰子模拟器会每次投掷的时候生成一个 1 到 6 的随机数。
     *
     * 不过我们在使用它时有个约束，就是使得投掷骰子时，连续 掷出数字 i 的次数不能超过 rollMax[i]（i 从 1 开始编号）。
     *
     * 现在，给你一个整数数组 rollMax 和一个整数 n，请你来计算掷 n 次骰子可得到的不同点数序列的数量。
     *
     * 假如两个序列中至少存在一个元素不同，就认为这两个序列是不同的。由于答案可能很大，所以请返回 模 10^9 + 7 之后的结果。
     *
     *
     * @param n         1 <= n <= 5000
     * @param rollMax   1 <= rollMax[i] <= 15 && rollMax.length == 6
     * @return
     */
    public int dieSimulator(int n, int[] rollMax) {
        long[][] dp = new long[7][n + 1];
        for (int i = 0; i < n; i++) {
            dp = bfs(dp, i, n, rollMax);
        }
        return (int) (sum(dp) % MOD);
    }

    /**
     * 我们假设 0<k<N
     *
     *
     * 设第 k 次 摇骰子函数 U(x,y) 次数
     *
     * 第一次       第二次
     *
     * U(1,1)=1 => U(1,2) x (假设 rollMax[1]=1)
     * 			   U(2,1) 1
     * 			   U(3,1) 1
     * 			   U(4,1) 1
     * 			   U(5,1) 1
     * 			   U(6,1) 1
     *
     * U(2,1)=1 => U(1,1) 1
     *             U(2,2) x  (假设 rollMax[2]=1)
     *             U(3,1) 1 （和上面合并）
     *             U(4,1) 1 （和上面合并）
     *             U(5,1) 1 （和上面合并）
     *             U(6,1) 1
     *
     * U(3,1)=1  ...
     * U(4,1)=1  ...
     * U(5,1)=1  ...
     * U(6,1)=1  ...
     *
     * @param dp
     * @param n
     * @param rollMax
     * @return
     */
    private long[][] bfs(long[][] dp, int i, int n, int[] rollMax) {
        if (i == 0) {
            for (int j = 1; j <= 6; j++) {
                dp[j][1] = 1;
            }
            return dp;
        }
        long[][] newDp = new long[7][n + 1];
        for (int j = 1; j <= 6; j++) {
            long[] fn = dp[j];
            for (int k = 1; k < fn.length; k++) {
                if (fn[k] > 0) {
                    split(newDp, j, k, fn[k], rollMax);
                }
            }
        }
        return newDp;
    }

    /**
     * 展开
     *
     * @param dp
     * @param ln         lastNum
     * @param lrt        lastRepeatTimes
     * @param p
     * @param rollMax
     */
    private void split(long[][] dp, int ln, int lrt, long p, int[] rollMax) {
        boolean breakOut = lrt >= rollMax[ln - 1];
        for (int j = 1; j <= 6; j++) {
            if (j == ln) {
                dp[j][lrt + 1] += breakOut ? 0 : p;
                dp[j][lrt + 1] = dp[j][lrt + 1] % MOD;
            } else {
                dp[j][1] += p;
                dp[j][1] = dp[j][1] % MOD;
            }
        }
    }

    /**
     * 汇总
     *
     * @param dp
     * @return
     */
    private long sum(long[][] dp) {
        long sum = 0L;
        for (long[] item : dp) {
            for (long value : item) {
                sum += value;
            }
        }
        return sum;
    }
}