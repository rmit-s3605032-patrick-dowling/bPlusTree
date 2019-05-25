package bPlus;

public abstract class Node 
{

    protected Index[] values = new Index[BPlusTree.Order];

    public Index[] getValues() {
        return values;
    }

    public Index getFirstValue() { return values[0]; }

}