class Solution {
    public boolean isPalindrome(int x) {
        String value = String.valueOf(x);
        int len = value.length();
        StringBuilder sb = new StringBuilder();
        for(int i=len-1;i>=0;i--){
            sb.append(value.charAt(i));
        }
        return value.equals(sb.toString());
    }
}
