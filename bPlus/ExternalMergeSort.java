package bPlus;

import java.util.ArrayList;

public class ExternalMergeSort {

    private ArrayList<Index> indexes;

    public ExternalMergeSort(ArrayList<Index> indexes)
    {
        this.indexes = indexes;
    }

    public void sort(ArrayList<Index> indexes, int leftmost, int rightmost)
    {
        if (leftmost < rightmost)
        {
            // Find the middle point
            int middleValue = (leftmost + rightmost) / 2;

            // Sort first and second halves
            sort(indexes, leftmost, middleValue);
            sort(indexes, middleValue + 1, rightmost);

            // Merge the sorted halves
            merge(leftmost, middleValue, rightmost);
        }
    }

    public void merge(int leftmost, int middle, int rightmost)
    {
        int leftSize = middle - leftmost + 1;
        int rightSize = rightmost - middle;

        Index leftArray[] = new Index[leftSize];
        Index rightArray[] = new Index[rightSize];

        // puts the values in their respective arrays left first then right
        for (int i=0; i < leftSize; i++)
        {
            leftArray[i] = indexes.get(leftmost + i);
        }
        for (int i=0; i < rightSize; i++)
        {
            rightArray[i] = indexes.get(middle + 1 + i);
        }

        // keeps track of the current merge index
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = leftmost;

        // handles both arrays concurrently while both still have values
        while ((leftIndex < leftSize) && (rightIndex < rightSize))
        {
            if (leftArray[leftIndex].getField().compareTo( rightArray[rightIndex].getField()) < 0)
            {
                indexes.set(mergeIndex, leftArray[leftIndex]);
                leftIndex++;
            }
            else
            {
                indexes.set(mergeIndex, rightArray[rightIndex]);
                rightIndex++;
            }
            mergeIndex++;
        }

        // handles left array if the right array finishes first
        while (leftIndex < leftSize)
        {
            indexes.set(mergeIndex, leftArray[leftIndex]);
            leftIndex++;
            mergeIndex++;
        }

        // handles right array if the left array finishes first
        while (rightIndex < rightSize)
        {
            indexes.set(mergeIndex, rightArray[rightIndex]);
            rightIndex++;
            mergeIndex++;
        }
    }


}