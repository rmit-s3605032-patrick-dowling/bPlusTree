package bPlus;

public abstract class Node 
{
    public abstract Integer getFirstValue();

    public abstract Node getPointerAt(int index);

    public abstract void printNode();
}