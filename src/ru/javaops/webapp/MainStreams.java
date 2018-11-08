package ru.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{9 , 8}));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((s1, s2) -> Integer.parseInt(String.valueOf(s1) + String.valueOf(s2)))
                .orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        long sum = integers.stream().mapToInt(Integer::intValue).sum();
        if (sum % 2 == 0) {
            return integers.stream().filter((s) -> s % 2 != 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter((s) -> s % 2 == 0).collect(Collectors.toList());
        }
    }
}
