package bPlus;

public class InternalNode extends Node
{

    public boolean IsRoot = false;

    private Node[] pointers = new Node[BPlusTree.Degree + 1];

    public InternalNode() {

    }

}