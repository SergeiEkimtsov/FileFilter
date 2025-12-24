package com.ekimtsovss.processor;

import com.ekimtsovss.args.ArgsParser;
import com.ekimtsovss.args.ParserCLI;
import com.ekimtsovss.exception.FileIOException;
import com.ekimtsovss.exception.ValidationException;
import com.ekimtsovss.io.FileService;
import com.ekimtsovss.io.FileServiceImpl;
import com.ekimtsovss.model.Arguments;
import com.ekimtsovss.parser.FloatParser;
import com.ekimtsovss.parser.IntegerParser;
import com.ekimtsovss.parser.Parser;
import com.ekimtsovss.parser.StringParser;

import java.util.*;
/**
 * Implementation of DataProcessor for file data processing.
 */
public class DataProcessorImpl implements DataProcessor {
    private final ParserCLI argsParser;
    private FileService fileService;
    private final Map<String, Parser> parsers;

    public DataProcessorImpl() {

        this.argsParser = new ArgsParser();
        this.fileService = new FileServiceImpl();
        this.parsers = initializeParsers();

    }
    public DataProcessorImpl(ParserCLI argsParser, FileService fileService, Map<String, Parser> parsers) {
        this.argsParser = argsParser;
        this.fileService = fileService;
        this.parsers = parsers;
    }
    /**
     * Executes data processing pipeline.
     */
    @Override
    public void execute(String[] args) throws ValidationException, FileIOException {
        Arguments arguments = parseArguments(args);
        parseFiles(arguments);
    }
    /**
     * Parses command line arguments.
     */
    private Arguments parseArguments(String[] args) throws ValidationException {
        return argsParser.parse(args);
    }
    /**
     * Processes files using parsers.
     */
    private void parseFiles(Arguments arguments) throws FileIOException {

        List<String> resultFromReadingFiles = concatDataFromFiles(arguments);


        for (Map.Entry<String, Parser> entry:parsers.entrySet()){
            Parser parser = entry.getValue();
            /**
             * Parses data from file
             */
            List<String> resultParsing = parser.parse(resultFromReadingFiles);

            /**
             * Displays statistics
             */
            parser.display(resultParsing, arguments.getModeShortStatistic(), arguments.getModeFullStatistic());
            /**
             * Writes file
            */
            String outputNameFile = arguments.getNameFileResult() + entry.getKey()+".txt";
            fileService.write(resultParsing,
                    outputNameFile,
                    arguments.getPathToResults(),
                    arguments.getModeFileWriting()
            );
        }
    }
    /**
     * Reads and concatenates data from all input files.
     */
    private List<String> concatDataFromFiles (Arguments arguments) throws FileIOException {
        List<String> resultFromReadingFiles = new ArrayList<>();
        for (String file : arguments.getListInputFiles()) {
            var dataFromFile = fileService.read(file);
            if (!dataFromFile.isEmpty())
                resultFromReadingFiles.addAll(dataFromFile);
        }
        return resultFromReadingFiles;
    }
    /**
     * Initializes parsers map.
     */
    private Map<String, Parser> initializeParsers(){

        Map<String, Parser> parsers = new LinkedHashMap<>();
        parsers.put("string",new StringParser());
        parsers.put("integer", new IntegerParser());
        parsers.put("float", new FloatParser());

        return parsers;
    }
}