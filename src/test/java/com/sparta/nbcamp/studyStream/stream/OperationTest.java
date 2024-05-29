package com.sparta.nbcamp.studyStream.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sparta.nbcamp.studyStream.stream.OperationTest.Gender.FEMALE;
import static com.sparta.nbcamp.studyStream.stream.OperationTest.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class OperationTest {

    @DisplayName("Stream의 Filter를 사용해 30대 사람들만 필터링해서 새로운 Person객체 리스트를 생성한다.")
    @Test
    void useStreamFilter() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        List<Person> people30s = people.stream()
                .filter(person -> person.getAge() >= 30 && person.getAge() < 40)
                .collect(Collectors.toList());

        // then
        assertThat(people30s).extracting("id", "name", "age", "mbti", "gender")
                .hasSize(2)
                .contains(
                        tuple(2L, "Lee", 31, "INFJ", MALE),
                        tuple(5L, "Kang", 33, "ISTJ", FEMALE)
                );
    }

    @DisplayName("개선된 For문을 사용해 30대 사람들만 필터링해서 새로운 Person객체 리스트를 생성한다.")
    @Test
    void useFor() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        List<Person> people30s = new ArrayList<>();
        for(Person person : people) {
            if(person.getAge() >= 30 && person.getAge() < 40) {
                people30s.add(person);
            }
        }

        // then
        assertThat(people30s).extracting("id", "name", "age", "mbti", "gender")
                .hasSize(2)
                .contains(
                        tuple(2L, "Lee", 31, "INFJ", MALE),
                        tuple(5L, "Kang", 33, "ISTJ", FEMALE)
                );
    }

    @DisplayName("Stream의 Map을 사용해 MBTI가 E인 사람들의 MBTI 리스트(List<String)를 생성한다.")
    @Test
    void useStreamMap() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        List<String> mbtiList = people.stream()
                .filter(person -> person.getMbti().startsWith("E"))
                .map(Person::getMbti)
                .collect(Collectors.toList());

        // then
        assertThat(mbtiList).contains("ENFP", "ESTJ")
                .hasSize(2);
    }

    @DisplayName("Stream의 Sorted를 사용해 나이를 오름차순으로 정렬해 새로운 Person객체 리스트를 생성한다.")
    @Test
    void useStreamSorted() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        List<Person> sortedPeople = people.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList());

        // then
        assertThat(sortedPeople).extracting("id", "name", "age", "mbti", "gender")
                .hasSize(5)
                .containsExactly(
                        tuple(3L, "Hong", 20, "ESTJ", FEMALE),
                        tuple(4L, "Park", 24, "INFP", MALE),
                        tuple(1L, "Kim", 28, "ENFP", MALE),
                        tuple(2L, "Lee", 31, "INFJ", MALE),
                        tuple(5L, "Kang", 33, "ISTJ", FEMALE)
                );
    }

    @DisplayName("Stream의 Distinct를 사용해 중복 제거 후 새로운 Person객체 리스트를 생성한다.")
    @Test
    void useStreamDistinct() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE)
        );

        // when
        List<Person> sortedPeople = people.stream()
                .distinct()
                .collect(Collectors.toList());

        // then
        assertThat(sortedPeople).extracting("id", "name", "age", "mbti", "gender")
                .hasSize(3)
                .containsExactly(
                        tuple(1L, "Kim", 28, "ENFP", MALE),
                        tuple(2L, "Lee", 31, "INFJ", MALE),
                        tuple(3L, "Hong", 20, "ESTJ", FEMALE)
                );
    }

    /**
     * Peak는 Stream에 영향을 주지 않고 특정 연산을 수행한다.
     * MapTo~ 는 기본 타입 Stream을
     *
     */
    @DisplayName("남성인 사람들의 평균 나이를 계산한다. (Stream의 Peak, MapToInt, Average 사용)")
    @Test
    void useStreamPeakAndMapToInt() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        double average = people.stream()
                .filter(p -> p.getGender() == MALE)
                .peek(System.out::println)
                .map(Person::getAge)
                .peek(System.out::println)
                .mapToInt(Integer::intValue)
                .average()
                .orElseGet(() -> 0.0);

        // then
        assertThat(average).isEqualTo(27.666666666666668);
    }

    @DisplayName("남성인 사람들의 평균 나이를 계산한다. - for문")
    @Test
    void malePeopleAverageAge() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        double ageSum = 0;
        double count = 0;
        for(Person person : people) {
            if(person.getGender() == MALE) {
                System.out.println(person.toString());
                System.out.println(person.getAge());
                ageSum += person.getAge();
                count += 1;
            }
        }
        double average = ageSum / count;


        // then
        assertThat(average).isEqualTo(27.666666666666668);
    }

    @DisplayName("20대인 사람들이 몇 명인지 카운트 한다.")
    @Test
    void count20sPeople() {
        // given
        List<Person> people = List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "INFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "INFP", MALE),
                new Person(5L, "Kang", 33, "ISTJ", FEMALE)
        );

        // when
        long count = people.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() < 30)
                .count();

        people.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() < 30)
                .forEach(System.out::println);

        // then
        assertThat(count).isEqualTo(3L);
    }

    @DisplayName(
            "1. MBTI E, T, I그룹의 People 리스트를 하나의 Person 리스트(List<Person>)로 만들어준다." +
                    "2. MBTI E, T, I그룹의 People 리스트를 하나의 나이 리스트(List<Integer>)로 만들어준다.")
    @Test
    void useFlatMap() {
        // given
        People mbtiGroupE = new People(List.of(
                new Person(1L, "Kim", 28, "ENFP", MALE),
                new Person(2L, "Lee", 31, "ENFJ", MALE),
                new Person(3L, "Hong", 20, "ESTJ", FEMALE),
                new Person(4L, "Park", 24, "ESTP", MALE),
                new Person(5L, "Kang", 33, "ENFJ", FEMALE)
        ));

        People mbtiGroupT = new People(List.of(
                new Person(6L, "Han", 28, "INTP", MALE),
                new Person(7L, "Ko", 31, "ESTP", MALE),
                new Person(8L, "Koo", 20, "ENTJ", FEMALE),
                new Person(9L, "Jeong", 24, "ESTJ", MALE),
                new Person(10L, "Hwang", 33, "ISTJ", FEMALE)
        ));

        People mbtiGroupI = new People(List.of(
                new Person(11L, "Son", 28, "INFP", MALE),
                new Person(12L, "Kim", 31, "INTP", MALE),
                new Person(13L, "Na", 20, "ISTP", FEMALE),
                new Person(14L, "Jang", 24, "INFP", MALE),
                new Person(15L, "Min", 33, "ISTJ", FEMALE)
        ));

        List<People> peopleList = List.of(mbtiGroupE, mbtiGroupT, mbtiGroupI);

        // when & then
        List<Person> personList = peopleList.stream()
                .flatMap(people -> people.getPeople().stream())
                .collect(Collectors.toList());
        personList.stream()
                .forEach(System.out::println);

        List<Integer> ageList = peopleList.stream()
                .flatMap(people -> people.getPeople().stream().map(Person::getAge))
                .collect(Collectors.toList());
        ageList.stream()
                .forEach(System.out::println);
    }


    public class Person {
        private Long id;
        private String name;
        private Integer age;
        private String mbti;
        private Gender gender;

        Person(Long id, String name, Integer age, String mbti, Gender gender) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.mbti = mbti;
            this.gender = gender;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public String getMbti() {
            return mbti;
        }

        public Gender getGender() {
            return gender;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;

            if (!(o instanceof Person))
                return false;

            Person p = (Person) o;
            return p.getId() == id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age, mbti, gender);
        }

        @Override
        public String toString() {
            return "id=" + id + ", name=" + name + ", age=" + age + ", mbti=" + mbti + ", gender=" + gender;
        }
    }

    public enum Gender {
        MALE, FEMALE
    }

    public class People {
        private List<Person> people;

        public People(List<Person> people) {
            this.people = people;
        }

        public List<Person> getPeople() {
            return people;
        }
    }
}