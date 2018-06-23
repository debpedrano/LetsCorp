package me.shouheng.letscorp.di.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author shouh
 * @version $Id: ActivityScoped, v 0.1 2018/6/23 12:28 shouh Exp$
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped { }

