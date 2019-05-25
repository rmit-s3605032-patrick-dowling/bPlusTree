package bPlus;

public class BPlusTree
{
    public static final int Order = 3;

    public BPlusTree() {

    }

    private InternalNode rootNode;
    public InternalNode getRootNode() { return rootNode; }

    private LeafNode firstLeaf = new LeafNode();
    public LeafNode getFirstLeaf() { return firstLeaf; }
    public void setFirstLeaf(LeafNode leaf) { this.firstLeaf = leaf; }

    public int getLeafCount() {
        var node = firstLeaf;
        var count = 0;
        while (node != null) {
            node = node.getNextNode();
            ++count;
        }
        return count;
    }

}