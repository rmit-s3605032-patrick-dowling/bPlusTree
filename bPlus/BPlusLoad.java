package bPlus;

import data.*;

import java.io.*;

public class BPlusLoad {

    private static DataStore ds = new DataStore();

    public static void main(String[] args)
    {
        BPlusLoad bPlusLoad = new BPlusLoad();
        bPlusLoad.checkArgs(args);

        System.out.println("Building tree...");

        var btree = new BPlusTree();
        btree.bulkLoad(ds.getIndexes());

        System.out.println("Equality search...");
        btree.EqualitySearch("0");

        System.out.println("Range search...");
        btree.RangeSearch("0", "1");

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
            final int maxRecordSize = 309;
            int pageOffset = 0;
            String fileName = "heap." + pageSize;
            BufferedReader reader = new BufferedReader(new FileReader(new File(dataFileName)));
            DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName));
            long startTime = System.currentTimeMillis();

            for (String data = reader.readLine(); data != null; data = reader.readLine(), lineNumber++)
            {
                // used to skip the line of headers
                if (lineNumber != 0)
                {
                    String[] dataSplit = data.split(",");

                    if (!dataSplit[3].equals("")){
                        var index = new Index(dataSplit[4], 0, 0); // [4] == StreetMarker
                        ds.addIndex(index);
                    }

                    DataEntry dataEntry = new DataEntry();

                    createDataEntry(dataSplit, dataEntry, currentPageNumber, pageOffset);
                    pageOffset += writeDataEntry(os, dataEntry, pageSize);

                    if (maxRecordSize > pageSize)
                    {
                        // finish up page, add bytes to fill
                        endPage(os, pageSize - pageOffset);
                        ++currentPageNumber;

                        pageOffset = 0;
                    }

                    if (lineNumber % 50000 == 0) {
                        System.out.println("Processed " + lineNumber + " lines.");
                    }

                }
            }

            endPage(os, pageSize - pageOffset);
            os.close();


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
        // add index to the data store
        var field = dataSplit[4]; // [4] == StreetMarker
        ds.addIndex(new Index(field, pageNumber, recordOffset));

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

}
