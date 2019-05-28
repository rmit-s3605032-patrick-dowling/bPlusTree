package bPlus;

public abstract class Node 
{
    public abstract long getFirstValue();

    public abstract Node getPointerAt(int index);

    public abstract void printNode();
}