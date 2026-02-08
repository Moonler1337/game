package studing;

public class Parent {
    public String name;
    public int age;

    public Parent(String name) {
        this.name = name;
    }

    public void speak() {
        System.out.println("Parent speaks");
    }

    public static void staticSpeak() {
        System.out.println("Parent static");
    }
}
