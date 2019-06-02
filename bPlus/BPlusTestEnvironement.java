package bPlus;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BPlusTestEnvironement {

    public static void main(String args[])
    {

        ArrayList<Index> indexes = new ArrayList<Index>();

        BPlusTree bPlus = new BPlusTree();

        bPlus.bulkLoad(indexes);

    }

}
