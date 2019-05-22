package bPlusTree.heapFile;

import bPlusTree.data.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class dbquery
{

    public static void main(String args[])
    {
        dbquery dbquery = new dbquery();
        dbquery.checkArgs(args);
    }

    public void checkArgs(String args[])
    {

        int pageSize = 0;
        String text = null;
        String type = "";
        
        try
        {
            type = args[0];
            text = args[1];
            pageSize = Integer.parseInt(args[2]);
        }
        catch (ArrayIndexOutOfBoundsException oobException)
        {
            invalidInput();
        }
        catch (NumberFormatException nfe)
        {
            invalidInput();
        }

        String fileName = "heap." + pageSize;

        if(type.equalsIgnoreCase("range") || type.equalsIgnoreCase("equality"))
        {
            queryHeap(type, text, fileName, pageSize);
        }
        else
        {
            invalidInput();
            System.exit(1);
        }
    }

    public void invalidInput()
    {
        System.out.println("Please enter the correct syntax: \njava dbquery [range/equality] [text] [pageSize]");
        System.out.println("Syntax for range should be as follows:");
        System.out.println("java dbquery range gt500 4096");
        System.out.println("java dbquery range lt500 4096\n");
        System.out.println("Where lt represents less than, gt represents greater than");
        System.out.println("Please keep in mind the application only indexes on the durationSeconds field");
        System.exit(1);
    }

    public void queryHeap(String type, String text, String fileName, int pageSize)
    {
        try
        {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName), pageSize);
            long startTime = System.currentTimeMillis();
            int pageNumber = 1;
            while( input.available() > 0) 
            {
                readInPage(input, pageNumber, type, pageSize, text);
                pageNumber++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Total Time of Query: " + (endTime - startTime));
        }
        catch (IOException ioe)
        {
            System.out.println("File does not exist");
            System.exit(1);
        }
        
    }

    public void readInPage(BufferedInputStream input, int pageNumber, String type, int pageSize, String text) throws IOException
    {
        byte[] line = input.readNBytes(pageSize);
        byte[] value = new byte[64];
        int valueNumber = 0;
        int countSinceLastValue = 0;
        Data data = new Data();
        // used to store all matches
        ArrayList<Data> matches = new ArrayList();
        // flag is just to prevent the query to be run multiple times when it is still gathering characters for deviceID.
        boolean flag = false;
        
        for (int i = 0; i < pageSize; i++)
        {
            // will query every 12 elements of the heap.
            if (valueNumber % 13 == 0 && valueNumber != 0 && flag == false)
            {
                matches = runQuery(matches, type, text, data);
                flag = true;
            }
            // will search for the last value of the line, otherwise keep appending to the value
            if (line[i] == '#')
            {
                countSinceLastValue = 0;
                data = retrieveValue(data, value, valueNumber);
                valueNumber++;
                value = emptyValue(value);
                flag = false;
            }
            // end of page
            else if (line[i] == '$')
            {
                break;
            }
            else
            {
                value[countSinceLastValue] = line[i];
                countSinceLastValue++;
            }
        }
    }

    public Data retrieveValue(Data data, byte[] value, int valueNumber)
    {
        ByteBuffer bb;
        switch (valueNumber % 13)
        {
            case 0:
                bb = ByteBuffer.wrap(value);
                int deviceID = bb.getInt();
                data.deviceID = deviceID;
                return data;

            case 1:
                data.arrivalTime = new String(value);
                return data;

            case 2:
                data.departureTime = new String(value);
                return data;

            case 3:
                bb = ByteBuffer.wrap(value);
                long durationSeconds = bb.getLong();
                data.durationSeconds = durationSeconds;
                return data;

            case 4:
                data.streetMarker = new String(value);
                return data;

            case 5:
                data.sign = new String(value);
                return data;

            case 6:     
                data.area = new String(value);
                return data;
            
            case 7:
                bb = ByteBuffer.wrap(value);
                int streetID = bb.getInt();
                data.streetID = streetID;
                return data;
            
            case 8:
                data.streetName = new String(value);
                return data;


            case 9:
                data.betweenStreet1 = new String(value);
                return data;


            case 10:
                data.betweenStreet2 = new String(value);
                return data;

            case 11:
                bb = ByteBuffer.wrap(value);
                int sideOfStreet = bb.getInt();
                data.sideOfStreet = sideOfStreet;
                return data;

            case 12:
                data.inViolation = (char) value[0];
                return data;

            default:
                return data;
        }
        
    }

    public byte[] emptyValue(byte[] value)
    {
        for (int i = 0; i < value.length; i++)
        {
            value[i] = ' ';
        }
        return value;
    }

    public ArrayList<Data> runQuery(ArrayList<Data> matches, String type, String text, Data data)
    {
        if (type.equalsIgnoreCase("range"))
        {
            return rangeQuery(matches, text, data);
        }
        else
        {
            return equalitySearch(matches, text, data);
        }
    }

    public ArrayList<Data> equalitySearch(ArrayList<Data> matches, String text, Data data)
    {
        String durationSeconds = String.valueOf(data.durationSeconds);
        
        if (durationSeconds.trim().equalsIgnoreCase(text.trim()))
        {
            matches.add(data);
        }
        return matches;
    }

    public ArrayList<Data> rangeQuery(ArrayList<Data> matches, String text, Data data)
    {
        long durationSeconds = data.durationSeconds;
        try
        {
            long userInput = (long) Long.parseLong(text);

            if (text.substring(0, 1).equalsIgnoreCase("gt"))
            {
                if (userInput > durationSeconds)
                {
                    matches.add(data);
                }
                else if (text.substring(0, 1).equalsIgnoreCase("lt"))
                {
                    if (userInput < durationSeconds)
                    {
                        matches.add(data);
                    }
                }
            }
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("Text submitted is not a long value.");
            System.exit(1);
        }
        return matches;
    }

    public void printDataEntry(Data d)
    {
        System.out.println("Device ID: " + d.deviceID);
        System.out.println("Arrival Time: " + d.arrivalTime);
        System.out.println("Departure Time: " + d.departureTime);
        System.out.println("Duration Seconds: " + d.durationSeconds);
        System.out.println("Street Marker: " + d.streetMarker);
        System.out.println("Sign: " + d.sign);
        System.out.println("Area: " + d.area);
        System.out.println("Street ID: " + d.streetID);
        System.out.println("Street Name: " + d.streetName);
        System.out.println("Between Street 1: " + d.betweenStreet1);
        System.out.println("Between Street 2: " + d.betweenStreet2);
        System.out.println("Side of Street: " + d.sideOfStreet);

        char inViolation = (char) d.inViolation;
        if (inViolation == 'T' || inViolation == 't' )
        {
            System.out.println("In Violation: True");
        }
        else
        {
            System.out.println("In Violation: False");
        }
    }

}