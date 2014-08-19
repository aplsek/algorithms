package converto;

import junit.framework.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { FibonacciJUnitTest.class })
public class FibonacciJUnitTest extends TestCase {

	Fibonacci f;
	
	public FibonacciJUnitTest(String name) {
		super(name);
	}
	
	public void setUp() {
		f = new Fibonacci();
	}
	
	public static Test suite() {
		return new FibonacciJUnitTest(Fibonacci.class);
	}
	
	@Test(timeout=100)
	public void test1() {
		assert(f.fibonacci(1) == 1);
	}
	
	@Test(timeout=100)
	public void test2() {
		assert(f.fibonacci(2) == 1);
	}
	
	@Test(timeout=100)
	public void test3() {
		assert(f.fibonacci(3) == 2);
	}
	
	@Test
	public void test4() {
		assert(f.fibonacci(10) == (f.fibonacci(9) + f.fibonacci(8)));
	}
	
	@Test(expected=IllegalArgumentException.class)	
	// we could be throwing this exception in case of an incorrect output.
	public void test5() {
		assert(f.fibonacci(-1) == 1);
	}
	
	@Test
	public void test6() {
		for (int i = 10 ; i < 20; i++)
		  assert(f.fibonacci(10) == (f.fibonacci(i-1) + f.fibonacci(i-2)));
	}
	
	@Test
	public void test7() {
		  assert(f.fibonacci(Integer.MAX_VALUE) == (f.fibonacci(Integer.MAX_VALUE-1) + f.fibonacci(Integer.MAX_VALUE-2)));
	}
	
	// assert sanity:
	@Test
	public void test8() {
		assert(f.fibonacci(100) == f.fibonacci(100));
	}
	
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());
	}
	
}

