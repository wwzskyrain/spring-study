package erik.spring.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author erik.wang
 * @date 2019/03/03
 **/
public class Person implements Serializable {

    private static final long serialVersionUID = -8319105369996771360L;
    private String name;
    private boolean sex;

    private transient Date birthDay;


    public Person() {
    }

    public Person(String name, boolean sex) {
        this.name = name;
        this.sex = sex;
    }

    public Person(String name, boolean sex, Date birthDay) {
        this.name = name;
        this.sex = sex;
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Person) {

            return ((Person) obj).getName().equals(this.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public static void main(String[] args) {

        Person wwz1 = new Person("wwz", true);
        Person wwz2 = new Person("wwz", true);

        System.out.println(wwz1.equals(wwz2));

        Set<Person> personSet = new HashSet<>();

        personSet.add(wwz1);
        personSet.add(wwz2);

        System.out.println(personSet.size());

    }


}
