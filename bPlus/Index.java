package bPlusTree.bPlus;

import bPlusTree.data.*;

public class Index
{
    // index key
    private byte[] durationSeconds;
    // page number it is stored in
    private int page;
    // offset of the page
    private int offset;


    public Index(byte[] durationSeconds, int page, int offset)
    {
        this.durationSeconds = durationSeconds;
        this.page = page;
        this.offset = offset;
    }
}