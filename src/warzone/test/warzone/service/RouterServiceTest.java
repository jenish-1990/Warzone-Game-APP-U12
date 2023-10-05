package warzone.service;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import warzone.model.ControllerName;
import warzone.model.ErrorType;
import warzone.model.GameContext;
import warzone.model.Router;

public class RouterServiceTest {
	private String d_command;
	private RouterService d_routerService = RouterService.getRouterService( GameContext.getGameContext() );
	private List<Router> d_routerList = new LinkedList<Router>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

}
