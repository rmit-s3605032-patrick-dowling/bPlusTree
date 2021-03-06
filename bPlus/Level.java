package bPlus;

import java.util.ArrayList;

public class Level
{
    private Level nextLevel = null;

    public int height;

    private ArrayList<Node> nodes = new ArrayList<Node>();

    public Boolean isFull()
    {
        if (nodes.size() == BPlusTree.Order + 1)
        {
            return true;
        }
        else
        {
            return false;
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
        this.nodes = new ArrayList<Node>();
    }

    public ArrayList<Node> getNodes()
    {
        return this.nodes;
    }

    public void addNode(Node node)
    {
        this.nodes.add(node);
    }

}