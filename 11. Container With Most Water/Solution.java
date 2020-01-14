class Solution {
    public int maxArea(int[] height) {
        int head = 0;
        int tail = height.length - 1;
        int max = 0;
        while(tail > head){
            int distance = tail - head;
            if(height[head] >= height[tail]){
                max = Math.max(max,height[tail] * distance);
                tail --;
            } else {
                max = Math.max(max,height[head] * distance);
                head ++;
            }
        }
        return max;
    }
}
