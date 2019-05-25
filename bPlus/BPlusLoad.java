package bPlus;

import data.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class BPlusLoad {

    private DataStore ds = new DataStore();

    public static void main(String[] args)
    {
        BPlusLoad bPlusLoad = new BPlusLoad();
        bPlusLoad.checkArgs(args);
    }

    private void checkArgs(String[] args)
    {
        int pageSize = 0;
        String datafileName = null;
        
        try
        {
            if (args[0].equals("-p"))
            {
                pageSize = Integer.parseInt(args[1]);
                datafileName = args[2];
            }
            else
            {
                invalidInput();
            }
        }
        catch (Exception e) {
            invalidInput();
        }

        writeData(pageSize, datafileName);

    }

    private void invalidInput()
    {
        System.out.println("Please enter the correct syntax: \njava bPlusLoad -p [pagesize] [datafile]");
        System.exit(1);
    }

    private void writeData(int pageSize, String dataFileName)
    {
        try 
        {
            int lineNumber = 0;
            int currentPageNumber = 1;
//            final int maxRecordSize = 309;
//            int pageOffset = 0;
//            String fileName = "heap." + pageSize;
            BufferedReader reader = new BufferedReader(new FileReader(new File(dataFileName)));
//            DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName));
            long startTime = System.currentTimeMillis();

            for (String data = reader.readLine(); data != null; data = reader.readLine(), lineNumber++)
            {
                // used to skip the line of headers
                if (lineNumber != 0)
                {
                    String[] dataSplit = data.split(",");

                    if (!dataSplit[3].equals("")){
                        var index = new Index(Long.parseLong(dataSplit[3]), 0, 0); //todo
                        ds.addIndex(index);
                    }

//                    DataEntry dataEntry = new DataEntry();
//
//                    createDataEntry(dataSplit, dataEntry, currentPageNumber, pageOffset);
//                    pageOffset += writeDataEntry(os, dataEntry, pageSize);
//
//                    if (maxRecordSize > pageSize)
//                    {
//                        // finish up page, add bytes to fill
//                        endPage(os, pageSize - pageOffset);
//                        ++currentPageNumber;
//
//                        pageOffset = 0;
//                    }

                    if (lineNumber % 50000 == 0) {
                        System.out.println("Processed " + lineNumber + " lines.");
                    }

                }
            }

            bulkLoad();
//            endPage(os, pageSize - pageOffset);
//            os.close();

            long endTime = System.currentTimeMillis();

            // prints statistics at the end.
            System.out.println("Number of Records Inserted: " + (lineNumber - 1));
            System.out.println("Number of Pages Used: " + currentPageNumber);
            System.out.println("Time Taken in Milliseconds " + (endTime - startTime));
        }
        catch (IOException ioe)
        {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    private void createDataEntry(String[] dataSplit, DataEntry dataEntry, int pageNumber, int recordOffset)
    {
        // add index to the datastore
        long durationSeconds = Long.parseLong(dataSplit[3]);
        ds.addIndex(new Index(durationSeconds, pageNumber, recordOffset));

        // set device ID
        dataEntry.setDeviceID(Integer.parseInt(dataSplit[0]));
        // set arrival time
        dataEntry.setArrivalTime(dataSplit[1]);
        // set departure time
        dataEntry.setDepartureTime(dataSplit[2]);
        // set duration seconds
        if (!dataSplit[3].equals("")){
            dataEntry.setDurationSeconds(Long.parseLong(dataSplit[3]));
        }
        // set street marker
        dataEntry.setStreetMarker(dataSplit[4]);
        // set sign
        dataEntry.setSign(dataSplit[5]);
        // set area
        dataEntry.setArea(dataSplit[6]);
        // set street id
        dataEntry.setStreetID(Integer.parseInt(dataSplit[7]));
        // set street name
        dataEntry.setStreetName(dataSplit[8]);
        // set between street 1
        dataEntry.setBetweenStreet1(dataSplit[9]);
        // set between street 2
        dataEntry.setBetweenStreet2(dataSplit[10]);
        // set side of street
        dataEntry.setSideOfStreet(Integer.parseInt(dataSplit[11]));
        // set in violation
        dataEntry.setInViolation(dataSplit[12].charAt(0));
    }

    private int writeDataEntry(OutputStream os, DataEntry dataEntry, int offset)
    {
        try
        {
            // writes each attribute with a field seperator
            offset += writeAttribute(os, dataEntry.getDeviceID());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getArrivalTime());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getDepartureTime());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getDurationSeconds());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getStreetMarker());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getSign());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getArea());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getStreetID());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getStreetName());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getBetweenStreet1());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getBetweenStreet2());
            offset += endOfString(os);

            offset += writeAttribute(os, dataEntry.getSideOfStreet());
            offset += endOfString(os);

            os.write(dataEntry.getInViolation());
            offset++;
            offset += endOfString(os);
        }


        catch (IOException ioe)
        {
            System.out.println("An error occurred");
            System.exit(1);
        }
        return offset;
    }

    // writes attribute and returns the size of the attribute.
    private int writeAttribute(OutputStream os, byte[] value) throws IOException
    {
        try
        {
            os.write(value);
            return value.length;
        }
        catch (NullPointerException npe)
        {
            return 0;
        } 
    }

    // adds # to end of string
    private int endOfString(OutputStream os) throws IOException
    {
        byte eosBytes = '#';
        os.write(eosBytes);
        return 1;
    }

    // prints $ to end of page.
    private void endPage(OutputStream os, int remainingBytes) throws IOException
    {
        byte[] eofBytes = new byte[remainingBytes];
        for (int i = 0; i < eofBytes.length; i++)
        {
            eofBytes[i] = '$';
        }
        os.write(eofBytes);
    }

    private void bulkLoad()
    {
        System.out.println("Running BULK LOAD...");

        ExternalMergeSort ems = new ExternalMergeSort(ds.getIndexes());
        // last value in the list to be sorted
        int rightmost = ds.getIndexes().size() - 1;

        System.out.println("First 10...");
        var indexes = ds.getIndexes();
        for (int i = 0; i < 10; ++i) {
            System.out.println(indexes.get(i).getDurationSeconds());
        }

        // runs merge sort to get all indexes in order
        System.out.println("Sorting...");
        ems.sort(indexes, 0, rightmost);

        System.out.println("First 10...");
        for (int i = 0; i < 10; ++i) {
            System.out.println(indexes.get(i).getDurationSeconds());
        }

        var bPlusTree = new BPlusTree();
        var node = bPlusTree.getFirstLeaf();

        int indexLoaded = 0;

        // loops through all values in the data store
        for (var index : indexes)
        {
            /* as the degree is the number of values the node can hold, this will calculate when a new leaf node is
            needed and is also used as the index for the array to insert the Node into*/
            int arrayIndex = indexLoaded++ % BPlusTree.Degree;
            if (arrayIndex == 0)
            {
                // points to the next node (singly linked list)
                var tempNode = node;
                node = new LeafNode();
                tempNode.setNextNode(node);
            }
            node.addValue(index, arrayIndex);
        }

        // rm empty node, from start:
        bPlusTree.setFirstLeaf(bPlusTree.getFirstLeaf().getNextNode());

        System.out.println("First 12...");
        node = bPlusTree.getFirstLeaf();
        for (int i = 0; i < 4; ++i) { // 4*3 = 12

            for (var val : node.getValues()) {
                System.out.println(val.getDurationSeconds());
            }

            node = node.getNextNode();

        }

        System.out.println("Leaf count: " + bPlusTree.getLeafCount());

    }
}