package com.ekimtsovss.args;

import com.ekimtsovss.exception.ValidationException;
import com.ekimtsovss.model.Arguments;

public interface ParserCLI {
    /**
     * Parses command line arguments
     *
     * @param args arguments from command line
     * @return object Arguments encapsulates arguments
     * @throws ValidationException if arguments have incorrect format and value
     */
    Arguments parse(String[] args) throws ValidationException;
}
