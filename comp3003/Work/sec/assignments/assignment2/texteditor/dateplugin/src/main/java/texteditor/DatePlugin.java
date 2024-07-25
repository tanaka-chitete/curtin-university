package texteditor;

import java.text.DateFormat;
import java.util.Date;

public class DatePlugin implements Plugin, DateHandler 
{
    private Control api;

    @Override
    public void start(Control api)
    {
        this.api = api;
        api.registerDateHandler(this);
    }

    @Override
    public void getDate() 
    {
        Date dateObject = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, api.getLocale());
        String dateString = dateFormat.format(dateObject);

        api.displayDate(dateString);
    }
}
