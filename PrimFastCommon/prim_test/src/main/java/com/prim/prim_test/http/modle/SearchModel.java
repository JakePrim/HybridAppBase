package com.prim.prim_test.http.modle;

import cn.prim.http.lib_net.model.Response;

import java.io.Serializable;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-05-10 - 18:53
 */
public class SearchModel implements Serializable {

    public SearchResult result;

    public int code;

    @Override
    public String toString() {
        return "SearchModel{" +
                "result=" + result +
                ", code=" + code +
                '}';
    }
}
