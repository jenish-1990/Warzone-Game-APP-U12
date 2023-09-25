package warzone.controller;

import warzone.view.GenericView;

public class ErrorController {
	public void error(String p_error) {
		GenericView.println(p_error);
	}
}
