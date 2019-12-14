class Solution {
    public int[] twoSum(int[] nums, int target) {
        for(int i=0;i<nums.length;i++){
            for(int k=0;k<nums.length;k++){
                if( k == i) continue;
                if(nums[k]+nums[i]==target){
                    return new int[]{i,k};
                }
            }
        }
        return null;
    }
}
