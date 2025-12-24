package com.ekimtsovss.parser;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
/**
 * Parser implementation for float data.
 * Filters and processes float elements from input data.
 */
public class FloatParser implements Parser{
    private final String REGEX = "[-+]?\\d*\\.\\d+(?:[eE]?[-+]\\d+)?";
    @Override
    public List<String> parse(List<String> data) {
        return data.stream()
                .filter(str->str.matches(REGEX))
                .toList();
    }
    @Override
    public void display(List<String> data, boolean shortMode, boolean fullMode) {
        if (data.isEmpty() && (shortMode || fullMode)){
            System.out.println("[NO FOUND DATA TYPE FLOAT]");
            return;
        }

        if (shortMode || fullMode) {
            System.out.println("[ FLOAT STATISTICS ]");
            System.out.println("Total: " + data.size());
        }

        if (fullMode) {

            Optional<Double> min = data.stream().map(Double::parseDouble).min(Comparator.comparingInt(Double::intValue));
            min.ifPresent(s -> System.out.println("Min: " + s));

            Optional<Double> max = data.stream().map(Double::parseDouble).max(Comparator.comparingInt(Double::intValue));
            max.ifPresent(s -> System.out.println("Max: " + s));

            Optional<Double> sum = data.stream().map(Double::parseDouble).reduce(Double::sum);
            sum.ifPresent(s -> {
                System.out.printf("Sum: %.2f\n", s);
                System.out.printf("Average: %.2f\n", s / data.size());
            });
        }
    }
}
