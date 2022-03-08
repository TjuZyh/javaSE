package com.javaSE.annotation;

import java.util.ArrayList;

public class AnnotationTest {

    public static void main(String[] args) {
        @MyAnnotation(value = "hello")
        int hello;
    }

}

class Generic<@MyAnnotation T>{
    public void show() throws @MyAnnotation RuntimeException{

        ArrayList<@MyAnnotation String> list = new ArrayList<>();

        int num = (@MyAnnotation int) 10L;
    }
}
