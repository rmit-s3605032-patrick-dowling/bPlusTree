package bPlus;

public class Index
{
    // index key
    //todo
    private String durationSeconds;
    // page number it is stored in
    private int page;
    // offset of the page
    private int offset;

    public Index(String durationSeconds, int page, int offset)
    {
        //todo
        this.durationSeconds = durationSeconds;
        this.page = page;
        this.offset = offset;
    }

    //todo
    public String getDurationSeconds() {
        return durationSeconds;
    }
}