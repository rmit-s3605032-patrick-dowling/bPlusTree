package bPlusTree.bPlus;

public class LeafNode extends Node {
    private Node nextNode;

    public void setNextNode(Node nextNode)
    {
        this.nextNode = nextNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    private Index[] values = new Index[3];

    public Index[] getValues() {
        return values;
    }

    public void addValue(Index value, int index) {
        this.values[index] = value;
    }


}