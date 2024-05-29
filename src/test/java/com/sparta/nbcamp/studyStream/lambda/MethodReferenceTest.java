package com.sparta.nbcamp.studyStream.lambda;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MethodReferenceTest {

    /**
     * 람다 : (x) -> ClassName.method(x)
     * 메서드 참조 : ClassName::method
     */
    @DisplayName("정적 메서드 참조 - 람다 vs 메서드 참조")
    @Test
    void staticReference() {
        // given
        Consumer<String> consumer1 = (String name) -> System.out.println("My name is " + name);
        Consumer<String> consumer2 = name -> System.out.println(name);
        Consumer<String> consumer3 = System.out::println;


        // when & then
        consumer1.accept("Dongil Kim");
        consumer2.accept("Dongil Kim");
        consumer3.accept("Dongil Kim");
    }

    /**
     * 람다 : (x) -> obj.method(x)
     * 메서드 참조 : obj::method
     * 좋지 않은 테스트 -> Instant.now() 때문에 시간에 따라 테스트 결과가 달라지기 때문.
     * Instant 관련 블로그 : https://www.daleseo.com/java8-instant/#google_vignette
     */
    @DisplayName("한정적 인스턴스 메서드 참조 - 람다 vs 메서드 참조")
    @Test
    void boundInstanceMethodReference1() {
        // given
        Function<Instant, Boolean> isAfter1 = (Instant instant) -> {
            Instant then = Instant.now();
            return then.isAfter(instant);
        };
        Function<Instant, Boolean> isAfter2 = Instant.now()::isAfter;

        // when
        Boolean result1 = isAfter1.apply(Instant.parse("2024-06-27T19:34:50.000Z"));
        Boolean result2 = isAfter2.apply(Instant.parse("2024-05-24T19:34:50.000Z"));

        // then
        Assertions.assertThat(result1).isFalse();
        Assertions.assertThat(result2).isTrue();
    }

    /**
     * boundInstanceMethodReference1 테스트를 더 좋은 테스트로 만드는 방법
     */
    @DisplayName("한정적 인스턴스 메서드 참조 - 람다 vs 메서드 참조")
    @Test
    void boundInstanceMethodReference2() {
        // given
        CharSequence targetTime = "2024-05-27T14:26:50.000Z";
        BiFunction<Instant, CharSequence, Boolean> isAfter = (Instant instant, CharSequence text) -> {
            Instant then = Instant.parse(text);
            return then.isAfter(instant);
        };

        // when
        Boolean result1 = isAfter.apply(Instant.parse("2024-06-27T19:34:50.000Z"), targetTime);
        Boolean result2 = isAfter.apply(Instant.parse("2024-05-24T19:34:50.000Z"), targetTime);

        // then
        Assertions.assertThat(result1).isFalse();
        Assertions.assertThat(result2).isTrue();
    }

    /**
     * 람다 : (x) -> obj.method(x)
     * 메서드 참조 : obj::method
     */
    @DisplayName("비한정적 인스턴스 메서드 참조 - 람다 vs 메서드 참조")
    @Test
    void unboundInstanceMethodReference() {
        // given
        Function<String, String> toLowerCase1 = (String str) -> str.toLowerCase();
        Function<String, String> toLowerCase2 = String::toLowerCase;

        // when
        String result1 = toLowerCase1.apply("JAVA");
        String result2 = toLowerCase2.apply("SPRING BOOT");

        // then
        Assertions.assertThat(result1).isEqualTo("java");
        Assertions.assertThat(result2).isEqualTo("spring boot");
    }

    /**
     * 람다 : (x, y) -> new ClassName(x, y)
     * 메서드 참조 : obj::method
     */
    @DisplayName("클래스 생성자 메서드 참조 - 람다 vs 메서드 참조")
    @Test
    void classConstructorMethodReference() {
        // given
        BiFunction<String, Integer, Subject> createSubject1 = (String title, Integer level) -> new Subject(title, level);
        BiFunction<String, Integer, Subject> createSubject2 = Subject::new;

        // when
        Subject result1 = createSubject1.apply("Java", 6);
        Subject result2 = createSubject2.apply("Spring Boot", 8);

        // then
        Assertions.assertThat(result1.getTitle()).isEqualTo("Java");
        Assertions.assertThat(result1.getLevel()).isEqualTo(6);
        Assertions.assertThat(result2.getTitle()).isEqualTo("Spring Boot");
        Assertions.assertThat(result2.getLevel()).isEqualTo(8);
    }

    public class Subject {
        private String title;
        private Integer level;

        Subject(String title, Integer level) {
            this.title = title;
            this.level = level;
        }

        public String getTitle() {
            return title;
        }

        public Integer getLevel() {
            return level;
        }
    }

    /**
     * 람다 : (x) -> new Array[x]
     * 메서드 참조 : Array[]::new
     */
    @DisplayName("배열 생성자 메서드 참조 - 람다 vs 메서드 참조")
    @Test
    void arrayConstructorMethodReference() {
        // given
        Function<Integer, int[]> createArray1 = (Integer len) -> new int[len];
        Function<Integer, int[]> createArray2 = int[]::new;

        // when
        int[] result1 = createArray1.apply(10);
        int[] result2 = createArray2.apply(100);

        // then
        Assertions.assertThat(result1.length).isEqualTo(10);
        Assertions.assertThat(result2.length).isEqualTo(100);
    }
}
