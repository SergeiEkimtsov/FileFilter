package com.ekimtsovss.processor;

import com.ekimtsovss.exception.FileIOException;
import com.ekimtsovss.exception.ValidationException;

public interface DataProcessor {

    /**
     * Processes data from command line arguments
     *
     * @param args arguments from command line
     * @throws ValidationException if arguments have incorrect format and value
     * @throws FileIOException if file does not exist or access
     */
    void execute(String[] args) throws ValidationException, FileIOException;

}
