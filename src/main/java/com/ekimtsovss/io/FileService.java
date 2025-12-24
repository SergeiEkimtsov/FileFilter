package com.ekimtsovss.io;

import com.ekimtsovss.exception.FileIOException;
import java.util.List;

public interface FileService {

    /**
     * Reads data from input files
     *
     * @param path path to the file for reading
     * @return list of String from input files
     * @throws FileIOException if file does not exist or access
     */
    List<String> read(String path) throws FileIOException;

    /**
     * Writes parsed data
     *
     * @param list list of String parsed files
     * @param nameFile name for output file
     * @param pathToResult path to the output file for writing
     * @param mode mode writing: true- append date, false - overwrite data
     * @throws FileIOException if file does not exist or access
     */
    void write(List<String> list, String nameFile, String pathToResult, boolean mode) throws FileIOException;
}
