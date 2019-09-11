package erik.study.spring.base.life;

import erik.study.spring.base.life.beans.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2019-09-08
 * @Created by erik
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = applicationContext.getBean("chinese", Person.class);
        person.useAxe();
    }

}
