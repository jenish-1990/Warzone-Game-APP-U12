package warzone.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test all test class of package warzone.service
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//	CommandServiceTest.class,
//	CommonToolTest.class,
//	ContinentServiceTest.class,
//	ControllerFactoryTest.class,
//	CountryServiceTest.class,
//	GameEngineTest.class,
	MapServiceTest.class,
//	NeighborServiceTest.class,
	RouterServiceTest.class,
	StartupServiceTest.class
	})
public class TestSuite {

}