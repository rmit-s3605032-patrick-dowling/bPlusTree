package bPlus;

import java.util.ArrayList;
import java.lang.Long;

public class InternalNode extends Node
{
    private String[] keys = new String[BPlusTree.Order];
    private ArrayList<Node> pointers = new ArrayList<Node>();

    public boolean printNode()
    {
        for (String key : this.keys)
        {
            System.out.println(key);
        }
        return false;
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
    public String getFirstValue()
    {
        if (keys[0] == null)
        {
            System.out.println("First Key error");
        }
        return keys[0];
    }

    public String getLastValue()
    {
        for (int i = keys.length - 1; i > 0; i--)
        {
            if (keys[i] != null)
            {
                return keys[i];
            }
        }
        System.out.println("Last Key error");
        return null;
    }

}
