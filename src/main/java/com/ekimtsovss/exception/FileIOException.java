package com.ekimtsovss.exception;

public class FileIOException extends Exception{

    public FileIOException(boolean isRead, String filename, Throwable cause ) {

        super((isRead ? "ERROR READING FILE: " : "ERROR WRITING FILE: ") + filename
                + "\n" + cause.getMessage());
    }
}

