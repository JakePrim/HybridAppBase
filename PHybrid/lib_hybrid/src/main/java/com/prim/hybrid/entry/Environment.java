package com.prim.hybrid.entry;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 环境配置信息
 * @time 2/20/21 - 2:41 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class Environment implements Serializable {
    private String id;

    private DataSource dataSource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "id='" + id + '\'' +
                ", dataSource=" + dataSource +
                '}';
    }
}
