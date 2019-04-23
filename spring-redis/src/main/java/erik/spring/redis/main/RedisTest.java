package erik.spring.redis.main;

import com.alibaba.fastjson.JSON;

import erik.spring.common.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import erik.spring.redis.config.RedisTemplateConfig;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisTemplateConfig.class})
public class RedisTest {


    private final static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {

        Map<String, Object> properties = new HashMap<>();

        properties.put("key-123", "value-123");
        properties.put("key-abc", 456);

        redisTemplate.opsForHash().putAll("hashKey", properties);

        Map<String, Object> ans = redisTemplate.opsForHash().entries("hashKey");
        System.out.println("ans:" + ans);

        for (Map.Entry<String, Object> entry : ans.entrySet()) {
            System.out.println(JSON.toJSONString(entry));
        }

        redisTemplate.opsForList();

    }

    @Test
    public void testCast() throws ParseException {

        List<Person> personList = getBrothers();

        String brotherKey = getKeyForBrother("wyk");

        SetOperations setOperations = redisTemplate.opsForSet();

        personList.forEach(person -> setOperations.add(brotherKey, person));

        logger.info("save brother to redis success");

        Set<Person> brothers = redisTemplate.opsForSet().members(brotherKey);

        int i = 1;
        for (Person person : brothers) {
            logger.info("{}:{}", i++, JSON.toJSONString(person));
        }

        logger.info("brother:{}", JSON.toJSONString(brothers));

    }

    @Test
    public void test_serializer() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, 5, 16, 1, 1, 1);
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("wusong", true, calendar.getTime()));
        calendar.add(Calendar.YEAR, 1);
        personList.add(new Person("wusong", true, calendar.getTime()));
        String brotherKeyForWuSong = getKeyForBrother("wusong");
        redisTemplate.opsForSet().add(brotherKeyForWuSong, personList.toArray(new Person[0]));

        Set<Person> brothers = redisTemplate.opsForSet().members(brotherKeyForWuSong);

        logger.info("brothers:{}", JSON.toJSONString(brothers));

    }

    @Test
    /**
     * 主要是要熟悉redis的pipeline返回值的结构
     */
    public void test_multi_query() {

        List<String> names = Arrays.asList("wyk", "wwz", "wyk", "nobody");

        List<String> keys = names.stream().map(name -> getKeyForBrother(name)).collect(Collectors.toList());

        List values = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                SetOperations setOperations = operations.opsForSet();


                for (int i = 0; i < keys.size(); i++) {
                    setOperations.members(keys.get(i));
                }

                return null;
            }
        });

        Set<Person> personSet = new HashSet<>();
        for (int i = 0; i < values.size(); i++) {

            Object value = values.get(i);
            if (value != null) {
                Set setValue = ((Set) value);
                personSet.addAll(setValue);
            }

        }

        logger.info("persons:{}", JSON.toJSONString(personSet));

    }

    public static List<Person> getBrothers() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<Person> personList = Arrays.asList(
                new Person("rjp", true, calendar.getTime()),
                new Person("rfh", true, calendar.getTime()));

        return personList;
    }

    public static List<Person> getBrothers1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1990, 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<Person> personList = Arrays.asList(
                new Person("wjh", true, calendar.getTime()),
                new Person("wzh", true, calendar.getTime()),
                new Person("wjk", true, calendar.getTime()));

        return personList;
    }


    public static String getKeyForBrother(String name) {
        return String.format("brother:%s", name);
    }

    @Test
    public void testRedisOpsForHash() {


    }

}
