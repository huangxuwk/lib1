package com.dl.test2;

public class MaxSubArray {

	public MaxSubArray() {
	}
	
    public int maxSubArray(int[] nums) {
        final int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i=1; i<len; i++)
        {
            dp[i] = dp[i-1]>0? dp[i-1]+nums[i] : nums[i];
            max = dp[i] > max? dp[i] : max;

        }
        return max; 
    }
}