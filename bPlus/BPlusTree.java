package bPlus;

import java.util.ArrayList;

public class BPlusTree
{
    public static final int Order = 3;

    public BPlusTree(DataStore ds) {
        bulkLoad(ds.getIndexes());
    }

    private InternalNode rootNode;

    // entry to linked list:
    private LeafNode firstLeaf;

    public int getLeafCount() {
        var node = firstLeaf;
        var count = 0;
        while (node != null) {
            node = node.getNextNode();
            ++count;
        }
        return count;
    }

    public Index query(long val) {
        return null; //todo
    }

    private void bulkLoad(ArrayList<Index> indexes)
    {
        System.out.println("Running BULK LOAD...");

        ExternalMergeSort ems = new ExternalMergeSort(indexes);
        // last value in the list to be sorted
        int rightmost = indexes.size() - 1;

        // runs merge sort to get all indexes in order
        System.out.println("Sorting...");
        ems.sort(indexes, 0, rightmost);

        byte count = 0;
        var buffer = new Index[Order];
        var currentNode = firstLeaf;

        // loops through all values in the data store, and init the bottom leaves:
        for (var index : indexes) {

            buffer[count++] = index;

            if (count == Order) {

                if (firstLeaf == null) {
                    firstLeaf = new LeafNode(buffer);
                    currentNode = firstLeaf;
                    count = 0;
                    continue;
                }

                currentNode.setNextNode(new LeafNode(buffer));
                currentNode = currentNode.getNextNode();
                count = 0;
            }
        }

        System.out.println("Leaf count: " + getLeafCount());

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
        rootNode = new InternalNode(firstLeaf, firstLeaf.getNextNode());
        var leaf = firstLeaf.getNextNode().getNextNode(); // third

        var currentlyFilling = rootNode;

        // ordered leaf loop:
        while (true) {

            if (rootNode.IsFull()) { // split

                if (currentlyFilling.IsFull()) { // this is only once at start, when currentlyFilling == rootNode

                    currentlyFilling = new InternalNode(leaf, leaf.getNextNode());
                    rootNode = rootNode.split(currentlyFilling);
                    leaf = leaf.getNextNode(); // skip one

                } else {
                    currentlyFilling.add(leaf);
                }

            } else {

                if (currentlyFilling.IsFull()) { // split

                    rootNode.add(currentlyFilling.split(leaf));

                } else {
                    currentlyFilling.add(leaf);
                }

            }

            // iterate
            if (leaf.getNextNode() == null) {
                break;
            } else {
                leaf = leaf.getNextNode();
            }
        }


        // TESTS:
        Node node = rootNode;

        while (true) {
            ++count;
            System.out.println(count);
            try {
                var next = (InternalNode)node;
                node = next.getPointers()[1];
            } catch (ClassCastException ex) {
                var bottom = (LeafNode)node;
                System.out.println(bottom.getFirstValue().getDurationSeconds());
                break;
            }
        }

        count = 1;
        for (var v : rootNode.getPointers()[0].getValues()) {
            System.out.println(count++ + " : " + v.getDurationSeconds());
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
