package bPlus;

import java.util.ArrayList;
import data.*;

public class BPlusTree {
    public static final int Order = 3;

//    private int Depth = 1; // 1 = leaves

    public BPlusTree(DataStore ds) {
        bulkLoad(ds.getIndexes());
    }

    private Node rootNode = null;

    // entry to linked list:

    public int getLeafCount(LeafNode firstLeaf) {
        LeafNode node = firstLeaf;
        var count = 1;
        while (node != null) {
            node = node.getNextNode();
            ++count;
        }
        return count;
    }

    public int getDepth()
    {
        Node currentNode = rootNode;
        int count = 0;
        while(currentNode.getPointerAt(0) != null)
        {
            currentNode = currentNode.getPointerAt(0);
            count++;
        }
        return count;
    }

    public Index query(long val) {
        return null; //todo
    }

    private void bulkLoad(ArrayList<Index> indexes) {
        System.out.println("Running BULK LOAD...");

        ExternalMergeSort ems = new ExternalMergeSort(indexes);
        // last value in the list to be sorted
        int rightmost = indexes.size() - 1;

        // runs merge sort to get all indexes in order
        System.out.println("Sorting...");
        ems.sort(indexes, 0, rightmost);

        int count = 0;
        var currentValues = new Index[Order];
        var currentNode = new LeafNode();
        LeafNode firstLeaf = new LeafNode();

        // loops through all values in the data store, and init the bottom leaves:
        for (var index : indexes) {

            currentValues[count++] = index;

            if (count == Order) {
//                if (firstLeaf == null) {
//                    firstLeaf = new LeafNode(buffer);
//                    currentNode = firstLeaf;
//                    count = 0;
//                    continue;
//                }

                currentNode.setValues(currentValues);
                currentNode.setNextNode(new LeafNode());
                currentNode = currentNode.getNextNode();
                count = 0;
                // initialise the first leaf to become the root node temporarily
                if (rootNode == null) {
                    rootNode = currentNode;
                    firstLeaf = currentNode;
                }
            }
        }


        System.out.println("Leaf count: " + getLeafCount(firstLeaf));

        buildTree(firstLeaf);





        /*
         * The first step is to sort the data entries according to a search key in ascending order.
         * We allocate an empty page to serve as the root, and insert a pointer to the first page of entries into it.
         * When the root is full, we split the root, and create a new root page.
         * Keep inserting entries to the right most index page just above the leaf level, until all entries are indexed.
         *
         * When the right-most index page above the leaf level fills up, it is split;
         * This action may, in turn, cause a split of the right-most index page on step closer to the root;
         * Splits only occur on the right-most path from the root to the leaf level.
         */

        // init:
//        rootNode = new InternalNode(firstLeaf, firstLeaf.getNextNode());
//        var leaf = firstLeaf.getNextNode().getNextNode(); // third
//
//        var currentlyFilling = rootNode;
////        var top = currentlyFilling;
//        var endOfDepths = new ArrayList<InternalNode>();
//
//        // depth = size() - 1
//        endOfDepths.add(null);     // 0 - nothing
//        endOfDepths.add(null);     // 1 = leaves
//        endOfDepths.add(rootNode); // 2 = root


//
//        // ordered leaf loop:
//        while (true) {
//
//            if (currentlyFilling.IsFull()) {
//
//                InternalNode top = null;
//
//                for (var i = 2; i < endOfDepths.size(); ++i) {
//
//                    var old = endOfDepths.get(i);
//
//                    if (old.IsFull()) {
//
//                        if (i == 2) {
//                            top = new InternalNode(leaf, leaf.getNextNode());
//                            currentlyFilling = top;
//                            endOfDepths.set(2, currentlyFilling);
//                            rootNode = top.splitR(old);
//                            endOfDepths.add(rootNode);
//                        } else {
//
//                        }
//
//                    } else {
//                        // old is root
//                        old.add(top);
//                    }
//
////                    if (i == 2) {
////                        top = new InternalNode(leaf, leaf.getNextNode());
////                        currentlyFilling = top;
////
////                    } else {
////                        if (old.IsFull()) {
////                            top = top.splitR(old);
////                        } else {
////                            old.add(currentlyFilling);
////                            break;
////                        }
////                    }
//
//                }
//
//            } else {
//                currentlyFilling.add(leaf);
//            }
//
//            // iterate
//            if (leaf.getNextNode() == null) {
//                break;
//            } else {
//                leaf = leaf.getNextNode();
//            }
//        }


//        // TESTS:
//        Node node = rootNode;
//
//        while (true) {
//            ++count;
//            System.out.println(count);
//            try {
//                var next = (InternalNode) node;
//                node = next.getPointers()[1];
//            } catch (ClassCastException ex) {
//                var bottom = (LeafNode) node;
//                System.out.println(bottom.getFirstValue().getDurationSeconds());
//                break;
//            }
//        }
//
//        count = 1;
//        for (var v : rootNode.getPointers()[0].getValues()) {
//            System.out.println(count++ + " : " + v.getDurationSeconds());
//        }

    }

    // used to build the tree from the leave nodes up.
    private void buildTree(LeafNode currentNode)
    {
        Level currentLevel = new Level();
        currentLevel.height = 1;
        currentLevel.setNextLevel(new Level());
        while (currentNode != null)
        {
            for (int i = 0; i < currentLevel.getNodes().length; i++)
            {
                currentLevel.addValue(currentNode);
                currentNode = currentNode.getNextNode();
            }
            increaseLevel(currentLevel);
        }
        buildRoot(currentLevel);
    }

    private void increaseLevel(Level previousLevel)
    {
        previousLevel.getNextLevel().height = previousLevel.height + 1;
        System.out.println(previousLevel.height);
        InternalNode iNode = new InternalNode();
        iNode.setPointers(previousLevel.getNodes());
        iNode.setKeys();

        previousLevel.getNextLevel().addValue(iNode);

        if (previousLevel.getNextLevel().isFull())
        {
            increaseLevel(previousLevel.getNextLevel());
            previousLevel.getNextLevel().wipe();
        }
    }

    private void buildRoot(Level currentLevel)
    {
        while(currentLevel.getNextLevel() != null)
        {
            currentLevel = currentLevel.getNextLevel();
        }
        Node[] highestLevelNodes = currentLevel.getNodes();

        if (highestLevelNodes[1] != null)
        {
            InternalNode iNode = new InternalNode();

            iNode.setPointers(currentLevel.getNodes());
            iNode.setKeys();

            currentLevel.getNextLevel().addValue(iNode);

            rootNode = iNode;
        }
        else
        {
            rootNode = highestLevelNodes[0];
        }
    }

}



/*
* if (rootNode.IsFull()) { // re-split

    } else {
        var temp = currentlyFilling;
        currentlyFilling = new InternalNode(leaf, leaf.getNextNode());
        rootNode.add(temp.split(currentlyFilling));
    }

// skip one
leaf = leaf.getNextNode();
* */
