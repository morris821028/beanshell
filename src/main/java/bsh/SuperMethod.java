package bsh;

public class SuperMethod {
	public static class Parent {
		public boolean sayHi() {
			System.err.println("HiHi");
			return true;
		}
	}

	public static class Child extends Parent {
		public Child() {
		}

		public boolean sayNo() {
			return true;
		}
	}

	public static Child getChild() {
		return new Child();
	}

	public static void test() throws EvalError {
		final Interpreter interpreter = new Interpreter();
		interpreter.eval(String.format("import %s;", SuperMethod.class.getName()));

		Object result = interpreter.eval(String.format("cc = SuperMethod.getChild();"));
		result = interpreter.eval(String.format("cc.sayHi();"));
		assert (Boolean) result == true;
	}
	
	public static void test2() throws EvalError {
		final Interpreter interpreter = new Interpreter();
		interpreter.eval(String.format("import %s;", SuperMethod.class.getName()));
		Child cc = new SuperMethod.Child();
		Object result = interpreter.eval(String.format("cc = new SuperMethod.Child();"));
		System.err.println(result);
		result = interpreter.eval(String.format("cc.sayHi();"));
		assert (Boolean) result == cc.sayHi();
	}

	public static void main(String[] args) {
		try {
			test2();
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
