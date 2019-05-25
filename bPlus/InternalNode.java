package bPlus;

public class InternalNode extends Node
{

    public boolean IsRoot = false;

    private Node[] pointers = new Node[BPlusTree.Order + 1];
//    private long[] keys = new long[BPlusTree.Order]; // use values

    public Node[] getPointers() { return pointers; }

    private int count = 1;

    public boolean IsFull() {
        return count >= BPlusTree.Order;
    }

    public InternalNode(Node left, Node right) {
        pointers[0] = left;
        values[0] = new Index(right.getFirstValue().getDurationSeconds(), 0, 0);
        pointers[count] = right;
    }

    public void add(Node node) {
        pointers[count] = node;
        values[count++] = new Index(node.getFirstValue().getDurationSeconds(), 0, 0);
    }

    public InternalNode split(Node right) {
        return new InternalNode(this, right);
    }

}