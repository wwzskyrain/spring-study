package listener;

import org.springframework.beans.factory.InitializingBean;

public class MyInitBean implements InitializingBean {

    private String initField ;

    public String getInitField() {
        return initField;
    }

    public void setInitField(String initField) {
        this.initField = initField;
    }

    public void afterPropertiesSet() throws Exception {

        if(initField==null){
            System.out.println("initField==null");
        }
        else {
            System.out.printf("MyInitBean.afterPropertiesSet(): initField=%s\n",initField);
        }

    }

    public void initMethod(){

        System.out.println(MyInitBean.class.getName()+".initMethod() is called!");
    }
}
