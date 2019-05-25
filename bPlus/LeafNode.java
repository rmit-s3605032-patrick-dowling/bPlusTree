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

    public LeafNode(Index[] values) {
        this.values = values;
    }

}