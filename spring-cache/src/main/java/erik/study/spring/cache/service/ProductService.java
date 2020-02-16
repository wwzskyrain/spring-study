package erik.study.spring.cache.service;

import com.alibaba.fastjson.JSON;
import erik.study.spring.cache.Product;
import erik.spring.util.aop.annotation.LogExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private static final String PRODUCT_NAME_PATTERN = "product_name_%d";

    public static Map<Long, Product> productMap = new HashMap<>();

    public static Product supply(Long productId) {
        return new Product(productId, getProductName(productId), new BigDecimal(productId));
    }

    public static String getProductName(Long productId) {
        return String.format(PRODUCT_NAME_PATTERN, productId);
    }

    static {
        for (int i = 0; i < 10; i++) {
            Product product = supply(i * 1L);
            product.setPrice(new BigDecimal(i));
            productMap.put(product.getId(), product);
            logger.info("init product data over: {}", JSON.toJSONString(product));
        }
    }

    @Cacheable(cacheNames = "productWithId", condition = "#productId < 3")
    @LogExecutionTime
    public Product findProductById(Long productId) {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productMap.get(productId);
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
