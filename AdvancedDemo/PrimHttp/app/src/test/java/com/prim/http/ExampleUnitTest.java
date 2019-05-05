package com.prim.http;

import android.util.Log;

import com.prim.http.test.BridgeInterceptor;
import com.prim.http.test.CacheInterceptor;
import com.prim.http.test.Interceptor;
import com.prim.http.test.RealInterceptorChain;
import com.prim.http.test.RetryAndFollowInterceptor;

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
    public void test() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new BridgeInterceptor());
        interceptors.add(new RetryAndFollowInterceptor());
        interceptors.add(new CacheInterceptor());

        RealInterceptorChain request = new RealInterceptorChain(interceptors, 0, "request");

        request.proceed("request");

    }
}