package erik.study.spring.aop.tech;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2019-09-15
 * @Created by erik
 */
public class MethodCounter implements Serializable {
    private static final long serialVersionUID = -1519065450937008403L;

    private Map<String, Integer> methodName2CountMap = new ConcurrentHashMap<>();

    private AtomicInteger allCount = new AtomicInteger(0);

    protected void count(Method method) {

    }

    protected void count(String methodName) {
        methodName2CountMap.compute(methodName, (key, oldValue) -> {
            if (oldValue == null) {
                return 1;
            } else {
                return oldValue++;
            }
        });
        allCount.incrementAndGet();
    }

    public Integer getCalls(String methodName) {
        return methodName2CountMap.getOrDefault(methodName, 0);
    }
}
