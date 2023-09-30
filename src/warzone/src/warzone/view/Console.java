package warzone.view;

/*
 * for specific ui, should create dedicated view class.
 * */
public class GenericView {
	
	public static void println(String p_text) {
		System.out.println(p_text);
	}
	
	public static void println(IRender p_content) {
		p_content.Render();
	}
}
