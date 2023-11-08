package warzone.model;

/**
 * Color used in map
 */
public class Color {
	public static final Color RED=new Color("red");
	public static final Color MAGENTA=new Color("magenta");
	public static final Color GREEN=new Color("green");
	public static final Color BLUE=new Color("blue");
	public static final Color YELLOW=new Color("yellow");
	public static final Color PINK=new Color("pink");
	public static final Color PURPLE=new Color("purple");
	public static final Color ORANGE=new Color("orange");
	public static final Color TURQUOISE=new Color("turquoise");
	public static final Color BROWN=new Color("brown");
	public static final Color GREY=new Color("grey");
	public static final Color WHITE=new Color("white");
	public static final Color BLACK=new Color("black");
	
	private String d_value;
	
	public Color(String p_value) {
		this.d_value=p_value;
	}
	
	public static Color valueOf(String p_value) {
		return new Color(p_value);
	}
	
	@Override
	public String toString() {
		return this.d_value;
	}
}