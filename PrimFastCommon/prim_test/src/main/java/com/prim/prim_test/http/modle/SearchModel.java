package com.prim.prim_test.http.modle;

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
    public String code;

    public class SearchResult {
        public List<Song> songs;

        @Override
        public String toString() {
            return "SearchResult{" +
                    "songs=" + songs +
                    '}';
        }
    }

    public class Song {
        public String id;
        public String name;
        public String status;

        @Override
        public String toString() {
            return "Song{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SearchModel{" +
                "result=" + result +
                ", code='" + code + '\'' +
                '}';
    }
}
