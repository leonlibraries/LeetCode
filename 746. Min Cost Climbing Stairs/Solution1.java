import java.util.Arrays;

class Solution1 {


    public static void main(String[] args) {

        int[] cost = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        Solution1 s = new Solution1();
        System.out.println(s.minCostClimbingStairs(cost));
    }

    /**
     * 根据题意分析出状态转换函数
     *
     * 假设楼梯有 N 阶，每阶 i 的cost 为 c(i)
     *
     * 最小成本：f(n)= ARG min(f(n-1)+c(n-1),f(n-2)+c(n-2))
     *
     * 这里用动态规划求解
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        if (cost.length < 2) {
            return 0;
        }
        int[] currStepMinCost = new int[cost.length + 1];
        Arrays.fill(currStepMinCost, Integer.MAX_VALUE);
        currStepMinCost[0] = 0;
        currStepMinCost[1] = 0;
        currStepMinCost[2] = Math.min(cost[0], cost[1]);
        for (int i = 3; i <= cost.length; i++) {
            int min = Math.min(currStepMinCost[i - 1] + cost[i - 1], currStepMinCost[i - 2] + cost[i - 2]);
            currStepMinCost[i] = min;
        }
        return currStepMinCost[cost.length];
    }

}