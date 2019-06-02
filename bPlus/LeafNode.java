package bPlus;

import java.util.ArrayList;

public class LeafNode extends Node {

    private LeafNode nextNode;
    private ArrayList<Index> values = new ArrayList<>();

    public boolean printNode()
    {
        for (Index index : this.values)
        {
            System.out.println(index.getField());
        }
        return true;
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
    public String getFirstValue()
    {
        return values.get(0).getField();
    }

    public String getLastValue()
    {
        return values.get(values.size() - 1).getField();
    }
}