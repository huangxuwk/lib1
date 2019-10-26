package com.dl.test2;

/*
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
 */
public class JumpFloorII {
	
	/*
	 * 1 : 1
	 * 2 : 2
	 * 3 : 111 12 21 3
	 */
    public int JumpFloor(int target) {
    	if (target == 0) {
    		return 1;
    	} else if (target < 0) {
    		return 0;
    	}
    	
    	int result = 0;
    	for (int i = 1; i <= target; i++) {
    		result += JumpFloor(target - i);
    	}
    	
    	return result;
    }
    
    public static void main(String[] args) {
		System.out.println(new JumpFloorII().JumpFloor(3));
	}
}
