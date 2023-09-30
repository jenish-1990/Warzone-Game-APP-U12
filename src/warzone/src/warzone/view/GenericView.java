package warzone.view;

import warzone.model.GameContext;
import warzone.model.Render;

/**
 * for specific ui, this class can create dedicated view class.
 *
 */
public class GenericView {
	
	/**
	 * This method can print normal messages from String
	 * @param p_text the text that should be printed
	 */
	public static void println(String p_text) {
		System.out.println(p_text);
	}
	
	/**
	 * This method can print warning messages
	 * @param p_text the text that should be printed
	 */
	public static void printWarning(String p_text) {
		System.out.println("Warning : " + p_text );
	}
	
	/**
	 * This method can print error messages
	 * @param p_text the text that should be printed
	 */
	public static void printError(String p_text) {
		System.out.println("Error : " + p_text);
	}
	
	/**
	 * This method can print success messages
	 * @param p_text the text that should be printed
	 */
	public static void printSuccess(String p_text) {
		System.out.println("Success : " + p_text);
	}

	/**
	 * This method can print normal messages from Render
	 * @param p_content the render instance
	 */
	public static void println(Render p_content) {
		p_content.render();
	}
	/**
	 * print out debug information
	 * @param p_text debug text
	 */
	public static void printDebug(String p_text) {
		if(GameContext.getGameContext().getIsDebug())
			System.out.println("Debug : " + p_text);
	}	
}
