import java.util.Set;
import java.util.*;

/**
 * GOOOD:  http://download.oracle.com/javase/1.4.2/docs/api/java/util/package-summary.html
 * 
 * LinkedList
 * 
 * List
 * 
 * BEST::: http://java.sun.com/developer/onlineTraining/collections/Collection.html
 * 
 * 
 * 
 * 
 * http://www.java-tips.org/java-se-tips/java.lang/the-enhanced-for-loop.html
 * 
 * for (int i : squares)
 * 
 * 
 * 
 * 
 * 
 * 
 * @author plsek
 *
 */



public class SetTests {

    public static void main(String[] args) { 
        //Set set = new Set();
        
        ArrayList<Data> list = new ArrayList<Data>();
    
        list.add(new Data(1));
        list.add(new Data(2));
        
        for(Data d : list) {
            
        }
        
        
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("A");
        list2.add("B");
        for(String i : list2) {
            
        }
        
        
        
        /**
         * 
         * PARSE A STRING
         * 
         */
        String test = "10 100 10 45";
        String test1 = "10 100 10 45";
        String[] tokens = test.split(" ");
        
        String test2 = "It's the number 1 way.";
        String[] tokens2 = test2.split(" ");       // Single blank is the separator.
        System.out.println(Arrays.toString(tokens2));
        //     [It's, the, number, 1, way.]

        
    }
    
}


class Data {
    int data;
    
    public Data(int d) {
        data = d;
    }
}