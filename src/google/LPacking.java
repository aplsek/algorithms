package google;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

/**
 * Program: L-Packing Algorithm
 * @author Ales Plsek 
 * @email : aplsek@gmail.com 
 * @www:  http://www.cs.purdue.edu/homes/plsek/ 
 * @date: 02/04/2011
 * 
 * 
 * Problem Formulation: 
 * ======================
 *  Packing of L-shaped items into a square of size 2^n x 2^n which contains one randomly
 *  filled tail of size 1x1:
 * 
 * Example:
 * ===================
 *         1 2 3 4 
 *       1 0 0 0 0 
 *       2 0 0 0 0  
 *       3 0 0 0 x    <--tail is here, position [4,3] 
 *       4 0 0 0 0 
 *         1 2 3 4 
 *    Symbols in the board:
 *           x .... the position of the tail    
 *           0 .... a free position
 *           L .... the tail is covered by 1/3 of the L shape
 *           
 *   The board can be filled with a following shape:      
 *           L     
 *           LL   
 *        (can be rotated).   
 *  
 * Input  
 * ================
 *    - input takes 1 argument (arg1), which is an integer number used to compute the size of the board
 *       board_size = 2^(arg1).
 *    - the position of the tail is randomly generated.    
 *       
 * Output:
 * ================
 *     - the program will print a sequence of of board configuration, each configuration contains one 
 *     new L shape inserted by the algorithm. 
 *     For example:
 *           STEP : 0 (initial state)
 *               --------------------
 *                 1 2 3 4 
 *               1 0 0 0 0 
 *               2 0 0 0 0 
 *               3 0 0 0 x 
 *               4 0 0 0 0 
 *                 1 2 3 4 
 *        
 *           STEP : 1
 *           --------------------
 *                1 2 3 4 
 *              1 0 0 0 0 
 *              2 0 0 0 0 
 *              3 0 0 L x 
 *              4 0 0 L L 
 *                1 2 3 4 
 *           --> this situation corresponds to insertion of ONE L-shape into the board.
 *           
 *           STEP : 2
 *           --------------------
 *                1 2 3 4 
 *              1 0 0 0 0 
 *              2 0 L L 0 
 *              3 0 L L x 
 *              4 0 0 L L 
 *                1 2 3 4 
 *           --> this situation corresponds to insertion of TWO L-shape into the board.
 *    
 *           STEPS: ...
 *        
 *           FINAL RESULT: 
 *             1 2 3 4 
 *            1 L L L L 
 *            2 L L L L 
 *            3 L L L x 
 *            4 L L L L 
 *              1 2 3 4 
 *           --> the board contains the tail "x" and the rest is fully covered by L-shapes.
 *  
 *           
 * Algorithm Description:          
 * ===================================          
 *           
 *  Strategy when filling the board (overview):
 *     The board can be filled with L-shapes in linear time by inserting the L-shapes along the
 *     diagonals of the board. 
 *     
 *     The algorithm starts with the tail "x", fills the first L-shape so that fits with the x-tail 
 *     and lays on the nearest diagonal. 
 *     
 *     Example:
 *     
 *     In the first step we start with the x-tail and choose a correct diagonal
 *     
 *           STEP : 1
        --------------------
           1 2 3 4 5 6 7 8 
         1 0 0 0 0 0 0 0 0 
         2 0 0 0 0 0 0 0 0 
         3 L L 0 0 0 0 0 0 
         4 x L 0 0 0 0 0 0 
         5 0 0 0 0 0 0 0 0 
         6 0 0 0 0 0 0 0 0 
         7 0 0 0 0 0 0 0 0 
         
        In the next steps we continue along the chosen diagonal.  
         
        STEP : 3
        --------------------
            1 2 3 4 5 6 7 8 
          1 0 0 L L 0 0 0 0 
          2 0 L L L 0 0 0 0 
          3 L L L 0 0 0 0 0 
          4 x L 0 0 0 0 0 0 
          5 0 0 0 0 0 0 0 0 
          6 0 0 0 0 0 0 0 0 
          7 0 0 0 0 0 0 0 0 
          8 0 0 0 0 0 0 0 0 
          1 2 3 4 5 6 7 8 
          
        When filling the L-shapes along this diagonal, whenever the chosen diagonal crosses 
        an orthogonal one, this diagonal is stored into a stack and after filling the current 
        diagonal, we recursively pop each diagonal from the stack and fill them:
        
        The following situation corresponds to filling of the second diagonal, the 
        next step will insert the L-shape on position [1,1].
        
        STEP : 8
        --------------------
          1 2 3 4 5 6 7 8 
        1 0 0 L L 0 0 0 0 
        2 0 L L L 0 0 0 0 
        3 L L L L 0 0 0 0 
        4 x L L L L 0 0 0 
        5 0 0 0 L L L 0 0 
        6 0 0 0 0 L L L 0 
        7 0 0 0 0 0 L L L 
        8 0 0 0 0 0 0 L L 
          1 2 3 4 5 6 7 8  
        
        In the next steps, we finish the filling of the second diagonal and then we continue
        with all the diagonals crossing the current diagonal.
        
        The algorithm continues till their are some diagonals on the stack == if there are no 
        diagonals on the stack, it means that we have processed all the diagonal of the board.
        
    The process of choosing the correct diagonal 
    ==================================================  
    
    Not all the diagonals of the board need to be processed by the algorithm. Therefore,
    before the algorithm starts, a set of relevant diagonals is generated. This is implemented
    as a recursive algorithm that represents all the diagonals as a graph - nodes represent
    the squares of the board, edges represents the diagonals. It is also important to handle 
    the nodes where 2 diagonals are crossing (this is used later in the algorithm to determine
    that a diagonal was reached and need to be pushed on the stack to be processed later).
    
    Process of generating the diagonals:
    1. two main diagonals of the board are generated - one going from South-West(SW) to Norht-East(NE),
    the other going from North-West(NW) -> South-East(SE). The length of these diagonals is equal
    to the size of the board. 
    2. We divide the board into 4 quadrants and apply the step 1. The length of the diagonal
    is (size_of_the_board)/2. 
    3. In each step the size of the board is divided by 2. The shortes diagonals that we consider are 
    of length 4. 
    
    The following example shows all the generated diagonals for a board of size 8.
    
    Diagonals (D)
    --------------------
      1 2 3 4 5 6 7 8 
    1 D 0 0 D D 0 0 D 
    2 0 D D 0 0 D D 0 
    3 0 D D 0 0 D D 0 
    4 D 0 0 D D 0 0 D 
    5 D 0 0 D D 0 0 D 
    6 0 D D 0 0 D D 0 
    7 0 D D 0 0 D D 0 
    8 D 0 0 D D 0 0 D 
      1 2 3 4 5 6 7 8 

    
    On the Formal Account of the Algorithm
    ================================================
        
    The important step of the algorithm is to determine correctly the first diagonal to start with.
    This is done based on the position of the "x" tail.     
    Following situations are possible: 
     - x is on a diagonal ==> the diagonal is chosen to start with, the first L is laid so that it 
                               covers x and lays on the diagonal
     - x is not on a diagonal
           - given by the nature of generated diagonals, it is easy to observe that
             each square has exactly one diagonal as its neighbor - either on North, South, East or West. 
             This diagonal is then chosen as a starting point of the algorithm.
           - Prove: "each square has exactly one diagonal as its neighbor". Can be easily proved 
           by induction, it hold for a board of size 4. Each board of size "i+1" is composed of 4 quadrants
           corresponding to a board of size "i".
           
    Progress:
       - in each step of the algorithm, a diagonal is filled. During the filling, all the diagonals that
       are crossing the current diagonal are stored to be processed in the next steps.
       Since the diagonals form an acyclic graph that has exactly one component, we are sure that
       all the diagonals will be processed.
    
    Termination:
       - by processing each diagonal, we are guaranteed to cover the whole board with L-shapes, since
       each point is either on the diagonal or a neighbor to one diagonal.
       
       
    Space and Time complexity of the algorithm:
        - the algorithm has 2 parts
           1) graph construction (diagonal construction)
              - number of Diagonals 
                    Di = 4x(Di-1) -2 
              
                    where Di ...... number of diagonals in a board related to number of diagonals
                                    in a board smaller by one order
                   
               n  |    Board Size  |  Number of Diagonals (d) 
               -------------------------------------
               2    |        4      |        2
               3    |        8      |        6
               4    |       16      |       22
               5    |       32      |       86
           
               Based on the number of diagonals:
               - Time Complexity - O(d)
               - Space Complexity -O(d)  - we use a HashMap to store the nodes,
                                           the HashMap is used since the part 2 of
                                           the algorithm needs a constant-time access guarantees
                                           to retrieve the nearest diagonal.
           
           2)   Filling of L-shapes
                - the filling algorithm is following the diagonals.
               
                Time Complexity - O(d)
                Space Complexity -O(d)  - no additional space is required 
       
 *           
 *    Notes
 *    ============
 *          - for board sizes 128 and bigger, the printing of each step is disables 
 *            to increase the speed of the algorithm.
 *          - the program does not have any test cases, in a typical real-life application
 *            I would use junit testing instead.  
 *           
 * The Algorithm : STEP 1
 * 
 * If the tail is not on the diagonal, we introduce this first step
 * where we manually add the first L shape into the Matrix. The L
 * shape is inserted so that it forms a square with the tail and
 * that its 2/3 are covering the nearest diagonal.
 * 
 * The Algorithm : STEP 2 In the first step we have determined which
 * which diagonal we start. In this step, we fill the whole diagonal
 * with L. When filling the diagonal, we have intersected another
 * diagonals, they are stored in the graph's stack. We take the
 * diagonals from the Stack and process them (by filling the L shapes).
 * During the filling, a new diagonals can be discovered, they are being
 * inserted into the stack.
 * 
 * Initially, the stack is empty, so we insert the Node, that we obtained
 * in STEP 1.
 *           
 */
