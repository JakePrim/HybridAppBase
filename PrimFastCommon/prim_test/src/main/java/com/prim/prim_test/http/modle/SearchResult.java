package com.prim.prim_test.http.modle;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    public List<Song> songs;

    @Override
    public String toString() {
        return "SearchResult{" +
                "songs=" + songs +
                '}';
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
}
