package config.model;

/**
 * @author erik.wang
 * @date 2020-06-12 09:54
 */

public class Student {

    long id;
    String name;
    int age;
    boolean man;

    public Student(long id, String name, int age, boolean man) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.man = man;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }
}
