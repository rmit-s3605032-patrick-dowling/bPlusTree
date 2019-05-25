package bPlus;

public abstract class Node 
{

    private Index[] values = new Index[BPlusTree.Degree];

    public void addValue(Index value, int index) {
        this.values[index] = value;
    }
    public Index[] getValues() {
        return values;
    }



}