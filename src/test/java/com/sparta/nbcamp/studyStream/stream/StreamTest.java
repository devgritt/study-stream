package com.sparta.nbcamp.studyStream.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StreamTest {

    @DisplayName("원본 데이터를 변경하지 않습니다.")
    @Test
    void doNotChangeSourceData() {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);   // List.of는 Immutable 하기 때문에 Arrays.asList 사용

        // 1. when & then
        numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5);

        // 2. when & then
        Collections.sort(numbers, Collections.reverseOrder());
        assertThat(numbers).containsExactly(5, 4, 3, 2, 1);
    }

    @DisplayName("스트림은 재사용이 불가능하다.")
    @Test
    void canNotBeReuse() {
        // given
        Stream<Integer> numberStream = List.of(1, 2, 3, 4, 5).stream();

        // when & then
        assertThatThrownBy(() -> {
            List<Integer> even = numberStream
                    .filter((n) -> n % 2 == 0)
                    .collect(Collectors.toList());

            List<Integer> odd = numberStream
                    .filter((n) -> n % 2 == 1)
                    .collect(Collectors.toList());
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("stream has already been operated upon or closed");
    }
}
