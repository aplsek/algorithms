
public class Chars {
    
    public static void main(String[] args) { 
        
        String str = "Ah! ',oj";
        
        for (int i=0; i < str.length(); i++) {
            Character chr = new Character(str.charAt(i));
            if (Character.isLetter(chr))
                System.out.println("is letter");
            else
                System.out.println("is NOT letter");
        }
        
        String[] str2 = new String[]{"01000","10000"};
        int[] p1 = new int[]{1,1,0,0,0};
    }
}
