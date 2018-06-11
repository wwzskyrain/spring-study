package listener;

import java.sql.SQLOutput;

public class Person {

    private String name;

    public Person(){
        System.out.println("==正在执行Person无参构造函数==");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("Person.setName() "+name);
    }
}
