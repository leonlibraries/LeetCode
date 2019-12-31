class Solution {
    public String longestPalindrome(String s) {
        String best = s.length() == 1 ? s : "";
        for (int i = 0; i < s.length(); i++) {
            for (int k = i + 1; k <= (s.length() - i) / 2 + i + 1; k++) {
                int len = k - i;
                String sub = s.substring(i, k);

                if (len * 2 + 1 < best.length()) {
                    continue;
                }
                // 回文
                int end = k + len;
                if (end <= s.length() && reverseEquals(sub, s, k)) {
                    String palindrome = s.substring(i, end);
                    best = palindrome.length() > best.length() ? palindrome : best;
                }
                end = k - 1 + len;
                if (end <= s.length() && reverseEquals(sub, s, k - 1)) {
                    String palindrome = s.substring(i, end);
                    best = palindrome.length() > best.length() ? palindrome : best;
                }
            }
        }
        return best;
    }

    /**
     * 反转
     */
    public boolean reverseEquals(String sub, String s, int start) {
        for (int i = sub.length() - 1, k = start; i >= 0; i--, k++) {
            char c1 = sub.charAt(i);
            char c2 = s.charAt(k);
            if (c1 != c2) {
                return false;
            }
        }
        return true;
    }
}
