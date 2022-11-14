import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {

    @Test
    public void testLog4j() {
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.debug("it is debug logger");
        logger.info("it is info logger");
        // test
    }
}
