package com.sparta.nbcamp.studyStream.lambda;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaTest {

    @Test
    @DisplayName("익명 내부 클래스와 람다식 차이 - filter")
    void test1() {
        // given
        List<Integer> numbers = List.of(1,2,3,4,5);

        // when
        List<Integer> even = numbers.stream()
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer number) {
                        return number % 2 == 0;
                    }
                })
                .collect(Collectors.toList());

        List<Integer> odd = numbers.stream()
                .filter((number) -> number % 2 == 1)
                .collect(Collectors.toList());

        // then
        Assertions.assertThat(even).containsExactly(2, 4);
        Assertions.assertThat(odd).containsExactly(1, 3, 5);
    }

    @Test
    @DisplayName("익명 내부 클래스와 람다식 차이 - compare")
    void test2() {

        // given
        List<Integer> numbers = List.of(1,2,3,4,5);

        // when
        Optional<Integer> minNumber = numbers.stream()
                .min(new Comparator<>() {
                    @Override
                    public int compare(Integer number1, Integer number2) {
                        return Integer.compare(number1, number2);
                    }
                });

        Optional<Integer> maxNumber1 = numbers.stream()
                .max((number1, number2) -> Integer.compare(number1, number2));

        Optional<Integer> maxNumber2 = numbers.stream()
                .max(Integer::compare);

        // then
        Assertions.assertThat(minNumber).isEqualTo(Optional.of(1));
        Assertions.assertThat(maxNumber1).isEqualTo(Optional.of(5));
        Assertions.assertThat(maxNumber2).isEqualTo(Optional.of(5));
    }
}