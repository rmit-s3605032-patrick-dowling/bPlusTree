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

        // init:
        rootNode = new InternalNode(firstLeaf, firstLeaf.getNextNode());
        var leaf = firstLeaf.getNextNode().getNextNode(); // third

        var currentlyFilling = rootNode;

        // ordered leaf loop:
        while (leaf != null) {

            if (currentlyFilling.IsFull()) { // split

                if (rootNode.IsFull()) {
                    var temp = new InternalNode(leaf, leaf.getNextNode());
                    rootNode = currentlyFilling.split(temp);
                    currentlyFilling = temp;
                    leaf = leaf.getNextNode();
                } else {
                    var temp = new InternalNode(leaf, leaf.getNextNode());
                    rootNode.add(currentlyFilling.split(temp));
                    currentlyFilling = temp;
                    leaf = leaf.getNextNode();
                }

            } else {
                currentlyFilling.add(leaf);
            }

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
            } catch (Exception ex) {
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
