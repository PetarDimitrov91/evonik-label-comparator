package org.evonik.models;

import org.apache.commons.csv.CSVRecord;

public abstract class Translation {
    private String key;
    private String contentEN;
    private String contentDE;
    private String contentIT;
    private String contentES;
    private String contentRU;
    private String contentTR;
    private String contentPL;
    private String contentHU;
    private String contentFR;
    private String contentJA;
    private String contentZH;
    private String contentKO;
    private String contentRO;
    private String contentCS;
    private String contentPT;

    public Translation(CSVRecord record, int startIndex) {
        this.key = record.get(startIndex);
        this.contentEN = record.get(startIndex + 1);
        this.contentDE = record.get(startIndex + 2);
        this.contentIT = record.get(startIndex + 3);
        this.contentES = record.get(startIndex + 4);
        this.contentRU = record.get(startIndex + 5);
        this.contentTR = record.get(startIndex + 6);
        this.contentPL = record.get(startIndex + 7);
        this.contentHU = record.get(startIndex + 8);
        this.contentFR = record.get(startIndex + 9);
        this.contentJA = record.get(startIndex + 10);
        this.contentZH = record.get(startIndex + 11);
        this.contentKO = record.get(startIndex + 12);
        this.contentRO = record.get(startIndex + 13);
        this.contentCS = record.get(startIndex + 14);
        this.contentPT = record.get(startIndex + 15);
    }

    public String getKey() {
        return key;
    }

    public String getContentEN() {
        return contentEN;
    }

    public String getContentDE() {
        return contentDE;
    }

    public String getContentIT() {
        return contentIT;
    }

    public String getContentES() {
        return contentES;
    }

    public String getContentRU() {
        return contentRU;
    }

    public String getContentTR() {
        return contentTR;
    }

    public String getContentPL() {
        return contentPL;
    }

    public String getContentHU() {
        return contentHU;
    }

    public String getContentFR() {
        return contentFR;
    }

    public String getContentJA() {
        return contentJA;
    }

    public String getContentZH() {
        return contentZH;
    }

    public String getContentKO() {
        return contentKO;
    }

    public String getContentRO() {
        return contentRO;
    }

    public String getContentCS() {
        return contentCS;
    }

    public String getContentPT() {
        return contentPT;
    }
}
