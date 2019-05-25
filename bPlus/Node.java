package bPlus;

public abstract class Node 
{

    private Index[] values = new Index[BPlusTree.Order];

    public void addValue(Index value, int index) {
        this.values[index] = value;
    }
    public Index[] getValues() {
        return values;
    }



}