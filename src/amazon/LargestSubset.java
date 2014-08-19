package amazon;


/**
 * 
 * 
 * @author plsek
 *
 */
public class LargestSubset {

    public static int getSubset(int[] arr) {
        if (arr.length == 0) {
            System.out.println("SUM: ?, Array Empty.");
            return 0;
        }
            
        int maxstart = -1;
        int maxend = -1; 
        int maxsum = 0x80000000;    //the smallest integer value of 32-bit integer
        
        int start = 0;
        int end = 0;
        int sum = 0;
        
        for(; end < arr.length ; end++) {
            sum = sum + arr[end];
            if (sum < 0) { 
                if (sum > maxsum) {      // handling arrays containing negative ints 
                    maxsum = sum;
                    maxstart = start;
                    maxend = end;
                }
                sum = 0;
                start = end + 1;
            } 
            else 
                if (sum > maxsum) {
                    maxsum = sum;
                    maxstart = start;
                    maxend = end;
                }
        }

        System.out.println("SUM: " + maxsum + ", start:" + maxstart + ", end : " + maxend);
        
        return maxsum;
    }
    
    
    public static void main(String[] args) {
        
        int[] arr1 = new int[] { 1 , 2, 3, 4 };                  //  res:10
        int[] arr2 = new int[] { 1 , 2, 3, 4, -10 };             //  res:10 
        int[] arr3 = new int[] { -10, 1 , 2, 3, 4,  };           //  res:10 
        int[] arr4 = new int[] { 1 , 2, -10, 3, 4 };             //  res:7 
        int[] arr5 = new int[] { -10, 1 , 1, -1, 1, 1, -20 };    //  res:3
        int[] arr6 = new int[] { -5 , -2, -3, -4 };              //  res:-2 
        int[] arr7 = new int[] {-3, -2, 5, -1, -2, 2, 3, -2};    //  res: 7
        int[] arr8 = new int[] {};
        
        
        String str;
        str[0];
        
        getSubset(arr1);
        getSubset(arr2);
        getSubset(arr3);
        getSubset(arr4);
        getSubset(arr5);
        getSubset(arr6);
        getSubset(arr7);
        getSubset(arr8);
        
    }
    
}
