package bPlus;

public class Index
{
    // index key
    private long durationSeconds;
    // page number it is stored in
    private int page;
    // offset of the page
    private int offset;

    public Index(long durationSeconds, int page, int offset)
    {
        this.durationSeconds = durationSeconds;
        this.page = page;
        this.offset = offset;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }
}