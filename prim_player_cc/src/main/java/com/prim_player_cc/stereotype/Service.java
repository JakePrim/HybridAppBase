package com.prim_player_cc.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author prim
 * @version 1.0.0
 * @desc 作用于业务逻辑视图用户和展示视图区分 业务视图不会添加到view中 只需要可以监听事件便可
 * @time 2020/4/9 - 2:54 PM
 * @contact https://jakeprim.cn
 * @name PeopleDaily_Android_CN
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any (or empty String otherwise)
     */
    String value() default "";

}
