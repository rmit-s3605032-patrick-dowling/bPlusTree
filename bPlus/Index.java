package bPlus;

public class Index
{
    // index key
    //todo
    private String field;
    // page number it is stored in
    private int page;
    // offset of the page
    private int offset;

    public Index(String field, int page, int offset)
    {
        //todo
        this.field = field;
        this.page = page;
        this.offset = offset;
    }

    //todo
    public String getField() {
        return field;
    }
}