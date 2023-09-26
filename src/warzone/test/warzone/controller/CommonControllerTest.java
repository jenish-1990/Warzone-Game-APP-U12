package warzone.controller;

import org.junit.Test;

import warzone.controller.CommonController;

public class CommonControllerTest {
	@Test
	public void welcomeTest() {
		(new CommonController()).welcome("test");
	}
}