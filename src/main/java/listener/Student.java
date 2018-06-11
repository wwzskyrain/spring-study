package listener;

import event.TestPaper;
import org.springframework.context.ApplicationListener;

public class Student implements ApplicationListener<TestPaper> {

    private String name;


    public void onApplicationEvent(TestPaper event) {
        String question = event.getQuestion();

        System.out.printf("student:%s received a question:%s\n",name,question);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
