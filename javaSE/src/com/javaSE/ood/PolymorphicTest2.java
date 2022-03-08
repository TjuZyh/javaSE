package com.javaSE.ood;

public class PolymorphicTest2 {

    public static void main(String[] args) {
        Iterator iterator1 = new MyArrayList();
        //method of myArrayList
        iterator1.hasNest();

        Iterator iterator2 = new MyLinkedList();
        //method of myLinkedList
        iterator2.hasNest();

    }

}

interface Iterator{
     String hasNest();

     String next();

     String remove();
}

class MyArrayList implements Iterator{
    @Override
    public String hasNest() {
        System.out.println("method of myArrayList");
        return null;
    }

    @Override
    public String next() {
        return null;
    }

    @Override
    public String remove() {
        return null;
    }
}

class MyLinkedList implements Iterator{
    @Override
    public String hasNest() {
        System.out.println("method of myLinkedList");
        return null;
    }

    @Override
    public String next() {
        return null;
    }

    @Override
    public String remove() {
        return null;
    }
}
