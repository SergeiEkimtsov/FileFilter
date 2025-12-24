package com.ekimtsovss.parser;

import java.util.List;

public interface Parser {

    /**
     * Parses data from input files
     *
     * @param data list of String from input files
     * @return list of String parsed files
     */
    List<String> parse(List<String> data);

    /**
     * Displays statistics parsed data
     *
     * @param data list of String parsed files
     * @param shortMode mode: if true return short statistics
     * @param fullMode mode: if true return full statistics

     */
    void display(List<String> data, boolean shortMode, boolean fullMode);
}