public class LPacking {

    Graph graph = new Graph();

    public static boolean DISABLE_PRINTING = false;
    
    /**
     * 
     * @param args - takes the first parameter arg1 to determine the size of the board
     *   board = 2^arg1; 
     */
    public static void main(String[] args) {
        int board_size = 32;
        if (args.length > 0)
            board_size = 2 ^ new Integer(args[0]);   
        
        if (board_size < 1 ) {
            System.err.println("ERROR: Wrong input : The board size must be power of 2 and bigger then 0.");
            System.out.println("The END.");
            return;
        }
        
        if (board_size > 128) 
            DISABLE_PRINTING = true;
        
        /**
         * Caching the trivial cases
         */
        if (board_size == 1) {
            System.out.println("Nothing to do. The resulting board is:");
            System.out.println("|x|");
            System.out.println("The END.");
            return;
        } else if (board_size == 2) {
            System.out.println("The board trivially small, " +
            		"only the following combinations possible:");
            System.out.println("|x|L| ,  |L|x| ,  |L|L| ,  |L|L| ");
            System.out.println("|L|L|    |L|L|    |x|L|    |L|x| ");
            System.out.println("The END.");
            return;
        }
            
        /**
         * Algorithm can start.
         */
        LPacking lp = new LPacking();
        lp.constructGraph(board_size, 0, 0);
        lp.startAlgorithm(board_size);
    }

