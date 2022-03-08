package com.javaSE.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.LOCAL_VARIABLE,ElementType.TYPE_PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    //内部定义成员，通常使用value表示
    String value() default "hello";
}
