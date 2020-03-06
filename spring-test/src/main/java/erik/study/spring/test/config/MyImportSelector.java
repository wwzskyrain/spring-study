package erik.study.spring.test.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author erik.wang
 * @date 2020-02-16 10:12
 * @description
 */
public class MyImportSelector implements ImportSelector {

    private static final Logger logger = LoggerFactory.getLogger(MyImportSelector.class);

    /**
     * erik_comment 这里主要是看一下参数AnnotationMetadata的实质。其实这很正常，属于上下文的一部分。
     * 上下文包括：函数的调用处，以及调用时传入的参数。
     * @param importingClassMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        logger.info("importingClassMetadata={}", JSON.toJSONString(importingClassMetadata));
        String myPersonOneClassName = MyPersonOne.class.getCanonicalName();
        String myPersonTwoClassName = MyPersonTwo.class.getCanonicalName();
        String[] classNames = new String[]{myPersonOneClassName, myPersonTwoClassName};
        logger.info("classNames={}", classNames);
        return classNames;
    }
}
