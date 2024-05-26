package com.sparta.nbcamp.studyStream.functional;

@FunctionalInterface
public interface Foo {
    Integer operate(Integer number1, Integer number2);

    // void action();

    // boolean equals(Object o);

    static void printName() {
        System.out.println("Dongil Kim");
    }

    default String getRole() {
        return "tutor";
    }
}
