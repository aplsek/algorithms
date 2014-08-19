package twoSigmaTest;


import java.util.ArrayList;

/**
 * Author : Ales Plsek, http://www.cs.purdue.edu/homes/plsek/
 * 
 *
 */
public class TwoSigmaCodeTestRoadWork {
    
    /** 
     * we store all the contracts here
     */
    static ArrayList<Contract> contracts;
   
    /**
     * this will be the result - the number of the fraud Feets
     */
    static int result = 0;
    
  
    public static int fraudFeet(int[] start, int[] end) {
        
        /** 
         * check the input
         */
        if (!checkInput(start,end)) {
            System.out.println("Common, don't be EVIL!");
            return -1;
        }
        
        /**
         * init the array of contracts
         */
        contracts = new ArrayList<Contract>();
        for (int i=0 ; i < start.length; i++) {
            contracts.add(new Contract(start[i],end[i]));
        }
        
        
        
        /**
         * THE ALGORITHM STARTS HERE:
         * 
         * Description.
         *   1. go till there are any contracts
         *                                                  ILLUSTRATION
         *   2. get the earliest contract c
         *   3. get the second earliest contract x
         *                                                      c         x
         *   4. if c and x do not overlap                   (--------)    
         *          remove c from contracts, c is OK                  (------)
         *   
         *   5  else
         *           if x is inside c                          c   x
         *              remove x from contracts              (-------)
         *              result + = lenght_of_x                  (---)
         *              update all the remaining contracts that starts earlier
         *                      than x ends to start at x.end 
         *           else 
         *              c is processed,                                      c      x
         *                   remove it and x is updated to start at c.end   (------)
         *              all the remaining contracts that could                 (-------)
         *                  start before c ended are updated to start at c.end
         * 
         */
        while (!contracts.isEmpty()) {
            
            Contract c = getMin();
            contracts.remove(c);
            Contract x = getMin();
            if (x == null)         // we are empty, c is the last, can not overlap with anyone
                break;
            if (x.s > c.e) {
                continue;        // x will be picked as c in the next loop
            } 
            else {
                if (x.e < c.e) {
                    result += x.e - x.s;
                    c.s = x.e;
                    update(x.e);
                    contracts.add(c);      // c is updated and back in the game
                } 
                else {
                    result += c.e - x.s;   // c is processed and out of the game
                    x.s = c.e;
                    update(c.e);           // the next loop will start with one of the contracts that start at c.e
                }
            }
        }
        return result;
    }
    
    
    /**
     * Returns a contract that has the minimal starting point.
     * 
     * @return
     */
    private static Contract getMin() {
        Contract min = null;
        for (Contract c : contracts) {
            if (min == null) 
                min = c;
            else
                if (min.s > c.s) 
                    min = c;
        }
        return min;
    }

    
    /** 
     * We update all the contracts to start at idx position instead of their old starting position 
     * - this means that the feet from c.start--> till idx were already accounted as fraud feet
     * 
     * @param idx
     */
    private static void update(int idx) {
        for (Contract c : contracts) {
            if (c.s < idx) 
                c.s = idx;
        }
    }
    
    /**
     * For testing only..
     */
    public static void print() {
        for (Contract c : contracts) {
            System.out.print(c.s + "-->");
            System.out.print(c.e);
            System.out.println("");
        }
    }
    
    /**
     * reset the result for the next test
     */
    public static void init () {
        result = 0;
    }
    
    /**
     * check input values
     */
    public static boolean checkInput (int[] start, int[] end) {
        
        if (start.length != start.length)
            return false;
        
        for (int i = 0; i < start.length; i++) {
            if (start[i] < 0 || end[i] < 0 || end[i] < start[i])
                return false;
        }
        
        return true;
    }
    
    
    /**
     * Here we go.....
     * 
     */
    public static void main(String[] args) { 
    
        System.out.println("TEST 1 --------------: ");
        init();
        int [] start = new int[]{50,50,50};
        int [] end = new int[]{58,58,60};
        int res;
        res = fraudFeet(start,end);
        System.out.println("result 1: " + res);
        
        
        
        
        System.out.println("TEST 2 --------------: ");
        init();
        start = new int[]{171234,12,20,30};
        end = new int[]{171236,20,30,40};
        res = fraudFeet(start,end);
        System.out.println("result 2 : " + res);
        
        

        System.out.println("TEST 3 --------------: ");
        init();
        start = new int[]{12,32,92};
        end = new int[]{991,161,1093};
        res = fraudFeet(start,end);
        System.out.println("result 3 : " + res);
        
        
        System.out.println("TEST 4 --------------: ");
        init();
        start = new int[]{0,0,0};
        end = new int[]{5,5,5};
        res = fraudFeet(start,end);
        System.out.println("result 4 : " + res);
        
        
        
        System.out.println("TEST 5 --------------: ");
        init();
        start = new int[]{0,5,8};
        end = new int[]{10,7,9};
        res = fraudFeet(start,end);
        System.out.println("result 5 : " + res);
        
        
        System.out.println("TEST 6 --------------: ");
        init();
        start = new int[]{0,4,7};
        end = new int[]{5,9,10};
        res = fraudFeet(start,end);
        System.out.println("result 6 : " + res);
        
    }
}






/**
 * Our data structure representing a contract
 * 
 * @author plsek
 *
 */
class Contract {
    public int s;
    public int e;
    
    public Contract(int start, int end) {
        s = start;
        e = end;
    }
}