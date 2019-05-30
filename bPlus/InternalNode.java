package bPlus;

import java.util.ArrayList;
import java.lang.Long;

public class InternalNode extends Node
{

    private ArrayList<Integer> keys = new ArrayList<Integer>();

    private ArrayList<Node> pointers = new ArrayList<Node>();

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
            keys.add(i - 1, this.pointers.get(i).getFirstValue());
        }
    }

    @Override
    public Integer getFirstValue()
    {
        return keys.get(0);
    }

}