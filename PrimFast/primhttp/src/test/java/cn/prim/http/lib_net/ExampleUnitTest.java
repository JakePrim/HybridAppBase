package cn.prim.http.lib_net;

import android.support.annotation.FractionRes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testParadigm() {
        User<String> stringUser = new User<>();
        stringUser.setX("s");
        funsM("ss");
        S.<Integer>funsM(1);
        List<String> parse = parse(String.class);
        String[] strings = fun1("1", "2");
        Integer[] integers = fun1(1, 2, 3);
        //使用范型接口
        StringC stringC = min(new StringC("12"), new StringC("123"), new StringC("1"));
        System.out.println(stringC.string);

        String fruitName = getFruitName(new Apple());
        System.out.println("fruitName:" + fruitName);
        Ponit<String> stringPonit = new Ponit<>("1", "2");
        ////无边界通配符 ?
        Ponit<? extends Integer> ponit;
        ponit = new Ponit<>(1, 2);
    }

    //无边界通配符 ? 只能用于填充范型变量T
    public class Ponit<T> {
        public T x;
        public T y;

        public Ponit(T x, T y) {
            this.x = x;
            this.y = y;
        }
    }


    //绑定类
    class Fruit {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static <T extends Fruit> String getFruitName(T t) {
        return t.getName();
    }

    public class Apple extends Fruit {
        public Apple() {
            setName("apple");
        }
    }

    //范型类型绑定 限制范型范围 调用范型内部函数
    public static <T extends Comparable> T min(T... a) {
        T small = a[0];
        for (T item : a) {
            if (small.conparaeTo(item)) {
                small = item;
            }
        }
        return small;
    }

    public class StringC implements Comparable<StringC> {

        private String string;

        public StringC(String string) {
            this.string = string;
        }

        @Override
        public boolean conparaeTo(StringC i) {
            if (string.length() > i.string.length()) {
                return true;
            }
            return false;
        }
    }

    //绑定接口
    public interface Comparable<T> {
        boolean conparaeTo(T i);
    }

    //范型数组
    public static <T> List<T> parse(Class<T> tClass) {
        List<T> mList = new ArrayList<>();
        return mList;
    }

    public static <T> T[] fun1(T... arg) {
        return arg;
    }

    //范型函数
    public <T> void funsM(T a) {
        T s;
    }

    public static class S {
        public static <T> void funsM(T a) {
            T s;
        }
    }

    //范型类
    public class User<T> {
        T x;


        public void setX(T t) {
            x = t;
        }

        public T getX() {
            return x;
        }
    }
}