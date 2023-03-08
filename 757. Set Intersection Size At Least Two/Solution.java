import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 官方思路
 *
 * @author leonwong
 */
class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] intervals = new int[][]{{2, 10}, {3, 7}, {3, 15}, {4, 11}, {6, 12}, {6, 16}, {7, 8}, {7, 11}, {7, 15}, {11, 12}};
        System.out.println(s.intersectionSizeTwo(intervals));
    }

    /**
     * 给你一个二维整数数组 intervals ，其中 intervals[i] = [starti, endi] 表示从 starti 到 endi 的所有整数，包括 starti 和 endi 。
     *
     * 包含集合 是一个名为 nums 的数组，并满足 intervals 中的每个区间都 至少 有 两个 整数在 nums 中。
     *
     * 例如，如果 intervals = [[1,3], [3,7], [8,9]] ，那么 [1,2,4,7,8,9] 和 [2,3,4,8,9] 都符合 包含集合 的定义。
     * 返回包含集合可能的最小大小。（最小！！！）
     *
     * 输入：intervals = [[1,3],[3,7],[8,9]]
     * 输出：5
     * 解释：nums = [2, 3, 4, 8, 9].
     * 可以证明不存在元素数量为 4 的包含集合。
     *
     * @param intervals
     * @return
     */
    public int intersectionSizeTwo(int[][] intervals) {
        int n = intervals.length;
        int res = 0;
        int m = 2;
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        System.out.println(Arrays.deepToString(intervals));
        // 这里涉及到贪心的核心思路是，区间集合里，我们按照左端点升序排序，从后往前看，能与前面的集合产生交集的，一定是区间靠前的数字
        // 以最后一个区间为例，[Sn,En], 我们设最小化交集总体的集合为 S, 每个区间的至少有m个元素在集合 S 中，这里 m=2
        // 那么，根据贪心策略，最优选择一定是 将 Sn 和 Sn +1 两个元素加入到 S 中
        // 以此思路，逐步遍历其他区间，看其他区间是否也含有相同元素，以此保证 S 的最小化。
        List<Integer>[] temp = new List[n];
        for (int i = 0; i < n; i++) {
            temp[i] = new ArrayList<Integer>();
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = intervals[i][0], k = temp[i].size(); k < m; j++, k++) {
                res++;
                help(intervals, temp, i - 1, j, m);
            }
        }
        return res;
    }

    public void help(int[][] intervals, List<Integer>[] temp, int pos, int num, int m) {
        for (int i = pos; i >= 0; i--) {
            if (intervals[i][1] < num) {
                break;
            }
            if (temp[i].size() < m) {
                temp[i].add(num);
            }
        }
    }

}