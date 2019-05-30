package bPlus;

import java.util.ArrayList;
import data.*;

public class BPlusTree {
    public static final int Order = 3;

//    private int Depth = 1; // 1 = leaves

    private Node rootNode = null;

    // entry to linked list:

    public int getLeafCount(LeafNode firstLeaf) {
        LeafNode node = firstLeaf;
        var count = 0;
        while (node != null) {
            node = node.getNextNode();
            ++count;
        }
        return count;
    }

    public int getDepth() {
        Node currentNode = rootNode;
        int count = 0;
        while (currentNode.getPointerAt(0) != null) {
            currentNode = currentNode.getPointerAt(0);
            count++;
        }
        return count;
    }

    public Index query(long val) {
        return null; //todo
    }

    public void bulkLoad(ArrayList<Index> indexes) {

        System.out.println("Running BULK LOAD...");



        // runs merge sort to get all indexes in order
        System.out.println("Sorting...");

        ExternalMergeSort ems = new ExternalMergeSort(indexes);
        ems.sort(indexes, 0, indexes.size() - 1);

        var currentNode = new LeafNode();
        LeafNode firstLeaf = null;

        // loops through all values in the data store, and init the bottom leaves:
        for (Index index : indexes)
        {
            currentNode.addValue(index);
            /* as the order is the number of values the node can hold, this will calculate when a new leaf node is
            needed and is also used as the index for the array to insert the Node into*/
            if (currentNode.getIndex().size() == BPlusTree.Order)
            {
                // points to the next node (singly linked list)
                currentNode.setNextNode(new LeafNode());
                if (firstLeaf == null)
                {
                    firstLeaf = currentNode;
                }
                currentNode = currentNode.getNextNode();
            }
        }

        System.out.println("Leaf count: " + getLeafCount(firstLeaf));

        Level currentLevel = new Level();

        buildTree(firstLeaf, currentLevel);
    }

    // used to build the tree from the leave nodes up.
    private void buildTree(LeafNode currentNode, Level currentLevel) {
        while (currentNode != null)
        {
            do {
                if (currentNode == null)
                {
                    break;
                }
                currentLevel.addNode(currentNode);
                currentNode = currentNode.getNextNode();
            }
            while (currentLevel.getNodes().size() < BPlusTree.Order + 1);
            if (currentLevel.getNextLevel() == null)
            {
                currentLevel.setNextLevel(new Level());
            }
            currentLevel = increaseLevel(currentLevel);
        }
        buildRoot(currentLevel);
        System.out.println("Depth is: " + getDepth());
    }

    private Level increaseLevel(Level previousLevel)
    {
        Level currentLevel = previousLevel.getNextLevel();
        InternalNode iNode = new InternalNode();
        iNode.setValues(previousLevel.getNodes());
        currentLevel.addNode(iNode);

        if (currentLevel.isFull()) {
            if (currentLevel.getNextLevel() == null)
            {
                currentLevel.setNextLevel(new Level());
            }
            currentLevel = increaseLevel(currentLevel);
        }
        previousLevel.wipe();

        previousLevel.setNextLevel(currentLevel);
        return previousLevel;
    }

    private void buildRoot(Level currentLevel) {

        while (currentLevel.getNextLevel() != null)
        {
            currentLevel = currentLevel.getNextLevel();
        }

        ArrayList<Node> highestLevelNodes = new ArrayList<Node>();

        for (Node node : currentLevel.getNodes())
        {
            highestLevelNodes.add(node);
        }

        if (highestLevelNodes.size() > 1) {
            InternalNode iNode = new InternalNode();

            iNode.setValues(currentLevel.getNodes());

            currentLevel.setNextLevel(new Level());
            currentLevel.getNextLevel().addNode(iNode);

            rootNode = iNode;
        }
        else
        {
            rootNode = highestLevelNodes.get(0);
        }
    }
}