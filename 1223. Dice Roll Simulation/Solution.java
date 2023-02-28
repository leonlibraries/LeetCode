class Solution {

    private final int mod = 10 ^ 9 + 7;

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

        int[][] dp = new int[n][];

        return 0;
    }
}