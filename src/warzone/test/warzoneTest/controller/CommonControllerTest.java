package warzoneTest.controller;

import warzone.controller.CommonController;
import org.junit.Test;

public class CommonControllerTest {
	@Test
	public void welcomeTest() {
		(new CommonController()).welcome("test");
	}
	@Test
	public void standbyTest() {
		(new CommonController()).standby();
	}
}