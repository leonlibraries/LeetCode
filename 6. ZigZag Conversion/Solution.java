class Solution {
    public String convert(String s, int numRows) {
        if(s==null || s.length() == 0){
            return "";
        }
        if(s.length() < 3 || numRows < 2){
            return s;
        }
        int len = s.length();
        int unitLen = 2 * numRows -2;
        int tailSeg = numRows-2;
        int portions = len / unitLen + 1;
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for(int row=0; row<numRows; row++){
            boolean zag = (numRows-row) ==1 || row == 0;
            for(int i=0;i<portions;i++){
                int index = i*unitLen + row;
                if(index <= len-1){
                    sb.append(s.charAt(index));
                }
                if(!zag){
                   // 非拐点
                   index = unitLen * (i+1) - row;
                   if(index <= len-1){
                     sb.append(s.charAt(index));
                   }
                }
            }
        }
        return sb.toString();
    }
}
