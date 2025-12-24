package com.ekimtsovss.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Data model representing command line arguments.
 * Contains all configuration parameters for file processing.
 */
public class Arguments {
    private String pathToResults;
    private String nameFileResult;
    private boolean modeFileWriting;
    private boolean modeShortStatistic;
    private boolean modeFullStatistic;
    private List<String> listInputFiles;

    public Arguments() {
        this.pathToResults = "";
        this.nameFileResult = "";
        this.modeFileWriting = false;
        this.modeShortStatistic = false;
        this.modeFullStatistic = false;
        this.listInputFiles = new ArrayList<>();
    }

    public String getPathToResults() {
        return pathToResults;
    }

    public void setPathToResults(String pathToResults) {
        this.pathToResults = pathToResults;
    }

    public String getNameFileResult() {
        return nameFileResult;
    }

    public void setNameFileResult(String nameFileResult) {
        this.nameFileResult = nameFileResult;
    }

    public boolean getModeFileWriting() {
        return modeFileWriting;
    }

    public void setModeFileWriting(boolean modeFileWriting) {
        this.modeFileWriting = modeFileWriting;
    }

    public boolean getModeShortStatistic() {
        return modeShortStatistic;
    }

    public void setModeShortStatistic(boolean modeShortStatistic) {
        this.modeShortStatistic = modeShortStatistic;
    }

    public boolean getModeFullStatistic() {
        return modeFullStatistic;
    }

    public void setModeFullStatistic(boolean modeFullStatistic) {
        this.modeFullStatistic = modeFullStatistic;
    }

    public List<String> getListInputFiles() {
        return listInputFiles;
    }

    public void setListInputFiles(List<String> listInputFiles) {
        this.listInputFiles = listInputFiles;
    }

}
