package texteditor;

import java.util.Locale;

public interface Control 
{
    void registerDateHandler(DateHandler callback);
    void registerFindHandler(FindHandler callback);
    void registerEmojiHandler(EmojiHandler callback);
    Locale getLocale(); 
    void displayDate(String date);
    void displayFind(int startIndex, int endIndex);
    void displayEmoji(String contents);
}