    /**
     *  Compute all diagonals of the board and store them as a graph.
     *  
     *  Diagonal are differentiated according to the Cardinal Directions:
     *                 N
     *              NW   NE 
     *            W         E
     *              SW   SE  
     *                 S
     */
    private void constructGraph(int size, int off_x, int off_y) {

        if (size == 2)
            return;
     
        /**
         * Computing the two longest diagonals
         */
        add_diagonal_East(size, off_x, off_y);
        add_diagonal_West(size, off_x, off_y);

        /**
         * Cutting the board into 4 smaller quadrants, we 
         * generate diagonals in each of them.
         */
        
        size = size / 2;
        int n_off_x = off_x;
        int n_off_y = off_y;
        constructGraph(size, n_off_x, n_off_y);

        n_off_x = off_x;
        n_off_y = off_y + size;
        constructGraph(size, n_off_x, n_off_y);

        n_off_x = off_x + size;
        n_off_y = off_y;
        constructGraph(size, n_off_x, n_off_y);

        n_off_x = off_x + size;
        n_off_y = off_y + size;
        constructGraph(size, n_off_x, n_off_y);

    }

    private void add_diagonal_West(int size, int off_x, int off_y) {

        if (graph.isOnDiagonal(off_x + 1, off_y + size))
            return; // diagonal is already there from the previous run

        /**
         * Look for the center of the diagonal that is already there:
         */
        Node center = graph.get(off_x + size / 2, off_y + size / 2);
        if (center == null || !center.cross)
            center = graph.get(off_x + size / 2 + 1, off_y + size / 2 + 1);

        /**
         * create my diagonal and tide it to the center.
         */
        Node prev = null;
        for (int i = 1; i <= size; i++) {
            Node node = graph.add(off_x + i, off_y + size + 1 - i);
            node.orientation = Orientation.SE2NW;
            if (prev != null) {
                node.nw = prev;
                prev.se = node;

                if (i == size / 2) {
                    if (center != null) {
                        center.nw = node;
                        center.cross = true;

                        node.ne = center;
                        node.se = center.se;
                        node.cross = true;
                    } else {
                        node.cross = true;
                    }
                }
            }
            prev = node;
        }
    }

