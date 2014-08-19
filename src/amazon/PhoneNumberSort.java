package amazon;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PhoneNumberSort {

    Number[] numbers;
    
    public PhoneNumberSort(String[] numbers) {
        this.numbers = parseNumbers(numbers);
    }
    
    /**
     * MERGESORT
     */
    public void mergesort(int start,int end) {
        if (start == end)
            return;
        int len = (end - start) / 2 ;
        mergesort(start,start+len);
        mergesort(start+len+1,end);
        
        merge(start,start+len+1,end);
    }
    
    public void merge(int left,int middle, int right) {
        int leftStart = left;
        int leftEnd = middle - 1;
        int index = 0;
        
        Number[] temp = new Number[right-left+1];
        
        // Main loop
        while( left <= leftEnd && middle <= right )
            if( numbers[left].isSmaller(numbers[middle]) )
                temp[ index++ ] = numbers[ left++ ];
            else
                temp[ index++ ] = numbers[ middle++ ];
        
        while( left <= leftEnd )    // Copy rest of first half
            temp[ index++ ] = numbers[ left++ ];
        
        while( middle <= right )  // Copy rest of right half
            temp[ index++ ] = numbers[ middle++ ];
        
        for (int i = 0 ; i < temp.length; i ++) 
            numbers[leftStart + i] = temp[i];
        
    }
    
    public int getLenght() {
        return numbers.length;
    }
    
    public void print() {
        for (Number num : this.numbers) {
            System.out.print(num.toString() + ", ");
        }
        System.out.println("");
    }
    
    /**
     */
    public static void main(String[] args) {

        // numbers given in a form : "ddd ddd dddd", where "d" is a digit 
        String [] numbers = {"111 0000 1113", "111 111 1113","111 111 1110","111 111 1111", "111 111 1112", "111 100 1113"};
        PhoneNumberSort sort = new PhoneNumberSort(numbers);       
        sort.mergesort(0,sort.getLenght()-1);
        sort.print();
    }

    /**
     * Processing the input.
     */
    public Number[] parseNumbers (String[] numbers) {
        Number[] tmp = new Number[numbers.length];   
        int index = 0;
        for (String str: numbers) {
            try {
                tmp[index] = new Number(str);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }
            
            index++;
        }
        
        Number[] result = new Number[index];
        for(int i = 0 ;i < index; i++) {
            result[i] = tmp[i];
        }
        
        return result;
    }
    
}


class Number {
    
    int area;
    int n1;
    int n2;
    
    private static final String space = " ";
    private static final String match = "\\d\\d\\d \\d\\d\\d \\d\\d\\d\\d";
    
    public Number (String str) throws Exception {
        
        if (!Pattern.matches(match, str)) {
            throw new Exception("ERROR: wrong phone number format! :" + str + " [Number Ignored] ");
        }
        
        String[] nums = str.split(space);
        int area = Integer.parseInt(nums[0]);
        int n1 = Integer.parseInt(nums[1]);
        int n2 = Integer.parseInt(nums[2]);
        
        if (area < 100 || area > 999 || n1 < 100 || n1 >999 || n2 < 1000 || n2 > 9999) {
            throw new Exception("ERROR: wrong phone number value!" + 
                    "\n\t number:" + area + " " + n1 + " " + n2 + ", [Number Ignored]");
        }
            
        this.area = area;
        this.n1 = n1;
        this.n2 = n2;
    }
    
    /**
     * Returns TRUE if "this" < y.
     * 
     */
    public boolean isSmaller( Number y) {
        if (this.area < y.area)
            return true;
        else 
            if (this.area > y.area)
                return false;
        
        if (this.n1 < y.n1)
            return true;
        else
            if (this.n1 > y.n1)
                return false;
        
        if (this.n2 < y.n2) 
            return true;
        else
            if (this.n2 > y.n2)
                return false;
        
        //they are equal
        return false;
    }
    
    public String toString() {
       return new String(this.area + " " + this.n1 + " " + this.n2 );
    }
}