package com.ekimtsovss.parser;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
/**
 * Parser implementation for string data.
 * Filters and processes string elements from input data.
 */
public class StringParser implements Parser{
    private final String REGEX = "[\\p{L}\\-\\s]+";
    @Override
    public List<String> parse(List<String> data) {
        return data.stream()
                .filter(str->str.matches(REGEX))
                .toList();
    }
    @Override
    public void display(List<String> data, boolean shortMode, boolean fullMode) {
        if (data.isEmpty() && (shortMode || fullMode)){
            System.out.println("[NO FOUND DATA TYPE STRING]");
            return;
        }

        if (shortMode || fullMode){
            System.out.println("[ STRING STATISTICS ]");
            System.out.println("Total: " + data.size());
        }

        if (fullMode) {

            Optional<String> min = data.stream().min(Comparator.comparingInt(String::length));
            min.ifPresent(s -> System.out.println("Shorter length: " + s.length()));

            Optional<String> max = data.stream().max(Comparator.comparingInt(String::length));
            max.ifPresent(s -> System.out.println("Longest length: " + s.length()));
        }
    }
}
