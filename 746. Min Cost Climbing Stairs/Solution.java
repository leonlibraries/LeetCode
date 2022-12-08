class Solution {


    public static void main(String[] args) {

        int[] cost = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        Solution s = new Solution();
        System.out.println(s.minCostClimbingStairs(cost));
    }

    /**
     * 根据题意分析出状态转换函数
     *
     * 假设楼梯有 N 阶，每阶 i 的cost 为 c(i)
     *
     * 最小成本：f(n)= ARG min(f(n-1)+c(n-1),f(n-2)+c(n-2))
     *
     * 先尝试用递归方式求解
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        if (cost.length == 0) {
            return 0;
        }
        return minCost4ClimbingAt(cost.length, cost);
    }

    private int minCost4ClimbingAt(int i, int[] cost) {
        if (i == 0 || i == 1) {
            // 可以这么理解，花费0成本就能到1阶 or 2阶
            return 0;
        }
        return Math.min(minCost4ClimbingAt(i - 1, cost) + cost[i - 1], minCost4ClimbingAt(i - 2, cost) + cost[i - 2]);
    }
}