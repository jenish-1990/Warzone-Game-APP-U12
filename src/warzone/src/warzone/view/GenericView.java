package warzone.view;

import warzone.model.Render;

/*
 * for specific ui, should create dedicated view class.
 * */
public class GenericView {
	
	public static void println(String p_text) {
		System.out.println(p_text);
	}
	
	public static void println(Render p_content) {
		p_content.render();
	}
}
