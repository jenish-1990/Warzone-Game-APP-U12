package warzone.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test all test class of package warzone.service
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GameEngineServiceTest.class,
	MapServiceTest.class,
	RouterServiceTest.class,
	StartupServiceTest.class,
	ConquestMapHandlerTest.class
	})
public class ServiceTestSuite {

}