    private void add_diagonal_East(int size, int off_x, int off_y) {

        if (graph.isOnDiagonal(off_x + size / 2, off_y + size / 2))
            return; // diagonal is already there from the previous run

        /**
         * Look for the center of the diagonal that is already there:
         */
        Node center = graph.get(off_x + size / 2 + 1, off_y + size / 2);
        if (center == null || !center.cross)
            center = graph.get(off_x + size / 2, off_y + size / 2 + 1);

        /**
         * create my diagonal and tide it to the center.
         */
        Node prev = null;
        for (int i = 1; i <= size; i++) {
            Node node = graph.add(off_x + i, off_y + i);
            node.orientation = Orientation.SW2NE;
            if (prev  != null ) {
                node.sw = prev;
                prev.ne = node;
                if (i == size / 2) { // if we are in the middle of the diagonal,
                                     // we need to add connection to the center
                                     // of the orthogonal diagonal.
                    if (center != null) {
                        center.sw = node;
                        center.cross = true;

                        node.nw = center;
                        node.se = center.se;
                        node.cross = true;
                    } else {
                        node.cross = true;
                    }
                }
            }
            prev = node;
        }
    }

    /**
     * ALGORITHM STARTs HERE:
     * 
     * @param order
     */
    public void startAlgorithm(int order) {
        graph.initTraversing(order);
        Node node = graph.getTail();
        fillDiagonals(node);
    }

    public void fillDiagonals(Node node) {
        //graph.printMatrix();

        if (node == null)
            return;
        if (!graph.isOnDiagonal(node.x, node.y)) {
            /**
             * The Algorithm : STEP 1
             * 
             * If the tail is not on the diagonal, we introduce this first step
             * where we manually add the first L shape into the Matrix. The L
             * shape is inserted so that it forms a square with the tail and
             * that its 2/3 are covering the nearest diagonal.
             */

            boolean b1 = graph.isOnDiagonal(node.x, node.y - 1);
            boolean b2 = graph.isOnDiagonal(node.x + 1, node.y);
            boolean b3 = graph.isOnDiagonal(node.x, node.y + 1);
            boolean b4 = graph.isOnDiagonal(node.x - 1, node.y);

            if (b1 && b2) {
                // d
                // 0 x d --> diagonal
                // 0
                graph.M[node.x][node.y - 1] = 'L';
                graph.M[node.x + 1][node.y] = 'L';
                graph.M[node.x + 1][node.y - 1] = 'L';

                node = graph.get(node.x, node.y - 1);

            } else if (b2 && b3) {
                // 0
                // 0 x d
                // d --> diagonal
                graph.M[node.x + 1][node.y + 1] = 'L';
                graph.M[node.x + 1][node.y] = 'L';
                graph.M[node.x][node.y + 1] = 'L';

                node = graph.get(node.x, node.y + 1);

            } else if (b3 && b4) {
                // 0
                // d x 0
                // diagonal <-- d
                graph.M[node.x - 1][node.y] = 'L';
                graph.M[node.x - 1][node.y + 1] = 'L';
                graph.M[node.x][node.y + 1] = 'L';

                node = graph.get(node.x, node.y + 1);

            } else if (b4 && b1) {
                // d
                // diagonal <---- d x 0
                // 0
                graph.M[node.x - 1][node.y - 1] = 'L';
                graph.M[node.x - 1][node.y] = 'L';
                graph.M[node.x][node.y - 1] = 'L';

                node = graph.get(node.x, node.y - 1);
            } else
                System.err.println("ERROR: No corresponding diagonal found!");
           // graph.printMatrix();
        }

        /**
         * The Algorithm : PART 2 In the first step we have determined which
         * which diagonal we start. In this step, we fill the whole diagonal
         * with L. When filling the diagonal, we have intersected another
         * diagonals, they are stored in the graph's stack. We take the
         * diagonals from the Stack and process them (by filling the L shapes).
         * During the filling, a new diagonals can be discovered, they are being
         * inserted into the stack.
         * 
         * Initially, the stack is empty, so we insert the Node, that we obtained
         * in STEP 1.
         */
        graph.stack.push(node);

        Node n = null;
        while (!graph.stack.isEmpty()) {
            n = graph.stack.pop();
            fill(n);
        }

        
        graph.printMatrix();
        /**
         * THE END.
         */
        System.out.println("The END.");
    }

