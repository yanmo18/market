package Test;

public class Test {
    public static void main(String[] args) {
        int[] nums = {-1,0,3,5,9,12};
        System.out.println(erfen(nums, 0));
    }
    public static  int erfen(int[] nums, int target){
        int left = 0;
        int right = nums.length-1;
        int mid = 0;
        while (left <= right){
            mid = left + ((right-left)/2);
            if (target > nums[mid]){
                left = mid+1;
            }else if (target < nums[mid]){
                right = mid-1;
            }else {
                return mid;
            }
        }
        return -1;
    }
}
