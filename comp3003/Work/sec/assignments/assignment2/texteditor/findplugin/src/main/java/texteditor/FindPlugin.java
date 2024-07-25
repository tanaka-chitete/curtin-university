package texteditor;

public class FindPlugin implements Plugin, FindHandler 
{
    private Control api;

    @Override
    public void start(Control api)
    {
        this.api = api;
        api.registerFindHandler(this);
    }

    @Override
    public void find(String contents, int caretPosition, String term) 
    {
        int startIndex = contents.indexOf(term, caretPosition);
        if (startIndex != -1)
        {
            int endIndex = startIndex + term.length();
            api.displayFind(startIndex, endIndex);
        }
    }
}