    private void fill(Node node) {
        if (node == null)
            return;
        if (node.orientation == Orientation.SW2NE) { // node.ne != null &&
                                                     // node.sw != null
            node.cross = false;
            graph.fillNE(node);
            graph.fillSW(node);
        } else if (node.orientation == Orientation.SE2NW) { // node.nw != null
                                                            // && node.se !=
                                                            // null
            node.cross = false;
            graph.fillNW(node);
            graph.fillSE(node);
        }
    }

    /**
     * DEBUG
     */
    public void printDiagonals() {
        graph.printDiagonals();
    }
}


/**
 * 
 * 
 * 
 * 
 * @author plsek
 *
 */
class Graph {

    /**
     * A set of all relevant diagonals in the Matrix.
     */
    HashMap<String, Node> set = new HashMap<String, Node>();

    public Stack<Node> stack = null;

    char[][] M;
    int order = 0;

    public void initTraversing(int order) {
        stack = new Stack<Node>();
        this.order = order;
        M = new char[order + 1][order + 1];

        for (int i = 0; i < order + 1; i++)
            for (int j = 0; j < order + 1; j++)
                M[i][j] = '0';

        placeTail();
    }

    /**
     * Counting the steps of the algorithm.
     */
    int count = 0;

    /**
     * Place of the tail on the board
     */
    int tail_x;
    int tail_y;

    /**
     * Randomly generates the position of the tail. 
     */
    private void placeTail() {
        Random generator = new Random();
        tail_x = generator.nextInt(order) + 1;
        tail_y = generator.nextInt(order) + 1;
        M[tail_x][tail_y] = 'x';
    }

    public Node getTail() {
        Node n = set.get(tail_x + "," + tail_y);
        if (n == null)
            return new Node(tail_x, tail_y);
        else
            return n;
    }

    public Node add(int x, int y) {
        if (isOnDiagonal(x, y))
            System.err.println("\n\n ERROR: Node is already IN!!!!");
        Node n = new Node(x, y);
        set.put(x + "," + y, n);
        return n;
    }

    public Node get(int x, int y) {
        return (Node) set.get(x + "," + y);
    }

    public boolean isOnDiagonal(int x, int y) {
        if (set.containsKey(x + "," + y))
            return true;
        return false;
    }

    public void fillNE(Node n) {
        if (n == null) return; // should never happen, the recursion will already end when n.nw == null. 
        if (n.ne == null)
            return; // we are at the end of the Diagonal

        if (M[n.x + 1][n.y + 1] != 'L') {
            M[n.x + 1][n.y] = 'L';
            M[n.x + 1][n.y + 1] = 'L';
            M[n.x][n.y + 1] = 'L';
            printMatrix();
        }

        if (n.cross) {
            // we are crossing another diagonal, we need to traverse it.
            if (n.se != null && n.se.cross)
                stack.push(n.se);
            else if (n.nw != null && n.nw.cross)
                stack.push(n.nw);
        }
        n = n.ne; // continue;
        fillNE(n);
    }

    public void fillNW(Node n) {
        if (n == null) return; // should never happen, the recursion will already end when n.nw == null. 
        if (n.nw == null)
            return; // we are at the end of the diagonal

        if (M[n.x - 1][n.y + 1] != 'L') {
            M[n.x - 1][n.y] = 'L';
            M[n.x - 1][n.y + 1] = 'L';
            M[n.x][n.y + 1] = 'L';
            printMatrix();
        }

        if (n.cross) {
            // we are crossing another diagonal, we need to traverse it.
            if (n.ne != null && n.ne.cross)
                stack.push(n.ne);
            else if (n.sw != null && n.sw.cross)
                stack.push(n.sw);
        }
        n = n.nw; // continue;
        fillNW(n);
    }

