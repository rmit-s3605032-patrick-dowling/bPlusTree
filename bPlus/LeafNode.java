package bPlus;

public class LeafNode extends Node {

    private LeafNode nextNode;

    public void setNextNode(LeafNode nextNode)
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

    public LeafNode() {

    }

}