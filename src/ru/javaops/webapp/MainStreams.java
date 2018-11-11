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
        return (int) Arrays.stream(values)
                .distinct()
                .sorted()
                .mapToDouble(num -> (double) num)
                .reduce((s1, s2) -> (s1 + (s2 / 10)) * 10)
                .orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {


        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);
        return integers.stream().filter((s) -> {
            if (sum % 2 == 0 && s % 2 == 0) {
                return false;
            } else return sum % 2 == 0 || s % 2 == 0;
        }).collect(Collectors.toList());

//        if (sum % 2 == 0) {
//            return integers.stream().filter((s) -> s % 2 != 0).collect(Collectors.toList());
//        } else {
//            return integers.stream().filter((s) -> s % 2 == 0).collect(Collectors.toList());
//        }
    }
}
