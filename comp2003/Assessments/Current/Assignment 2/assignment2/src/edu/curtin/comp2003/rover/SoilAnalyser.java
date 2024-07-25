package edu.curtin.comp2003.rover;

public class SoilAnalyser {
    /**
     * Begins a soil analysis. The soil analysis will complete some time later, and its result can
     * be retrieved by calling pollAnalysis().
     * 
     * If startAnalysis() is called while analysis is already underway, it will throw an exception.
     */
    public void startAnalysis() { }

    /** 
     * Retrieves the results of a soil analysis, if they're ready yet. If no noew results have been
     * produces, this method returns null.
     */

    public byte[] pollAnalysis() { return new byte[0]; }
}
