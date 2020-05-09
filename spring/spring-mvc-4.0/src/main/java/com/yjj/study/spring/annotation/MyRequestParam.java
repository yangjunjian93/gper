package com.yjj.study.spring.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {

    String value() default "";

}
