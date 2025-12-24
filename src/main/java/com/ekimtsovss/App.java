package com.ekimtsovss;

import com.ekimtsovss.manager.AppManager;
import com.ekimtsovss.print.ExceptionPrinter;
/**
 * Main application class for file handling and filtering
  */
public class App {
    private static final ExceptionPrinter PRINTER = new ExceptionPrinter();
    /**
     * Main entry point for the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try {
            AppManager application = new AppManager();
            int exitCode = application.run(args);
            System.exit(exitCode);

        } catch (Throwable e){
            PRINTER.printException(e);
            System.exit(1);
        }
    }
}

