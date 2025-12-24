package com.ekimtsovss.args;

import com.ekimtsovss.exception.ValidationException;
import com.ekimtsovss.model.Arguments;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
/**
 * Parses command line arguments into Arguments object.
 */
public class ArgsParser implements ParserCLI{

    public Arguments parse(String[] args) throws ValidationException {
        if (args.length == 0) {
            throw new ValidationException(
                    """
                            Invalid argument: no arguments.
                            No input files specified.
                            Please provide at least one .txt file.
                            """
                    , ValidationException.ErrorType.NO_ARGUMENTS);
        }

        Arguments arguments = new Arguments();

        try {
            for (int i = 0; i < args.length; i++) {

                switch (args[i]) {
                    case "-a":
                        arguments.setModeFileWriting(true);
                        break;

                    case "-f":
                        arguments.setModeFullStatistic(true);
                        break;

                    case "-s":
                        arguments.setModeShortStatistic(true);
                        break;

                    case "-o":
                        validatePathToResults(args, i);
                        i++;
                        arguments.setPathToResults(args[i]);
                        break;

                    case "-p":
                        validateNameFileResult(args, i);
                        i++;
                        arguments.setNameFileResult(args[i]);
                        break;

                    default:
                        if (args[i].startsWith("-")) {
                            throw new ValidationException("Invalid argument: " + args[i]
                                    , ValidationException.ErrorType.UNKNOWN_OPTION);
                        }
                        while (i < args.length && !args[i].startsWith("-")) {
                            arguments.getListInputFiles().add(args[i]);
                            i++;
                        }
                        i--;
                        break;
                }
            }
            validateArgsWithNoPrefix(arguments.getListInputFiles());
            //    throw new IOException("!");
            return arguments;

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("Argument parsing error: " + e.getMessage()
                    , ValidationException.ErrorType.UNKNOWN_OPTION);
        }
    }

    /**
     * Validates output directory path.
     */
    private void validatePathToResults(String[] args, int i) throws ValidationException {

        if (i == args.length - 1 || args[i + 1].isBlank() || args[i + 1].startsWith("-")) {
            throw new ValidationException("Invalid  argument: value after '-o' must not be empty",
                    ValidationException.ErrorType.MISSING_VALUE);
        }
        String pathArg = args[i + 1];

        if (pathArg.contains("..")) {
            throw new ValidationException("Invalid  argument: value after '-o' must not contain '..'",
                    ValidationException.ErrorType.INVALID_VALUE);
        }
        if (pathArg.matches("(?i)^[a-z]:\\\\windows\\.*") ||
                pathArg.startsWith("/etc/") ||
                pathArg.startsWith("/bin/")) {
            throw new ValidationException("Invalid  argument: value after '-o' must not contain '" + pathArg + "'",
                    ValidationException.ErrorType.INVALID_VALUE);
        }

        Path path = Paths.get(pathArg);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);

                if (!Files.isDirectory(path)) {
                    throw new ValidationException("Path '" + path + "' is not a directory"
                            , ValidationException.ErrorType.INVALID_VALUE);
                }

                if (!Files.isWritable(path)) {
                    throw new ValidationException("No write permission for directory: " + path,
                            ValidationException.ErrorType.INVALID_VALUE);
                }

            } catch (IOException e) {
                throw new ValidationException("Failed to create/access directory '" + path + "': " + e.getMessage(),
                        ValidationException.ErrorType.INVALID_VALUE);
            }
        }
    }
    /**
     * Validates output file name prefix.
     */
    private void validateNameFileResult(String[] args, int i) throws ValidationException {
        if (i==args.length-1 || args[i+1].isBlank() || args[i+1].startsWith("-")) {
            throw  new ValidationException("Invalid  argument: value after '-p' must not be empty",
                    ValidationException.ErrorType.MISSING_VALUE);
        }
        String fileName = args[i + 1];
        String forbiddenCharacters =  "[.,/\\\\<>:;\"|?*]";

        if (fileName.matches(".*" + forbiddenCharacters + ".*") ||
                !fileName.matches("\\p{L}[\\p{L}\\d-]*")) {

            throw new ValidationException(
                    """
                     Invalid argument: value after '-p' must start with letter or letters/digits/dash
                     and must not contain characters [.,/\\<>:;\"|?*] 
                     """, ValidationException.ErrorType.INVALID_VALUE);
        }

    }
    /**
     * Validates input file arguments.
     */
    private void validateArgsWithNoPrefix(List<String> listArgs) throws ValidationException {
        if (listArgs.size()==0) {
            throw new ValidationException("""
                                             Invalid argument: no input file
                                             Please provide at least one .txt file.
                                             """, ValidationException.ErrorType.NO_ARGUMENTS);
        }
        for (String arg:listArgs){
            if (!arg.matches("\\p{L}*\\d*.txt")){
                throw new ValidationException("Invalid argument: '" + arg+ "'",
                        ValidationException.ErrorType.UNKNOWN_OPTION);
            }
        }
    }

}