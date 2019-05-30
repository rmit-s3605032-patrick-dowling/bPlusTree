package bPlus;

import java.util.ArrayList;

public class InternalNode extends Node
{
    private long[] keys = new long[BPlusTree.Order];

    private ArrayList<Node> pointers = new ArrayList<Node>();

    public ArrayList<Node> getPointers()
    {
        return pointers;
    }

    public  void printNode()
    {
        for (long key : this.keys)
        {
            System.out.println(key);
        }
    }

    public Node getPointerAt(int index)
    {
        if (index > pointers.size())
        {
            return null;
        }
        else {
            return pointers.get(index);
        }
    }

    public void setValues(ArrayList<Node> nodes)
    {
        this.pointers.addAll(nodes);

        for (int i = 1; i < this.pointers.size(); i++)
        {
            keys[i - 1] = pointers.get(i).getFirstValue();
        }
    }


    @Override
    public long getFirstValue()
    {
        return keys[0];
    }

}