package com.ekimtsovss.print;

import com.ekimtsovss.exception.FileIOException;
import com.ekimtsovss.exception.ValidationException;
/**
 * Handles printing of different types of exceptions with appropriate formatting.
 */
public class ExceptionPrinter {

    /**
     * Prints validation exceptions with help messages.
     */
    public void printException(ValidationException e) {
        System.err.println("[ERROR VALIDATION] " + e.getMessage());

        String message_MISSING_VALUE = "USAGE: java -jar util.jar -o value -p value FILE1.txt";
        String message_MISSING_REQUIRED_ARG = "USAGE: java -jar util.jar FILE1.txt";
        String message_UNKNOWN_OPTION = "USAGE: java -jar util.jar -a -s -f -o -p FILE1.txt";
        String message_FULL_HELP = """
                =====================[ HELP ]=====================          
                OPTIONS:
                -a              Append mode (default: overwrite)
                -s              Display short statistics
                -f              Display full statistics
                -o "directory"  Output directory for results
                -p "prefix"     Prefix for name output files
                        
                USAGE:          java -jar util.jar [OPTIONS...] FILE1.txt FILE2.txt ...
                EXAMPLES:       jar util.jar data.txt
                                java -jar util.jar -a -s -o results -p sample- data1.txt data2.txt
                """;
        switch (e.getErrorType()) {

            case NO_ARGUMENTS:
                System.err.println(message_FULL_HELP);
                break;

            case UNKNOWN_OPTION:
                System.err.println(message_UNKNOWN_OPTION);
                break;

            case MISSING_VALUE:
                System.err.println(message_MISSING_VALUE);
                break;

            case MISSING_REQUIRED_ARG:
                System.err.println(message_MISSING_REQUIRED_ARG);
                break;
        }
    }
    /**
     * Prints file I/O exceptions.
     */
    public void printException(FileIOException e){
        System.err.println("[ERROR IO] " + e.getMessage());

    }

    /**
     * Prints general exceptions with stack trace.
     */
    public void printException(Exception e){
        System.err.println("[UNEXPECTED ERROR] " +  e.getClass().getName());
        e.printStackTrace();
    }

    /**
     * Prints fatal errors/throwables.
     */
    public void printException(Throwable e){
        System.err.println("[FATAL ERROR] " + e.getClass().getName());
        e.printStackTrace();
    }

}
