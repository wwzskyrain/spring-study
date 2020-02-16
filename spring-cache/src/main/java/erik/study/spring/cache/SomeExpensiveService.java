package erik.study.spring.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.TimeUnit;

/**
 * @author erik.wang
 * @Date 2020-01-09
 */

public class SomeExpensiveService {

    private static final Logger logger = LoggerFactory.getLogger(SomeExpensiveService.class);

    @Cacheable(cacheNames = "expensiveCalculate1")
    public String calculate(Long input) {
        logger.info("method calculate was called with input:{}", input);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result" + input;
    }



}
