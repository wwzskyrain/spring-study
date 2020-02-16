package service;

import com.alibaba.fastjson.JSON;
import erik.study.spring.cache.Product;
import erik.study.spring.cache.SpringConfig;
import erik.study.spring.cache.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author erik.wang
 * @Date 2020/1/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ProductServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @Autowired
    private ProductService productService;


    @Test
    public void test_find_by_id_with_cacheable() {
        for (int i = 0; i < 10; i++) {
            Product foundProduct = productService.findProductById(i * 1L);
            logger.info("foundProduct is:{}", JSON.toJSONString(foundProduct));
        }

        for (int i = 0; i < 100; i++) {
            Long id = i % 10 * 1L;
            Product foundProduct = productService.findProductById(id);
            logger.info("foundProduct is:{}", JSON.toJSONString(foundProduct));
        }
    }

    @Test
    public void test_find_by_product_with_cacheable() {
        for (int i = 0; i < 10; i++) {
            Product product = ProductService.supply(i * 1L);
            Product foundProduct = productService.findProductByProduct(product);
            logger.info("foundProduct is:{}", JSON.toJSONString(foundProduct));
        }

        for (int i = 0; i < 100; i++) {
            Long id = i % 10 * 1L;
            Product product = ProductService.supply(id);
            Product foundProduct = productService.findProductByProduct(product);
            logger.info("foundProduct is:{}", JSON.toJSONString(foundProduct));
        }
    }

    @Test
    public void test_find_by_product_and_by_id_with_cacheable() {
        for (int i = 0; i < 5; i++) {
            Long id = i * 1L;
            productService.findProductById(id);
        }
        logger.info("----------------");
        for (int i = 0; i < 5; i++) {
            Long id = i % 10 * 1L;
            Product product = ProductService.supply(id);
            Product foundProduct = productService.findProductByProduct(product);
            logger.info("foundProduct is:{}", JSON.toJSONString(foundProduct));
        }
    }

    @Test
    public void test_is_found_product_same_as_cached_product() {
        Long productId = 1L;
        Product firstFoundProduct = productService.findProductById(productId);
        logger.info("firstFoundProduct:{}", firstFoundProduct);

        Product secondFoundProduct = productService.findProductById(productId);
        logger.info("secondFoundProduct:{}", secondFoundProduct);

        Product thirdFoundProduct = productService.findProductByProduct(ProductService.supply(productId));
        logger.info("thirdFoundProduct:{}", thirdFoundProduct);

        Product cachedProduct = ProductService.productMap.get(productId);
        logger.info("cachedProduct:{}", cachedProduct);

    }

    @Test
    public void test_cache_put() {
        Long productId = 1L;
        Long productIdNotCached = 2L;
        productService.updateProductById(1L);
        productService.findProductById(productId);
        productService.findProductByProduct(ProductService.supply(productId));
        productService.findProductById(productIdNotCached);
    }

    @Test
    public void test_cache_evict() {
        Long productId1 = 1L;
        Long productId2 = 2L;
        productService.updateProductById(productId1);
        productService.updateProductById(productId2);
        productService.findProductById(productId1);
        logger.info("call findByProductId method with productId:{} over!", productId1);
        productService.findProductById(productId2);
        logger.info("call findByProductId method with productId:{} over!", productId2);

        logger.info("evict cache with productId:{}", productId1);
        productService.deleteProductById(productId1);
        productService.findProductById(productId1);
        logger.info("call findByProductId method with productId:{} over!", productId1);
        productService.findProductById(productId2);
        logger.info("call findByProductId method with productId:{} over!", productId2);
    }

    @Test
    public void test_cache_with_condition() {

        productService.updateProductById(1L);
        productService.updateProductById(2L);
        productService.updateProductById(3L);

        productService.findProductById(1L);
        productService.findProductById(2L);
        productService.findProductById(3L);
    }


    @Test
    public void test_update_product_by_product() {

        Long productId = 1L;

        productService.updateProductById(productId);
        Product firstFoundProduct = productService.findProductById(productId);
        logger.info("foundProduct={}", firstFoundProduct);

        Product newProduct = ProductService.supply(productId);
        newProduct.setName("updatedName");
        productService.updateProductWithVoidReturn(newProduct);
        logger.info("----更新product成功后----");

        Product secondFoundProduct = productService.findProductById(productId);
        logger.info("firstFoundProduct={}", firstFoundProduct);
        logger.info("secondFoundProduct={}", secondFoundProduct);

        Assert.assertNotEquals(firstFoundProduct, secondFoundProduct);

    }

    @Test
    public void test_find_from_caffeine_cache_manager() {
        String productName = ProductService.getProductName(1L);
        productService.findProductByName(productName);
        productService.findProductByName(productName);
    }

    @Test
    public void test_caffeine_cache_load_when_result_is_null() {
        String productName = ProductService.getProductName(11L);
        logger.info("{}", productService.findProductByName(productName));
        logger.info("{}", productService.findProductByName(productName));

        String product1Name = ProductService.getProductName(1L);
        logger.info("{}", productService.findProductByName(product1Name));
        logger.info("{}", productService.findProductByName(product1Name));
    }

    @Test
    public void test_expire_policy_for_caffeine_cache_manager() {
        String product1Name = ProductService.getProductName(1L);
        productService.findProductByName(product1Name);
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("{}", productService.findProductByName(product1Name));

        String product2Name = ProductService.getProductName(2L);
        logger.info("{}", productService.findProductByName(product2Name));
    }

    /**
     * 测试Caffeine的过期策略特性值afterWrite
     */
    @Test
    public void test_expire_policy_for_after_write() {
        String product1Name = ProductService.getProductName(1L);
        productService.findProductByName(product1Name);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            logger.info("{}", productService.findProductByName(product1Name));
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("{}", productService.findProductByName(product1Name));
    }
}
