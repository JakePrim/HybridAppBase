package com.prim.primweb.core.config;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ConfigKey.API_HOST, ConfigKey.CONFIG_READY, ConfigKey.WEB_POOL,ConfigKey.APPLICATION_CONTEXT,ConfigKey.WEB_X5CORE})
@Retention(RetentionPolicy.SOURCE)
public @interface ConfigAnnotation {
}
