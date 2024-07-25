package edu.curtin.comp2003.operator.state;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public interface RoverState {
    public void handle(RoverOperator context);
}