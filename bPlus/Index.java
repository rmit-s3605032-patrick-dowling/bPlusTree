package bPlus;

public class Index
{
    // index key
    private int durationSeconds;
    // page number it is stored in
    private int page;
    // offset of the page
    private int offset;

    public Index(int durationSeconds, int page, int offset)
    {
        this.durationSeconds = durationSeconds;
        this.page = page;
        this.offset = offset;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}