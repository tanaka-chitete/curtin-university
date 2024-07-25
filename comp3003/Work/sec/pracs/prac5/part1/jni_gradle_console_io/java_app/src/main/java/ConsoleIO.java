import java.util.List;

public class ConsoleIO
{
    static
    {
        System.loadLibrary("console_io_c_library");
    }

    public native static double read(double defaultValue);
    public native static void printStr(String text);
    public native static void printList(List<String> list);
}
