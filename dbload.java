import java.lang.IndexOutOfBoundsException;
import java.lang.NumberFormatException;
import java.nio.ByteBuffer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class dbload {
    public static void main(String args[])
    {
        dbload dbload = new dbload();
        dbload.checkArgs(args);
    }

    public void checkArgs(String args[])
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
        catch (ArrayIndexOutOfBoundsException oobException)
        {
            invalidInput();
        }
        catch (NumberFormatException nfe)
        {
            invalidInput();
        }

        writeData(pageSize, datafileName);

    }

    public void writeData(int pageSize, String  datafileName)
    {
        try 
        {
            int lineNumber=0;
            int currentPageNumber = 1;
            final int maxRecordSize = 309;
            int bytesSinceLastPage = 0;
            String fileName = "heap." + pageSize;
            BufferedReader reader = new BufferedReader(new FileReader(new File(datafileName)));
            DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName));
            long startTime = System.currentTimeMillis();

            for (String data = reader.readLine(); data != null; data = reader.readLine(), lineNumber++)
            {
                // used to skip the line of headers
                if (lineNumber == 0)
                {
                    continue;
                }
                else
                {
                    String[] dataSplit = data.split(",");
                    
                    DataEntry dataEntry = new DataEntry();
                    
                    createDataEntry(dataSplit, dataEntry);
                    bytesSinceLastPage += writeDataEntry(os, dataEntry, pageSize);

                    if (bytesSinceLastPage +  maxRecordSize > pageSize)
                    {
                        // finish up page, add bytes to fill
                        endPage(os, pageSize - bytesSinceLastPage);
                        ++currentPageNumber; 
                        
                        bytesSinceLastPage = 0;
                    }
                    
                }
            }
            endPage(os, pageSize - bytesSinceLastPage);
            os.close();
            long endTime = System.currentTimeMillis();
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

    public void invalidInput()
    {
        System.out.println("Please enter the correct syntax: \njava dbload -p [pagesize] [datafile]");
        System.exit(1);
    }

    public void createDataEntry(String[] dataSplit, DataEntry dataEntry)
    {
        // set device ID
        dataEntry.setDeviceID(Integer.parseInt(dataSplit[0]));
        // set arrival time
        dataEntry.setArrivalTime(dataSplit[1]);
        // set departure time
        dataEntry.setDepartureTime(dataSplit[2]);
        // set duration seconds
        if (dataSplit[3] != ""){
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

    public int writeDataEntry(OutputStream os, DataEntry dataEntry, int pageSize)
    {
        int recordSize = 0;
        try
        {           

            recordSize += writeAttribute(os, dataEntry.getDeviceID());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getArrivalTime());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getDepartureTime());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getDurationSeconds());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getStreetMarker());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getSign());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getArea());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getStreetID());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getStreetName());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getBetweenStreet1());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getBetweenStreet2());
            recordSize += endOfString(os);

            recordSize += writeAttribute(os, dataEntry.getSideOfStreet());
            recordSize += endOfString(os);

            os.write(dataEntry.getInViolation());
            recordSize++;
            recordSize += endOfString(os);
        }
        catch (IOException ioe)
        {
            System.out.println("An error occurred");
            System.exit(1);
        }

        return recordSize;
    }

    public int writeAttribute(OutputStream os, byte[] value) throws IOException
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

    public int endOfString(OutputStream os) throws IOException
    {
        byte eosBytes = '#';
        os.write(eosBytes);
        return 1;
    }

    public void endPage(OutputStream os, int remainingBytes) throws IOException
    {
        byte[] eofBytes = new byte[remainingBytes];
        for (int i = 0; i < eofBytes.length; i++)
        {
            eofBytes[i] = '$';
        }
        os.write(eofBytes);
    }

    

}