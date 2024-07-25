package edu.curtin.comp3003.comparator;

public class Contents {
    private final String filename;
    private final char[] contents;

    public Contents(String filename, char[] contents) {
        this.filename = filename;
        this.contents = contents;
    }

    public String getFilename() {
        return filename;
    }

    public char[] getContents() {
        return contents;
    }
}
