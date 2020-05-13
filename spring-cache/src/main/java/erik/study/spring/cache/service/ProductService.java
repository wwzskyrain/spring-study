package erik.study.spring.cache.service;

import com.alibaba.fastjson.JSON;
import erik.study.spring.cache.Product;
import erik.spring.util.aop.annotation.LogExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author erik.wang
 * @Date 2020/1/11
 */
@Service
public class ProductService implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private static final String PRODUCT_NAME_PATTERN = "product_name_%d";
    public static Map<Long, Product> productMap = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            Product product = supply(i * 1L);
            product.setPrice(new BigDecimal(i));
            productMap.put(product.getId(), product);
            logger.info("init product data over: {}", JSON.toJSONString(product));
        }
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static Product supply(Long productId) {
        return new Product(productId, getProductName(productId), new BigDecimal(productId));
    }

    public static String getProductName(Long productId) {
        return String.format(PRODUCT_NAME_PATTERN, productId);
    }


    @Cacheable(cacheNames = "productWithId", condition = "#productId < 3")
    @LogExecutionTime
    public Product findProductById(Long productId) {
        logger.info("method-findProductById been called.");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productMap.get(productId);
    }


    /**
     * 主要用来测试'自调用时注解无效'的情况
     * 1.P是C的代理类，则P.m1和P.m2则都会触发m1和m2的aop注解
     * 2.C.m1和C.m2都不会触发m1和m2
     * 3.总结1和2，代理对象调用方法会触发代理逻辑，被代理对象(源对象)调用方法不会触发代理逻辑
     * 4.点题：在m1中调用m2，或者反过来，都是不会触发后者的aop注解的，why？
     * 很简单，因为我们在写m1和m2时，使用this.m2/this.m1的，这样就是
     * 在C上调用，即作用到被代理对象自身上，而不是作用到代理对象。
     * 为什么是这样呢？其实很简单，因为我们不可能在代理层次编写代码，因为我们编写代码的时候
     * 是不知道以后会添加什么代理逻辑的，程序执行机制，也必定要保证这种安全性的。
     * <p>
     * 5.那么如何在自调用的时候，保留aop注解生效呢？
     * 1.   当然就找到代理对象了，然后从代理对象开始调用就可以了呀。
     * 2.   所以关键是如何找到代理对象，谁是代理对象？
     * 3.   我们从spring自动注入的对象都是代理对象的
     * 4.   所以我们可以保留springContext的引用，从其中取出代理对象。
     * 5.   第二种方法，那就是在设计类的时候，就保留一个代理类对象引用，并且在
     * 对象初始化完毕后，设置它，以待自调用时使用。但是这样的设计就很蹩脚了。
     * 6.   打假专区：Spring的@Transactional有事务传播的情况下，@T1调用@T2的时候，@T2会生效吗？
     * 只要@T1和@T2不在同一个类，就可以生效呀，也就是事务在传播的时候，是不能在同一个类的。
     *
     * @param productId
     * @param thisObj
     * @return
     */
    public Product findProductBySelfCall(Long productId, Object thisObj) {
        logger.info("method-findProductBySelfCall been called.");

//        ProductService productService = (ProductService) context.getBean("productService");
//        logger.info("productService==this？{}", productService == this);
//        return productService.findProductById(productId);

        ProductService productservice = (ProductService) thisObj;
        return productservice.findProductById(productId);

    }

    /**
     * 可以用来加载指定id的商品数据到缓存中，也是更新缓存的一个手段
     *
     * @param productId
     * @return
     */
    @LogExecutionTime
    @CachePut(cacheNames = "productWithId", key = "#productId")
    public Product updateProductById(Long productId) {
        //  当该函数是事务的一部分时，如果事务回滚了，这里函数的'更新'会回滚吗？
        return productMap.get(productId);
    }

    /**
     * 业务逻辑中正常的一个更新product的方法；
     * 而通过cacheName和key可以在业务更新流程中，一并更新掉缓存。
     * 关于更新缓存的思考：
     * 1.如果该方法返回null，缓存会被刷为null吗？更新缓存是用的入参还是方法的返回呢？
     * 答：更新缓存用的return的返回；
     * 1.1 那如果return的类型不是product，那是要报错的吧。试一下
     * 答：return是void，那么按照null加载换粗；
     * 其他非product类型，则会报错'ClassCastException'
     * 2.如果product中没有id属性，会报错吗？总不能用一个null做为缓存的key吧。
     * 答：用unless吧？condition呢？
     *
     * @param product
     * @return
     */
    @LogExecutionTime
    @CachePut(cacheNames = "productWithId", key = "#product.id")
    public Product updateProduct(Product product) {
        product.setName(product.getName() + "updated in method");
        return product;
    }

    @LogExecutionTime
    @CachePut(cacheNames = "productWithId", key = "#product.id")
    public Long updateProductWithVoidReturn(Product product) {
        product.setName(product.getName() + "updated in method");
        return product.getId();

    }

    @LogExecutionTime
    @CacheEvict(cacheNames = "productWithId")
    public void deleteProductById(Long productId) {
        // 如果该函数执行失败，是否要执行缓存操作呢？这个问题比较容易解答，在cacheEvict中也有属性来说明的。
        logger.info("method deleteProductById was called with productId:{}", productId);
        // 可以去做一些数据库删除数据的操作
    }

    @LogExecutionTime
    @Cacheable(cacheNames = "product")
    public Product findProductByProduct(Product product) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productMap.get(product.getId());
    }

    @LogExecutionTime
    @Cacheable(cacheNames = "productWithName",
            key = "#productName",
            cacheManager = "caffeineCacheManager",
            unless = "#result == null")
    public Product findProductByName(String productName) {
        for (Product product : productMap.values()) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

}
