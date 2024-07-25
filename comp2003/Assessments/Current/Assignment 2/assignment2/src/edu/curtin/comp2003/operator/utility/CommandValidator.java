package edu.curtin.comp2003.operator.utility;

public class CommandValidator {
    /**
     * Verifies whether a command polled from Earth represents a prescribed command. If so, returns
     * true. Otherwise, false.
     * 
     * @param command
     */
    public static boolean isValidCommand(String command) {
        String[] splitCommand = command.split(" ");
        int n = splitCommand.length;

        boolean validCommand = false;
        if (n >= 1) {
            char specifier = splitCommand[0].charAt(0);
            if (n == 1) {
                if (specifier == 'P' || specifier == 'E' || specifier == 'S') {
                    validCommand = true;
                }
            }
            else if (n == 2) {
                try {
                    double value = Double.parseDouble(splitCommand[1]);
                    if (specifier == 'D') {
                        if (value > 0.0) {
                            validCommand = true;
                        }
                    }
                    else if (specifier == 'T') {
                        if (-180.0 <= value && value <= 180.0) {
                            validCommand = true;
                        }
                    }
                }
                catch (NumberFormatException e) { }
            }
        }

        return validCommand;
    }
}
