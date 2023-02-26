import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        // 1,4 => 3,5 => 0,2
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
        Map<Integer, Map<Set<Integer>, Integer>> map = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            dfs(map, i, nums);
        }
        return map.get(n).values().stream().findFirst().get();
    }


    private void dfs(Map<Integer, Map<Set<Integer>, Integer>> map, int i, int[] nums) {
        //上一次选择的 markScore 值
        Map<Set<Integer>, Integer> markScore = map.get(i - 1);
        Map<Set<Integer>, Integer> currMarkScore = new HashMap<>();
        if (markScore == null) {
            // first
            for (int j = 0; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    Set<Integer> mark = new HashSet<>();
                    mark.add(j);
                    mark.add(k);
                    currMarkScore.put(mark, i * gcd(nums[j], nums[k]));
                }
            }
        } else {
            markScore.forEach((key, value) -> {
                for (int j = 0; j < nums.length; j++) {
                    if (!key.contains(j)) {
                        for (int k = j + 1; k < nums.length; k++) {
                            if (!key.contains(k)) {
                                Set<Integer> mark = new HashSet<>(key);
                                mark.add(j);
                                mark.add(k);
                                int curr = currMarkScore.getOrDefault(mark, 0);
                                int max = Math.max(value + i * gcd(nums[j], nums[k]), curr);
                                currMarkScore.put(mark, max);
                            }
                        }
                    }
                }
            });
        }
        map.put(i, currMarkScore);
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