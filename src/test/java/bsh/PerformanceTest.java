package bsh;

import org.junit.Test;

public class PerformanceTest {

	private static void testStack(int dep, Interpreter interpreter) throws EvalError {
		if (dep <= 0) {
			String s;
			for (int i = 0; i < 10000; i++) {
				s = (String) interpreter.eval("\"abc\"+\"def\"");
			}
		} else {
			testStack(dep - 1, interpreter);
		}
	}

	private static void testBasicStack(int dep, Interpreter interpreter) throws EvalError {
		if (dep <= 0) {
			String s;
			for (int i = 0; i < 10000000; i++) {
				s = "abc" + "def";
			}
		} else {
			testBasicStack(dep - 1, interpreter);
		}
	}

	@Test
	public void test() {
		final Interpreter interpreter = new Interpreter();

		Runtime runtime = Runtime.getRuntime();

		long usedMemoryBefore = 0;
		long usedMemoryAfter = 0;
		long start;
		long end;
		
		start = System.currentTimeMillis();
		try {
			testBasicStack(1000, interpreter);
		} catch (EvalError e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();
		final long goldTime = end - start;
				
		start = System.currentTimeMillis();
		usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		try {
			testStack(1000, interpreter);
		} catch (EvalError e) {
			e.printStackTrace();
		}
		usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		
		end = System.currentTimeMillis();
		final long testTime = end - start;
		final long testMemory = usedMemoryAfter - usedMemoryBefore;
		
		System.out.println("Memory increased: " + (testMemory) / 1024 + " KB");
		System.out.println("Usage time: " + (testTime) / 1000 + " Seconds");
		
		System.out.printf("Test %d Golden %d Test/Golden %f\n", testTime, goldTime, ((double) testTime) / goldTime);
	}
}
