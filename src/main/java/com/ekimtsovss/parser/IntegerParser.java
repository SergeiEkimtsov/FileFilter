package com.ekimtsovss.parser;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
/**
 * Parser implementation for integer data.
 * Filters and processes integer elements from input data.
 */
public class IntegerParser implements Parser{
    private final String REGEX = "(?<!\\.)[+-]?\\d+(?!\\.)";
    @Override
    public List<String> parse(List<String> data) {
        return data.stream()
                .filter(str->str.matches(REGEX))
                .toList();
    }
    @Override
    public void display(List<String> data, boolean shortMode, boolean fullMode) {
        if (data.isEmpty() && (shortMode || fullMode)){
            System.out.println("[NO FOUND DATA TYPE INTEGER]");
            return;
        }

        if (shortMode || fullMode) {
            System.out.println("[ INTEGER STATISTICS ]");
            System.out.println("Total: " + data.size());
        }

        if (fullMode) {

            Optional<Integer> min = data.stream().map(Integer::parseInt).min(Comparator.comparingInt(Integer::intValue));
            min.ifPresent(s -> System.out.println("Min: " + s));

            Optional<Integer> max = data.stream().map(Integer::parseInt).max(Comparator.comparingInt(Integer::intValue));
            max.ifPresent(s -> System.out.println("Max: " + s));

            Optional<Integer> sum = data.stream().map(Integer::parseInt).reduce(Integer::sum);
            sum.ifPresent(s -> {
                System.out.println("Sum: " + s);
                System.out.println("Average: " + s / data.size());
            });
        }
    }
}
