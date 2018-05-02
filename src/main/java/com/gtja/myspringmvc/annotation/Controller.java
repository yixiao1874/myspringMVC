package com.gtja.myspringmvc.annotation;

import java.lang.annotation.*;

@Documented //java Doc
@Target(ElementType.TYPE) //作用于类上
@Retention(RetentionPolicy.RUNTIME) //运行期
public @interface Controller {
    public String value();
}
