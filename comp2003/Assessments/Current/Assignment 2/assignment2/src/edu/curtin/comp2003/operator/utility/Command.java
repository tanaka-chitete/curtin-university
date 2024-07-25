package edu.curtin.comp2003.operator.utility;

public class Command {
    private char   specifier;
    private double value;           // Auto-initialised to 0.0
    private String commandAsString;

    /** Alternate Constructor */
    public Command(String commandAsString) {
        if (!CommandValidator.isValidCommand(commandAsString)) {
            specifier = '!';
        }
        else {
            String[] splitCommandAsString = commandAsString.split(" ");
            specifier = splitCommandAsString[0].charAt(0);
            if (specifier == 'D' || specifier == 'T') {
                value = Double.parseDouble(splitCommandAsString[1]);
            }
        }
        this.commandAsString = commandAsString;
    }

    public char getSpecifier() {
        return specifier;
    }

    public double getValue() {
        return value;
    }

    public String getCommand() {
        return commandAsString;
    }

    @Override 
    public String toString() {
        return getCommand();
    }
}
