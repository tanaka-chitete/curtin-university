/**
 * The entry point for Spark, an application which models a city’s electricity network. It either 
 * reads or randomly generates a dataset representing the structure of the network. Thereafter, it 
 * either writes or displays the network. The user chooses which actions to perform via the 
 * command-line.
 * @author Tanaka Chitete
 */

package main;

import controller.Spark;
import controller.Generator;
import controller.Reader;
import controller.Processor;
import controller.Writer;
import view.Displayer;

import java.util.Arrays;

public class Main {
    /** Main */
    public static void main(String[] args) {
        Spark instance = new Spark();

        instance.addOption('g', new Generator());
        instance.addOption('r', new Reader());
        instance.addOption('p', new Processor());
        instance.addOption('d', new Displayer());
        instance.addOption('w', new Writer());

        // Identify which invocation the user is attempting to run Spark using
        int invocation = instance.identifyInvocation(args);

        // Execute user-specified invocation
        instance.launchInvocation(args, invocation);
    }    
}
