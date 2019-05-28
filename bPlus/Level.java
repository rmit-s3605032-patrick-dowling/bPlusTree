public class Level
{
    private Level nextLevel;

    public int height;

    private Node[] nodes = new Node[BPlusTree.degree];

    public Boolean isFull()
    {
        if (this.nodes[BPlusTree.degree - 1] != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean addValue(Node node)
    {
        for (int i = 0; i < nodes.length)
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
        this.nodes = new Node[BPlusTree.degree];
    }

    public void getNodes()
    {
        return this.nodes;
    }
}