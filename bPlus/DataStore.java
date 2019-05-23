package bPlus;

import java.util.ArrayList;

public class DataStore {

    private ArrayList<Index> indexes = new ArrayList<>();

    public ArrayList<Index> getIndexes() {
        return indexes;
    }

    // probably not needed, good to have
    public void setIndexes(ArrayList<Index> indexes) {
        this.indexes = indexes;
    }

    public void addIndex(Index index)
    {
        this.indexes.add(index);
    }
}
