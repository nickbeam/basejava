package ru.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
    }

    private static int minValue(int[] values) {
        return  Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((s1, s2) -> s1 * 10 + s2)
                .orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().filter(s -> s % 2 != sum % 2).collect(Collectors.toList());
    }
}
