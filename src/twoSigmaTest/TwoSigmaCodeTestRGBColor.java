package twoSigmaTest;


/**
 * Author : Ales Plsek, http://www.cs.purdue.edu/homes/plsek/
 * 
 *
 */
public class TwoSigmaCodeTestRGBColor {
    
    
    /**
     * ALGORITHM STARTS HERE 

     *
     */
    public static int[] getComplement(int[] rgb) {
        
        int[] result;
        if (!checkCorrectInput(rgb)) {
            return null;
        }
        result = complement(rgb);
        
        if (checkForGrey(rgb,result)) {
            result = resolveGrey(rgb);
        }
        
        return result;
    }
    
    
    /**
     * verify input for correctness
     */
    public static boolean checkCorrectInput(int[] rgb) {
      for (int i=0; i < rgb.length ; i++) {
            if (rgb[i] < 0 || rgb[i] > 255)
                return false;
                
      }
      return true;
    }
    
    
    
    public static int[] complement(int[] rgb) {
        
        int[] res = new int[3];
        
        for (int i=0; i < rgb.length ; i++) {
              res[i] = 255 - rgb[i];
        }
        
        return res;
    }
    
    /**
     * If each component of a color and its corresponding component 
     * of the color's complement differ by 32 or less, we return true
     */
    public static boolean checkForGrey(int[] rgb, int[] cont) {
        boolean grey = true;
        for (int i=0; i < rgb.length ; i++) {
            int x = rgb[i] - cont[i];
            if (x < 0) x = x * (-1);
            
            if (x > 32) {
                grey = false;
                break;
            }
        }
        return grey;
    }
    
    /**
     * resolving the grey colors 
     */
    private static int[] resolveGrey(int[] rgb) {
        int [] res = new int[3];
        for (int i=0; i < rgb.length ; i++) {
           if (rgb[i] < 128)
               res[i] =  rgb[i] + 128;
           else
               res[i] = rgb[i] - 128;
        }
        
        return res;
    }
    
    
    
    /**
     * START
     */
    public static void main(String[] args) { 
       int [] test1 = new int[]{255,0,0};
       
       int[] res;
       res = getComplement(test1);
       
       System.out.println("result of 255, 0, 0 ");
       System.out.print("(");
       for (int i=0; i < res.length ; i++) {
           System.out.print(res[i] +"," );
       }
       System.out.println(")\n--------------");
       
       
       
       
       int [] test2 = new int[]{115,115,143};
       res = getComplement(test2);
       System.out.println("result of 115,115,143 ");
       System.out.print("(");
       for (int i=0; i < res.length ; i++) {
           System.out.print(res[i] +"," );
       }
       System.out.println(")\n--------------");
       
       
       int [] test3 = new int[]{153,12,55};
       res = getComplement(test3);
       System.out.println("result of 153,12,55 ");
       System.out.print("(");
       for (int i=0; i < res.length ; i++) {
           System.out.print(res[i] +"," );
       }
       System.out.println(")\n--------------");
       
       
       int [] test4 = new int[]{128,128,128};
       res = getComplement(test4);
       System.out.println("result of 128,128,128");
       System.out.print("(");
       for (int i=0; i < res.length ; i++) {
           System.out.print(res[i] +"," );
       }
       System.out.println(")\n--------------");
       
       
       int [] test5 = new int[]{129,129,129};
       res = getComplement(test5);
       System.out.println("result of 129,127,129");
       System.out.print("(");
       for (int i=0; i < res.length ; i++) {
           System.out.print(res[i] +"," );
       }
       System.out.println(")\n--------------");
       
       int [] test6 = new int[]{129,129,129};
       res = getComplement(test6);
       System.out.println("result of 129,129,129");
       System.out.print("(");
       for (int i=0; i < res.length ; i++) {
           System.out.print(res[i] +"," );
       }
       System.out.println(")\n--------------");
       
    }
}
