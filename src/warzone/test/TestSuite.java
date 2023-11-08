import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Run all test cases
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	warzone.model.TestSuite.class,
	warzone.service.TestSuite.class,
//	warzone.state.TestSuite.class,
//	warzone.view.TestSuite.class
	})
public class TestSuite {

}
