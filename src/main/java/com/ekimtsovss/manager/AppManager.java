package com.ekimtsovss.manager;

import com.ekimtsovss.exception.FileIOException;
import com.ekimtsovss.exception.ValidationException;
import com.ekimtsovss.print.ExceptionPrinter;
import com.ekimtsovss.processor.DataProcessor;
import com.ekimtsovss.processor.DataProcessorImpl;

/**
 * Application manager class responsible for coordinating the application workflow.
 * Handles initialization, execution, and error management.
 */
public class AppManager{

    private final DataProcessor dataProcessor;
    private final ExceptionPrinter exceptionPrinter;

    public AppManager() {
        this.dataProcessor = new DataProcessorImpl();
        this.exceptionPrinter = new ExceptionPrinter();

    }

    public AppManager(DataProcessor dataProcessor, ExceptionPrinter helpPrinter) {
        this.dataProcessor = dataProcessor;
        this.exceptionPrinter = helpPrinter;
    }
    public int run(String[] args){

        try {
            dataProcessor.execute(args);

        } catch (ValidationException e) {
            exceptionPrinter.printException(e);
            return 2;

        } catch (FileIOException e) {
            exceptionPrinter.printException(e);
            return 3;

        } catch (Exception e) {
            exceptionPrinter.printException(e);
            return 1;
        }
        return 0;
    }
}