    public void fillSE(Node n) {
        if (n == null) return; // should never happen, the recursion will already end when n.nw == null. 
        if (n.se == null)
            return; // we are at the end of the Diagonal
        if (M[n.x + 1][n.y - 1] != 'L') {
            M[n.x + 1][n.y] = 'L';
            M[n.x + 1][n.y - 1] = 'L';
            M[n.x][n.y - 1] = 'L';
            printMatrix();
        }

        if (n.cross) {
            // we are crossing another diagonal, we need to traverse it.
            if (n.ne != null && n.ne.cross)
                stack.push(n.ne);
            else if (n.sw != null && n.sw.cross)
                stack.push(n.sw);
        }
        n = n.se; 
        fillSE(n); // continue;
    }

    public void fillSW(Node n) {
        if (n == null)
            return; // should never happen
        if (n.sw == null)
            return; // we are at the end of the Diagonal
        if (M[n.x - 1][n.y - 1] != 'L') {
            M[n.x - 1][n.y] = 'L';
            M[n.x - 1][n.y - 1] = 'L';
            M[n.x][n.y - 1] = 'L';
            printMatrix();
        }

        if (n.cross) {
            // we are crossing another diagonal, we need to traverse it.
            if (n.nw != null && n.nw.cross)
                stack.push(n.nw);
            else if (n.se != null && n.se.cross)
                stack.push(n.se);
        }
        n = n.sw; // continue;
        fillSW(n);
    }

    /************************************************************
     * DEBUG / HELPER METHODS
     */

    HashSet<Node> diagonals = new HashSet<Node>(); // for debug only

    public void addDiagonal(Node n) {
        diagonals.add(n);
    }
    
    /**
     * DEBUG and Printing of the MATRIX after each step.
     */
    public void printMatrix() {
        if (LPacking.DISABLE_PRINTING)
            return;
        
        System.out.println("STEP : " + count++);
        System.out.println("--------------------");
        System.out.print("  ");
        for (int i = 1; i <= order; i++) {
            System.out.print(i + " ");
        }
        System.out.print("\n");

        for (int i = 1; i <= order; i++) {
            System.out.print(i + " ");
            for (int j = 1; j <= order; j++)
                System.out.print(M[j][i] + " ");
            System.out.print("\n");
        }

        System.out.print("  ");
        for (int i = 1; i <= order; i++) {
            System.out.print(i + " ");
        }
        System.out.print("\n\n\n");
    }

    /**
     * DEBUG
     */
    public void printDiagonals() {
        for (Node n : diagonals) {
            n.printDiagonals();
            System.out.print("\n");
        }
    }

    /**
     * DEBUG
     */
    public void checkGraph() {
        for (Node n : set.values()) {
            n.printNode();
            System.out.println("");
        }
    }
}


/**
 * 
 * 
 */
class Node {

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;

    Node nw = null;
    Node ne = null;
    Node sw = null;
    Node se = null;

    Orientation orientation;

    boolean cross = false;

    public boolean equals(Node m) {
        if (this.x == m.x && this.y == m.y)
            return true;
        else
            return false;
    }

    public void travers_NW(Node n) {
        if (n == null) return;
        travers_NW(n.nw);
    }

    public void travers_NE(Node n) {
        if (n == null) return;
        travers_NE(n.ne);
    }

    public void travers_SE(Node n) {
        if (n == null) return;
        travers_SE(n.se);
    }

    public void travers_SW(Node n) {
        if (n == null) return;
        travers_SW(n.sw);
    }

    /**
     *  DEBUG METHODS:
     */
    
    public String toString() {
        return "[" + x + "," + y + "], ";
    }
    
    public void printDiagonals() {
        this.printNode();

        if (nw != null)
            travers_NW(nw);

        if (ne != null) 
            travers_NE(ne);

        if (se != null)
            travers_SE(se);

        if (sw != null) 
            travers_SW(sw);
    }

    public void printNode() {
        System.out.println("Node:" + this);
        System.out.println("\t cross:" + cross);
        System.out.println("\t orientation :" + orientation);
        System.out.println("\t ne:" + ne);
        System.out.println("\t nw:" + nw);
        System.out.println("\t sw:" + sw);
        System.out.println("\t se:" + se);
    }
}

/**
 * This enum type stores the orientation of a diagonal.
 */
enum Orientation {
    SW2NE,    // from Sout-West to North-East
    SE2NW     // from Sout-East to North-West
}
