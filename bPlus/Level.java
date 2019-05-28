package bPlus;

public class Level
{
    private Level nextLevel;


    //TODO
    public int height;

    private Node[] nodes = new Node[BPlusTree.Order];

    public Boolean isFull()
    {
        if (this.nodes[BPlusTree.Order - 1] != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void addValue(Node node)
    {
        for (int i = 0; i < nodes.length; i++)
        {
            if (this.nodes[i] == null)
            {
                this.nodes[i] = node;
            }
        }
    }

    public Level getNextLevel()
    {
        return this.nextLevel;
    }

    public void setNextLevel(Level nextLevel)
    {
        this.nextLevel = nextLevel;
    }

    public void wipe()
    {
        this.nodes = new Node[BPlusTree.Order];
    }

    public Node[] getNodes()
    {
        return this.nodes;
    }

}