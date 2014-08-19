package amazon;

import java.util.ArrayList;


/**
 * @author plsek, aplsek@purdue.edu
 */
public class Trees {

	/**
	 * Compares if the values of the elements
	 * in the trees t1 and t2 are the same.
	 */
	public boolean compare(Node t1, Node t2) {

		if (t1 == null && t2 == null)
			return true;          // NULL, trees are equal! 
		else 
			if (t1 == null || t2 == null)
				return false;	  // only one of the trees is NULL, trees are definitely not equal
		
		ArrayList<Integer> arr1 = t1.serialize();
		ArrayList<Integer> arr2 = t2.serialize();
		
		return compareArrays(arr1, arr2);
	}
	
	/**
	 * Compares if both the arrays contain the same elements.
	 */
	private boolean compareArrays(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
		
		// the arrays needs to have the same size, 
		// otherwise there is some element missing in one of them
		if (arr1.size() != arr2.size())
			return false;
		
		// We have the same number of elements in each array, arrays are sorted.
		// This means that if they are to be "equal", an element from arr1
		// needs to be contained also in arr2 AND at the same index
		for (int i=0 ; i < arr1.size(); i ++) {
			if (arr1.get(i).equals(arr2.get(i)))
				continue;
			else
				return false;
		}
		
		// all the elements of the array arr1 are also in the array arr2,
		// and both arrays have the same size so they must be equal!
		return true;   
	}
	
	public static void main(String[] args) {
		
		/**
		 * 
		 * TEST CASE 1
		 * 
		 * 
		 */
		System.out.println("TEST 1:");
		// creating Tree 1
		Node tree1 = new Node(2);
		tree1.insert(1);
		tree1.insert(3);
		tree1.insert(4);
		
		// creating Tree 2
		Node tree2 = new Node(3);
		tree2.insert(1);
		tree2.insert(2);
		tree2.insert(4);
		
		Trees trees = new Trees();
		
		boolean res = trees.compare(tree1,tree2);
		if (res) 
			System.out.println("Trees are the same!");
		else
			System.out.println("Trees are NOT the same!");
		
		/**
		 * 
		 * TEST CASE 2
		 * 
		 * 
		 */
		System.out.println("\nTEST 2:");
		// creating Tree 1
		Node tree3 = new Node(2);
		tree3.insert(1);
		tree3.insert(3);
		tree3.insert(4);
		
		// creating Tree 2
		Node tree4 = new Node(3);
		tree4.insert(1);
		tree4.insert(2);
		tree4.insert(5);
		
		Trees trees2 = new Trees();
		
		if (trees2.compare(tree3,tree4)) 
			System.out.println("Trees are the same!");
		else
			System.out.println("Trees are NOT the same!");
		
		
		/**
		 * 
		 * TEST CASE 3
		 * 
		 * 
		 */
		System.out.println("\nTEST 3:");
		if (trees2.compare(null,null)) 
			System.out.println("Trees are the same!");
		else
			System.out.println("Trees are NOT the same!");
		

		/**
		 * 
		 * TEST CASE 4
		 * 
		 * 
		 */
		System.out.println("\nTEST 4:");
		
		if (trees2.compare(null,tree4)) 
			System.out.println("Trees are the same!");
		else
			System.out.println("Trees are NOT the same!");
		
	}

}


class Node {
	int id;
	Node left;
	Node right;
	
	/**
	 * Constructor.
	 */
	public Node (int id) {
		this.id = id;
		left = null;
		right = null;
	}
	
	/**
	 * Recursive construction of a Tree.
	 */
	public void insert(int i) {
		if (id == i)
			System.err.println("ERROR: The element is already contained in the tree!");
		
		if (i < id ) {
			if (left == null)
				left = new Node(i);
			else left.insert(i);
		}
		else {
			if (right == null)
				right = new Node(i);
			else
				right.insert(i);
		}
	}
	
	/**
	 * Returns a list of nodes sorted from the smallest one to the biggest one.
	 * @return
	 */
	public ArrayList<Integer> serialize() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		serialize(this,arr);
		return arr;
	}
	
	/**
	 *  The recursive method implementing the serialization.
	 */
	private void serialize(Node t, ArrayList<Integer> arr) {
		if (t.left != null)
			serialize(t.left, arr);			// serialize the left side
		arr.add(t.id);						// serialize the node
		if (t.right != null)
			serialize(t.right,arr);			// serialize the right side
	}
}