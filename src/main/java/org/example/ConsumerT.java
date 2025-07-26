package org.example;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerT {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(3,5,2,1,-4,4,46,8,7,6,6,5,4,1,2,1);
        List<Integer> noDuplicatesNumbers = numbers.stream().distinct().toList();
        noDuplicatesNumbers.forEach(System.out::println);

        System.out.println("=== === === === === === === ===");
        List<Integer> limit = noDuplicatesNumbers.stream().limit(2).toList();
        limit.forEach(System.out::println);
        System.out.println("=== === === === === === === ===");
        List<Integer> skip = noDuplicatesNumbers.stream().skip(3).toList();
        skip.forEach(System.out::println);

        List<Integer> sort = numbers.stream().sorted(Integer::compareTo).toList();

        System.out.println("=== === === === === === === ===");
        sort.forEach(System.out::println);

        System.out.println("=== === === === === === === ===");

        // Desafio: somar todos os pares
        int sum = numbers.stream().mapToInt(i -> i).sum();
        System.out.println(sum);

        System.out.println("=== === === === === === === ===");

        // remover toodos os impares
        List<Integer> onlyEven = numbers.stream().filter(n -> n % 2 == 0).toList();
        onlyEven.forEach(System.out::println);

        System.out.println("=== === === === === === === ===");

        boolean allNumbersIsPositive = numbers.stream().noneMatch(i -> i< 0);
        System.out.println(allNumbersIsPositive);
        System.out.println("=== === === === === === === ===");

        List<Integer>  threeAndFive = numbers.stream().filter(n -> n % 3 == 0 && n % 5 == 0).toList();
    }
}
