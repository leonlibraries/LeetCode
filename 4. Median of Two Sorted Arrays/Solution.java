class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int total = len1 + len2;
        boolean isOdd = total % 2 == 1;
        int idx = isOdd ? total / 2 + 1 : total / 2;
        int c1 = 0, c2 = 0;
        for (int i = 1; i <= total; i++) {
            int value;
            if (c1 >= nums1.length) {
                value = nums2[c2++];
            } else if (c2 >= nums2.length) {
                value = nums1[c1++];
            } else {
                value = nums1[c1] <= nums2[c2] ? nums1[c1++] : nums2[c2++];
            }
            if (idx == i) {
                if (isOdd) {
                    return (double) value;
                } else {
                    int next;
                    if (c1 >= nums1.length) {
                        next = nums2[c2];
                    } else if (c2 >= nums2.length) {
                        next = nums1[c1];
                    } else {
                        next = nums1[c1] <= nums2[c2] ? nums1[c1] : nums2[c2];
                    }
                    return ((double) value + (double) next) / 2;
                }
            }
        }
        return 0.0;
    }
}
