package bPlus;

import java.util.ArrayList;
import java.lang.Long;

public class InternalNode extends Node
{

    private long[] keys = new long[BPlusTree.Order];

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
            // used to catch array out of bounds error for when there are no children.
            try
            {
                keys[i - 1] = this.pointers.get(i).getFirstValue();
            }
            catch (IndexOutOfBoundsException iobe)
            {
                keys[i - 1] = this.pointers.get(i - 1).getLastValue();
            }
        }
    }

    @Override
    public long getFirstValue()
    {
        if (keys[0] == 0)
        {
            System.out.println("Someone fucked up");
        }
        return keys[0];
    }

    public long getLastValue()
    {
        for (int i = keys.length; i > 0; i--)
        {
            if (keys[i] != 0)
            {
                return keys[i];
            }
        }
        return 0;
    }



}