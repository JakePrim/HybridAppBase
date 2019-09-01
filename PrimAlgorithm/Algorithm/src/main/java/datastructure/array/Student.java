package datastructure.array;

public class Student {
    private String name;
    private int source;

    public Student(String name, int source) {
        this.name = name;
        this.source = source;
    }

    @Override
    public String toString() {
        return String.format("Student(name:%s,source:%d)", name, source);
    }

    public static void main(String[] args) {
        Array<Student> array = new Array<>();
        array.addLast(new Student("ss",90));
        array.addLast(new Student("ss2",91));
        array.addLast(new Student("ss1",94));

        System.out.println("args = [" + array.toString() + "]");
    }
}
