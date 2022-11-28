public class Solution2 {

    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        String data = s.charAt(0) + "";
        int maxPalindromeLen = 0;
        for (int i = 0; i < s.length(); i++) {
            int subLen = s.length() - i;
            if (s.length() - i <= maxPalindromeLen) {
                break;
            }
            for (int k = i; k <= subLen / 2 + i; k++) {
                int tail = Math.min(s.length(), 2 * (k - i) + i + 1);
                String subStr = s.substring(i, tail);
                if (isPalindrome(subStr) && subStr.length() > maxPalindromeLen) {
                    data = subStr;
                    maxPalindromeLen = subStr.length();
                }
                if (tail + 1 <= s.length()) {
                    subStr = s.substring(i, tail + 1);
                    if (isPalindrome(subStr) && subStr.length() > maxPalindromeLen) {
                        data = subStr;
                        maxPalindromeLen = subStr.length();
                    }
                }
            }
        }
        return data;
    }

    /**
     * 检查是否是回文串
     */
    public boolean isPalindrome(String subStr) {
        int len = subStr.length();
        if (len <= 0) {
            return false;
        }
        for (int i = 0, k = subStr.length() - 1; i < subStr.length() / 2; i++, k--) {
            char a = subStr.charAt(i);
            char b = subStr.charAt(k);
            if (a != b) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution2 s2 = new Solution2();
        System.out.println(s2.longestPalindrome("ccc"));
    }
}
