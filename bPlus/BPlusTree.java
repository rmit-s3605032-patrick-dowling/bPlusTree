package bPlus;

import java.util.ArrayList;

public class BPlusTree {

    public static final int Order = 3;
    private int depth = 0;
    private InternalNode rootNode = null;
    private LeafNode firstLeafNode;

    public LeafNode GetFirstLeaf() {
        return firstLeafNode;
    }

    // entry to linked list:
    public int getLeafCount(LeafNode firstLeaf)
    {
        LeafNode node = firstLeaf;
        int count = 0;
        while (node != null) {
            node = node.getNextNode();
            ++count;
        }
        return count;
    }

    public void setDepth() {
        Node currentNode = rootNode;
        int count = 0;
        while (currentNode.getPointerAt(0) != null) {
            currentNode = currentNode.getPointerAt(0);
            count++;
        }
        this.depth = count;
    }

    public int getDepth()
    {
        return this.depth;
    }

    public ArrayList<Index> EqualitySearch(String value) {

        try {

            System.out.println("Search term: " + value);

            long startTime = System.currentTimeMillis();

            InternalNode node = rootNode;
            int count = 0;
            int ios = 0;
            LeafNode bottom = null;

            while (true) {

//                System.out.println("Lvl: " + count);
//                node.printNode();

                int index = 0;

                for (var k : node.getKeys()) {
                    if (k == null) {
                        break;
                    }
                    if (k.compareTo(value) < 0) { // main comparison
                        ++ios;
                        ++index;
                    } else {
                        break;
                    }
                }

                try {
                    var next = node.getPointerAt(index);
                    node = (InternalNode)next; // iterates down the tree
                } catch (ClassCastException ex) {
                    bottom = (LeafNode)node.getPointerAt(index); // reached the bottom
                    break;
                }

                ++count;

            }

            if (bottom == null) {
                System.out.println("Search error");
                return null;
            }

//            bottom.printNode();
//            System.out.println("Bottom: " + count);

            var result = new ArrayList<Index>();
            count = 0;

            while (bottom != null) {

                for (var i : bottom.getIndex()) {

                    if (i.getField().compareTo(value) < 0) {
                        ++ios;
                        result.add(i);
                    } else {
                        System.out.println("Finished search. Leaves searched: " + count);
                        bottom = null;
                        break;
                    }
                }

                if (bottom == null) {
                    break;
                }

                bottom = bottom.getNextNode();
                ++count;
            }

            System.out.println("Finished search. Final Count: " + result.size());
//            System.out.println("Number of IO Operations: " + ios);

            long endTime = System.currentTimeMillis();
            System.out.println("Time Taken in Milliseconds " + (endTime - startTime));

            return result; // DONE

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Search error");
        return null; // error
    }

    public ArrayList<Index> RangeSearch(String left, String right) {

        try {

            System.out.println("Search term: Left: " + left + ", Right: " + right);

            if (left.compareTo(right) > 0) {
                System.out.println("Search error. Left is greater than right.");
                return null;
            }

            long startTime = System.currentTimeMillis();

            InternalNode node = rootNode;
            int count = 0;
            int ios = 0;
            LeafNode bottom = null;

            while (true) {

//                System.out.println("Lvl: " + count);
//                node.printNode();

                int index = 0;

                for (var k : node.getKeys()) {
                    if (k == null) {
                        break;
                    }
                    if (k.compareTo(left) < 0) { // main comparison
                        ++ios;
                        ++index;
                    } else {
                        break;
                    }
                }

                try {
                    var next = node.getPointerAt(index);
                    node = (InternalNode)next; // iterates down the tree
                } catch (ClassCastException ex) {
                    bottom = (LeafNode)node.getPointerAt(index); // reached the bottom
                    break;
                }

                ++count;

            }

            if (bottom == null) {
                System.out.println("Search error");
                return null;
            }

//            bottom.printNode();
//            System.out.println("Bottom: " + count);

            var result = new ArrayList<Index>();
            count = 0;

            while (bottom != null) {

                for (var i : bottom.getIndex()) {

                    if (i.getField().compareTo(right) < 0) {
                        ++ios;
                        result.add(i);
                    } else {
                        System.out.println("Finished search. Leaves searched: " + count);
                        bottom = null;
                        break;
                    }
                }

                if (bottom == null) {
                    break;
                }

                bottom = bottom.getNextNode();
                ++count;
            }

            System.out.println("Finished search. Final Count: " + result.size());
//            System.out.println("Number of IO Operations: " + ios);

            long endTime = System.currentTimeMillis();
            System.out.println("Time Taken in Milliseconds " + (endTime - startTime));

            return result; // DONE

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Search error");
        return null; // error

    }

    public void bulkLoad(ArrayList<Index> indexes) {

        System.out.println("Running BULK LOAD...");

        // runs merge sort to get all indexes in order
        System.out.println("Sorting...");

        ExternalMergeSort ems = new ExternalMergeSort(indexes);
        ems.sort(indexes, 0, indexes.size() - 1);

        var currentNode = new LeafNode();
        LeafNode firstLeaf = null;

        // loops through all values in the data store, and init the bottom leaves
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

        firstLeafNode = firstLeaf;
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


        /* used to set root to highest value. If multiple values on highest level, will build root 1 level
        higher and assign the lower level values as it's children */
        buildRoot(currentLevel);
        setDepth();
        System.out.println("Depth is: " + getDepth());
    }

    /* recursive function called when moving up a level. Will add the values of the previous Level as pointers
    * to the current level*/
    private Level increaseLevel(Level previousLevel)
    {
        Level currentLevel = previousLevel.getNextLevel();

        InternalNode iNode = new InternalNode();
        // sets both pointers and keys for the internal node
        if (previousLevel.getNodes().size() > 0)
        {
            iNode.setValues(previousLevel.getNodes());
        }
        currentLevel.addNode(iNode);

        // when the current level has order + 1 values, increase the level again
        if (currentLevel.isFull()) {
            if (currentLevel.getNextLevel() == null)
            {
                currentLevel.setNextLevel(new Level());
            }
            currentLevel = increaseLevel(currentLevel);
        }
        // called right before recurring back. Will reset the previous level
        previousLevel.wipe();

        previousLevel.setNextLevel(currentLevel);
        return previousLevel;
    }

    private void buildRoot(Level currentLevel) {

        // will keep going up until reaches highest level
        while (currentLevel.getNextLevel() != null)
        {
            currentLevel = currentLevel.getNextLevel();
        }

        ArrayList<Node> highestLevelNodes = new ArrayList<>(currentLevel.getNodes());

        /* if the highest level has multiple nodes, add another level and add those values as pointers
        then assigns that value as the root of the tree */
        if (highestLevelNodes.size() > 1) {

            InternalNode iNode = new InternalNode();

            iNode.setValues(currentLevel.getNodes());

            currentLevel.setNextLevel(new Level());
            currentLevel.getNextLevel().addNode(iNode);

            rootNode = iNode;
        }
        else
        {
            rootNode = (InternalNode)highestLevelNodes.get(0);
        }
    }
}
