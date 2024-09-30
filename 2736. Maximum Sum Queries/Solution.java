import java.util.Arrays;

/**
 * 给你两个长度为 n 、下标从 0 开始的整数数组 nums1 和 nums2 ，另给你一个下标从 1 开始的二维数组 queries ，其中 queries[i] = [xi, yi] 。
 * <p>
 * 对于第 i 个查询，在所有满足 nums1[j] >= xi 且 nums2[j] >= yi 的下标 j (0 <= j < n) 中，找出 nums1[j] + nums2[j] 的 最大值 ，如果不存在满足条件的 j 则返回 -1 。
 * <p>
 * 返回数组 answer ，其中 answer[i] 是第 i 个查询的答案。
 */
public class Solution {

    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length;
        int[] answer = new int[queries.length];

        // 对 nums1 和 nums2 进行排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        for (int i = 0; i < queries.length; i++) {
            int xi = queries[i][0];
            int yi = queries[i][1];

            // 使用二分查找找到 nums1 中满足条件的最大下标
            int left = 0, right = n - 1, maxIndex = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums1[mid] >= xi) {
                    maxIndex = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // 判断是否存在满足条件的下标，若不存在则返回 -1
            if (maxIndex == -1 || nums2[n - 1] < yi) {
                answer[i] = -1;
            } else {
                // 计算最大和
                int maxSum = nums1[maxIndex] + nums2[n - 1];
                for (int j = n - 2; j >= 0; j--) {
                    if (nums2[j] >= yi) {
                        maxSum = Math.max(maxSum, nums1[maxIndex] + nums2[j]);
                    } else {
                        break;
                    }
                }
                answer[i] = maxSum;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 4, 6, 8, 10};
        int[] nums2 = {1, 3, 5, 7, 9};
        int[][] queries = {{2, 1}, {4, 3}, {6, 5}, {8, 7}, {10, 9}};
        Solution s = new Solution();
        int[] answer = s.maximumSumQueries(nums1, nums2, queries);
        System.out.println(Arrays.toString(answer)); // 输出: [11, 13, 15, 17, 19]
    }
}