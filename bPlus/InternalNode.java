package bPlus;

public class InternalNode extends Node
{

    private long[] keys = new long[BPlusTree.order];

    private Node[] pointers = new Node[BPlusTree.Order + 1];

    public Node[] getPointers()
    {
        return pointers;
    }

    public void setPointers(Nodes[] values)
    {
        this.values = values;
    }

    @Override
    public long getFirstValue()
    {
        return keys[0];
    }

    public void addKey(long key)
    {
        for (int i = 0; i < keys.length; i++)
        {
            if (keys[i] == null)
            {
                keys[i] = key;
            }
        }
    }

    public void addPointer(Nodes pointer)
    {
        for (int i = 0; i < pointers.length; i++)
        {
            if (pointers[i] == null)
            {
                pointers[i] = pointer;
            }
        }
    }

    public void setKeys()
    {
        for (int i = 1; i < pointers.length; i++)
        {
            this.keys[i - 1] = this.pointers[i].getFirstValue();
        }
    }

//    private int count = 1;

//    public boolean IsFull()
//    {
//        return count >= BPlusTree.Order;
//    }
//
//    public InternalNode(Node left, Node right)
//    {
//        pointers[0] = left;
//        values[0] = new Index(right.getFirstValue().getDurationSeconds(), 0, 0);
//        pointers[count] = right;
//    }
//
//    public void add(Node node)
//    {
//        pointers[count] = node;
//        values[count++] = new Index(node.getFirstValue().getDurationSeconds(), 0, 0);
//    }
//
//    public InternalNode split(Node right)
//    {
//        return new InternalNode(this, right);
//    }
//
//    public InternalNode splitR(Node left)
//    {
//        return new InternalNode(this, left);
//    }



}