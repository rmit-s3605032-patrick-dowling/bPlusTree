package bPlus;

public class LeafNode extends Node {

    private LeafNode nextNode;

    public void setNextNode(LeafNode nextNode)
    {
        this.nextNode = nextNode;
    }

    public LeafNode getNextNode() {
        return nextNode;
    }

    private Index[] values = new Index[BPlusTree.Degree];

    public Index[] getValues() {
        return values;
    }

    public void addValue(Index value, int index) {
        this.values[index] = value;
    }

    public LeafNode() {

    }

}