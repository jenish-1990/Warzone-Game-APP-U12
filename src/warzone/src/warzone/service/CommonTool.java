package warzone.service;

import warzone.view.GenericView;

/**
 * This class is responsible to provide some basic tools to other classes.
 * @author ayush, alay
 *
 */
public class CommonTool {
	/**
	 * This method can convert p_number into integer format.
	 * @param p_number the number that should be converted
	 * @return the value of the number
	 */
	public static int parseInt(String p_number) {
		int l_result = -1;
		if(p_number != null) {
			try {
				 l_result = Integer.parseInt(p_number.trim());
			}
			catch(Exception ex) {
				GenericView.printError("cannot convert " + p_number + " into integer.");
			}
		}
		return l_result;		
	}
	
	/**
	 * This method can convert parameters parsed by command parser into String array.
	 * @param p_parameters p_parameters that should be converted
	 * @return the String array of parameters
	 */
	public static String[] conventToArray(String p_parameters) {
		if(p_parameters != null)
			return p_parameters.split("\\s+");
		else
			return new String[] {};
	}	
	
	/**
	 * This method can generate random numbers from a range.
	 * @param p_minNumber the lower bound of the random number
	 * @param p_maxNumber the upper bound of the random number
	 * @return the generated number
	 */
	public static int getRandomNumber(int p_minNumber, int p_maxNumber) {
	    return (int) ((Math.random() * (p_maxNumber - p_minNumber)) + p_minNumber);
	}
	
	/**
	 * This method can generate String from a String array from a range and inserting separator into it.
	 * @param p_stringArray the array that should be converted
	 * @param p_separator separator of the String
	 * @param from the start of the array
	 * @param to the end of the array
	 * @return the String generated from the array
	 */
	public static String convertArray2String(String[] p_stringArray, String p_separator, int from, int to) {
		StringBuilder l_sb = new StringBuilder();
		for (int l_temp = from; l_temp < to + 1; l_temp++) {
			l_sb.append(p_stringArray[l_temp]);
			if (l_temp < to && l_temp < p_stringArray.length - 1) {
				l_sb.append(p_separator);
			}
		}
		return l_sb.toString();
	}
}
