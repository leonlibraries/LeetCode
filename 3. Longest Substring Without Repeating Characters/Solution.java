class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        char[] queue = new char[35536];
        int len = 0, left = 0, right = 0;
        for (char c : s.toCharArray()) {
            int temp = left;
            for (int i = left; i <= right; i++) {
                if (queue[i] == c) {
                    temp = i + 1;
                }
            }
            if (temp != left) {
                len = Math.max(right - left, len);
                left = temp;
            }
            queue[right++] = c;
        }
        len = Math.max(right - left, len);
        return len;
    }
}
