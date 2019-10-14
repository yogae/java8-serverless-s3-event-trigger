package com.serverless.model;

public class S3Object {
    private String key;
    private int size;
    private String eTag;
    private String sequencer;

    public String getKey() {
        return key;
    }

    public String getSequencer() {
        return sequencer;
    }

    public String geteTag() {
        return eTag;
    }

    public int getSize() {
        return size;
    }

}
