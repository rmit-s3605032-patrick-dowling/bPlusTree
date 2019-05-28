package bPlus;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BPlusTestEnvironement {

    public static void main(String args[])
    {
        ArrayList<Index> arrayList = new ArrayList<Index>();

        arrayList.add(new Index(15,0,0));
        arrayList.add(new Index(20,0,0));
        arrayList.add(new Index(25,0,0));
        arrayList.add(new Index(30,0,0));
        arrayList.add(new Index(40,0,0));
        arrayList.add(new Index(50,0,0));
        arrayList.add(new Index(60,0,0));
        arrayList.add(new Index(70,0,0));
        arrayList.add(new Index(80,0,0));
        arrayList.add(new Index(90,0,0));
        arrayList.add(new Index(11,0,0));
        arrayList.add(new Index(12,0,0));
        arrayList.add(new Index(15,0,0));
        arrayList.add(new Index(14,0,0));
        arrayList.add(new Index(17,0,0));
        arrayList.add(new Index(18,0,0));
        arrayList.add(new Index(14,0,0));
        arrayList.add(new Index(16,0,0));
        arrayList.add(new Index(13,0,0));
        arrayList.add(new Index(0,0,0));
        arrayList.add(new Index(3,0,0));
        arrayList.add(new Index(1111,0,0));
        arrayList.add(new Index(1234,0,0));
        arrayList.add(new Index(123,0,0));
        arrayList.add(new Index(136,0,0));
        arrayList.add(new Index(368,0,0));
        arrayList.add(new Index(653,0,0));
        arrayList.add(new Index(2464,0,0));

        DataStore ds = new DataStore();

        ds.setIndexes(arrayList);

        BPlusTree bPlus = new BPlusTree(ds);

    }

}
