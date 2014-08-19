package bits;

public class BitOperations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(getBits(4));
		
		int displayMask = 1 << 31;
		System.out.println(displayMask);
		System.out.println(getBits(displayMask));
		
		int j = 4 & displayMask;
		System.out.println(j);
		System.out.println(getBits(j));
		System.out.println("-------");
		System.out.println(4 << 1);
		double l = 	0x0202020202L;
		System.out.println(	l);
		
	}

	public static void printByteToBits(int j) {
		byte number = 8;
		int i = 256; //max number * 2
		while( (i >>= 1) > 0) {
		System.out.print(((number & i) != 0 ? "1" : "0"));
		}
	}
	
	 private static  String getBits( int value )
     {
     int displayMask = 1 << 31;
     StringBuffer buf = new StringBuffer( 35 );
    
     for ( int c = 1; c <= 32; c++ ) 
         {
         buf.append( ( value & displayMask ) == 0 ? '0' : '1' );
         value <<= 1;
        
         if ( c % 8 == 0 )
         buf.append( ' ' );
     }
    
     	return buf.toString();
     }

	 private static String getMyBits(int value) {
		 return "";
	 }
	 
	 private void reverse() {
		 int v;     // input bits to be reversed
		 int r = v; // r will be reversed bits of v; first get LSB of v
		 int s = sizeof(v) * 8 - 1; // extra shift needed at end

		 for (v >>= 1; v != 0; v >>= 1)
		 {   
		   r <<= 1;
		   r |= v & 1;
		   s--;
		 }
		 r <<= s; // shift when v's highest bits are zero
	 }
	 
	 
	
}
