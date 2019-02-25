package com.prim.base;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.UnaryOperator;

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
    public void teset() {
        AtomicReference<String> atomicReference = new AtomicReference<>();
        atomicReference.set("Hello");
        boolean b = atomicReference.compareAndSet("Hello", "World");
        System.out.println("b: " + b + " a :" + atomicReference.get());
        String s = atomicReference.updateAndGet(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return "HelloWorld";
            }
        });

        System.out.println(s + " b :" + atomicReference.get());
    }

    class SimpleValueHolder{

    }

}