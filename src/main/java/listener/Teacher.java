package listener;

import event.TestPaper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Teacher implements ApplicationContextAware {

    private String name;

    private ApplicationContext context;

    public Teacher(){

        System.out.println("----正在执行Teacher的无参构造函数----");

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        System.out.println("Teacher.setName() "+ name);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
        System.out.printf("method %s.%s is called!\n",Teacher.class.getName(),"setApplication()");
    }

    public void publishQuestion(String question){
        context.publishEvent(new TestPaper(context,question));
    }

}

