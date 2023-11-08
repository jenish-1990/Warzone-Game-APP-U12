package warzone.view;

import warzone.model.Render;

/*
 * for specific ui, should create dedicated view class.
 * */
public class GenericView {
	
	public static void println(String p_text) {
		System.out.println(p_text);
	}
	
	public static void printWarning(String p_text) {
		System.out.println("Warning : " + p_text );
	}
	
	public static void printError(String p_text) {
		System.out.println("Error : " + p_text);
	}
	public static void printSuccess(String p_text) {
		System.out.println("Success : " + p_text);
	}

	public static void println(Render p_content) {
		p_content.render();
	}
	/**
	 * print out debug information
	 * @param p_text debug text
	 */
	public static void printDebug(String p_text) {
		System.out.println("Debug : " + p_text);
	}
}
