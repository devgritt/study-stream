package com.sparta.nbcamp.studyStream.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FooTest {

    @DisplayName("함수형 인터페이스 - 익명 내부 클래스(Anonymous Inner Class) 이용")
    @Test
    void anonymousInnerClass() {
        // given
        Foo foo = new Foo() {
            @Override
            public Integer operate(Integer number1, Integer number2) {
                return number1 + number2;
            }
        };

        // when
        Integer result = foo.operate(2,3);

        // then
        assertThat(result).isEqualTo(5);
    }

    @DisplayName("함수형 인터페이스 - 람다 이용")
    @Test
    void lambda() {
        // given
        Foo foo = (number1, number2) -> number1 + number2;    // <- 일급 객체 특징 1번에 해당 (1.모든 일급 객체는 변수나 데이터에 담을 수 있어야 한다.)

        // when
        Integer result = foo.operate(4, 5);

        // then
        assertThat(result).isEqualTo(9);
    }
}