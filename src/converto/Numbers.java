package converto;

public class Numbers {
		
	
	
	
	/**
	 * Write in your most comfortable language) code that prints the numbers from 1 to 1 million. But for multiples of prime number seven print "Multi" instead of the number and for the multiples of prime number thirteen print "Attribution". For numbers which are multiples of both three and five print "Multi-Attribution".
	 * @param args
	 */
	public static void main(String[] args) {
		String multi = "Multi";
		String atrib = "Attribution";
		String mAtrib = "Multi-Attribution";

		for (int i =  1 ; i <= 100 ; i++) {
		  if ( i % 7 == 0 ) 
		     System.out.println(multi);
		  else {
			  if ( i % 13 == 0) 
				  System.out.println(atrib);
			  else {
				  if ((i % 5 == 0) && (i % 3 == 0))
					  System.out.println(mAtrib);
				  else
					  System.out.println(i);
			  }
		  }
		}
	}
}
