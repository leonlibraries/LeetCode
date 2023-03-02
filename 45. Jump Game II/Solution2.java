import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 s = new Solution2();
        int[] nums = new int[]{0};
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
     * 输入: nums = [4,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        // 逆推，倒着跳跃
        if (nums.length == 1) {
            return 0;
        }
        int[] currNodes = new int[]{nums.length - 1};
        for (int step = 1; step <= nums.length; step++) {
            currNodes = reverseJump(nums, currNodes);
            if (containStartPoint(currNodes)) {
                return step;
            }
        }
        return 0;
    }

    private int[] reverseJump(int[] nums, int[] currNodes) {
        List<Integer> list = new ArrayList<>(0);
        for (int node : currNodes) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] + i >= node) {
                    list.add(i);
                }
            }
        }
        return list.stream().mapToInt(e -> e).toArray();
    }

    private boolean containStartPoint(int[] nodes) {
        return Arrays.stream(nodes).anyMatch(e -> e == 0);
    }
}
