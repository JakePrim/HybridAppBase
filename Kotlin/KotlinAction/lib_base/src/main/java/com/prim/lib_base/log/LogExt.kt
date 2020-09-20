package com.prim.lib_base.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @desc 打印日志
 * @author prim
 * @time 2019-05-29 - 09:52
 * @version 1.0.0
 */

val logMap = HashMap<Class<*>, Logger>()

//reified ??
inline val <reified T> T.logger: Logger
       get() {
           return logMap[T::class.java]?:LoggerFactory.getLogger(T::class.java).apply { logMap[T::class.java] = this }
       }