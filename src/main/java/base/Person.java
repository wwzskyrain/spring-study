package base;

public class Person {

    private Axe axe;

    public void setAxe(Axe axe){
        this.axe=axe;
    }

    public void useAxe(){
        System.out.println("我打算去砍柴来烧火");

        System.out.println(axe.chop());
    }

}
