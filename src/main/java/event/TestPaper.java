package event;

import org.springframework.context.ApplicationEvent;

public class TestPaper extends ApplicationEvent {

    private String question;

    public TestPaper(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public TestPaper(Object source, String question) {
        super(source);
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
