package bPlus;

public class LeafNode extends Node {

    private LeafNode nextNode;
    Index[] values = new Index[BPlusTree.Order];

    public Node getPointerAt(int index)
    {
        return null;
    }

    private Index[] index = new Index[BPlusTree.Order];

    public void setValues(Index[] values)
    {
        this.values = values;
    }

    public void setNextNode(LeafNode nextNode)
    {
        this.nextNode = nextNode;
    }

    public LeafNode getNextNode()
    {
        return nextNode;
    }

    public Index[] getIndex()
    {
        return index;
    }

    public void setIndex(Index[] index)
    {
        this.index = index;
    }

    @Override
    public long getFirstValue()
    {
        return index[0].getDurationSeconds();
    }
}