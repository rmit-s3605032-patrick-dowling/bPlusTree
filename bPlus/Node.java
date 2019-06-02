package bPlus;

public abstract class Node 
{
    public abstract String getFirstValue();

    public abstract Node getPointerAt(int index);

    public abstract boolean printNode();

    public abstract String getLastValue();
}