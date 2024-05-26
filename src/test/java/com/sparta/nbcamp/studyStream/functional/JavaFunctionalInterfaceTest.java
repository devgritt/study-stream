package com.sparta.nbcamp.studyStream.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.*;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaFunctionalInterfaceTest {

    @DisplayName("Function - T 타입을 받아서 R 타입을 리턴하는 함수 인터페이스")
    @Test
    void functionApply() {
        // given
        Function<Integer, String> plus10 = (number) -> String.valueOf(number + 10);

        // when
        String result = plus10.apply(10);

        // then
        assertThat(result).isEqualTo("20");

    }

    @DisplayName("Function default 메서드 compose - 인자로 Function 객체를 받으며 두 개의 Function을 합쳐진 하나의 Function 객체로 리턴한다.")
    @Test
    void functionCompose() {
        // given
        Function<Integer, Integer> multiply3 = (number) -> number * 3;

        // when
        Integer result = multiply3.compose((Integer number) -> {
            return number + 10;
        }).apply(5);   // <- 일급 객체 특징 2번에 해당 (2.모든 일급 객체는 함수의 파라미터로 전달 할 수 있어야 한다.)

        // then
        assertThat(result).isEqualTo(45);
    }

    @DisplayName("Function default 메서드 andThen - 파라미터로 Function 객체를 받으며 다수의 Function들을 순차적으로 실행한다.")
    @Test
    void functionAndThen() {
        // given
        Function<Integer, Integer> plus10 = (number) -> number + 10;
        Function<Integer, Integer> multiply3 = (number) -> number * 3;

        // when
        Integer result = multiply3
                .andThen(plus10)    // <- andThen 메서드 내부 return 값을 보면 일급 객체 특징 3번에 해당 (3.모든 일급 객체는 함수의 리턴값으로 사용할 수 있어야 한다.)
                .apply(5);

        // then
        assertThat(result).isEqualTo(25);
    }

    @DisplayName("BiFunction - 두 개의 입력값(T, U)를 받아서 R 타입을 리턴하는 함수 인터페이스")
    @Test
    void biFunctionApply() {
        // given
        BiFunction<Integer, Integer, Integer> plus = (number1, number2) -> number1 + number2;
        BiFunction<Integer, Integer, Integer> minus = (number1, number2) -> number1 - number2;
        BiFunction<Integer, Integer, Integer> multiply = (number1, number2) -> number1 * number2;
        BiFunction<Integer, Integer, Float> divide = (number1, number2) -> Float.valueOf(number1 / number2);


        // when
        Integer plusResult = plus.apply(2,2);
        Integer minusResult = minus.apply(4,2);
        Integer multiplyResult = multiply.apply(3,2);
        Float divideResult = divide.apply(4,2);


        // then
        assertThat(plusResult).isEqualTo(4);
        assertThat(minusResult).isEqualTo(2);
        assertThat(multiplyResult).isEqualTo(6);
        assertThat(divideResult).isEqualTo(2.0f);
    }

    // System.out.println 검증 - https://eblo.tistory.com/123 참고
    @DisplayName("Consumer - T 타입을 받아서 아무값도 리턴하지 않는 함수 인터페이스")
    @Test
    void consumerAccept() {
        // given
        Consumer<String> printf = (s) -> System.out.println(s);

        // when & then
        printf.accept("Consumer test");
    }

    @DisplayName("Supplier - T 타입의 값을 제공하는 함수 인터페이스")
    @Test
    void supplier() {
        // given
        Supplier<String> tutor = () -> "tutor";

        // when
        String result = tutor.get();

        // then
        assertThat(result).isEqualTo("tutor");
    }

    @DisplayName("Predicate - T 타입을 받아서 boolean을 리턴하는 함수 인터페이스")
    @Test
    void predicate() {
        // given
        Predicate<Integer> over10 = (number) -> number > 10;

        // when
        boolean result = over10.test(25);

        // then
        assertThat(result).isTrue();
    }
}
