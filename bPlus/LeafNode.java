package bPlus;

import java.util.ArrayList;

public class LeafNode extends Node {

    private LeafNode nextNode;

    ArrayList<Index> values = new ArrayList<Index>();

    public void printNode()
    {
        for (Index index : this.values)
        {
            System.out.println(index.getDurationSeconds());
        }
    }

    public Node getPointerAt(int index)
    {
        return null;
    }

    public void setNextNode(LeafNode nextNode)
    {
        this.nextNode = nextNode;
    }

    public LeafNode getNextNode()
    {
        return nextNode;
    }

    public ArrayList<Index> getIndex()
    {
        return values;
    }

    public void setIndex(ArrayList<Index>  index)
    {
        this.values = index;
    }

    public void addValue(Index value)
    {
        values.add(value);
    }

    @Override
    public long getFirstValue()
    {
        return values.get(0).getDurationSeconds();
    }

    public long getLastValue()
    {
        return values.get(values.size() - 1).getDurationSeconds();
    }
}