package controller;

public interface Option {
    public boolean requiresText();
    public String doOption(String s);
    public String doOption();
}