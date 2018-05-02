package com.gtja.myspringmvc.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD) //作用于字段
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {
    public String value();
}
