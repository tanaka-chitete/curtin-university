package texteditor;

public interface FindHandler 
{
    void find(String contents, int caretPosition, String term);
}
