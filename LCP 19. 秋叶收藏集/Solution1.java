public class Solution1 {
    public int minimumOperations(String leaves) {
       char[] data = leaves.toCharArray();
       if(data.length < 3){
           return 0;
       }
       int[][] dp = new int[data.length][3];
       dp[0][0] = data[0] == 'y' ? 1 : 0;
       dp[0][1] = dp[0][2] = dp[1][2] = Integer.MAX_VALUE;
       for(int i = 1;i< data.length; i++){
           char c = data[i];
           int isYellow = c == 'y' ? 1:0;
           int isRed = c == 'r' ? 1:0;
           dp[i][0] = dp[i-1][0] + isYellow;
           dp[i][1] = Math.min(dp[i-1][0],dp[i-1][1]) + isRed;
           dp[i][2] = Math.min(dp[i-1][1],dp[i-1][2]) + isYellow;
       }
       return dp[data.length-1][2];
   }
}