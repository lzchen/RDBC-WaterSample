package com.company;

/**
 * Created by Leighton on 9/13/2017.
 * Class representing a FactorWeight
 */
public class FactorWeight {
    // id: The unique id of the FactorWeight
    // rest: the weightings for each chemical compound of this FactorWeight
    int id;
    Double chloroformWeight;
    Double bromoformWeight;
    Double bromodichloromethaneWeight;
    Double dibromichloromethaneWeight;

    public boolean equals(FactorWeight factorWeight) {
        if (factorWeight == null) { return false; }
        return this.id == factorWeight.id && this.chloroformWeight.equals(factorWeight.chloroformWeight) && this.bromoformWeight.equals(factorWeight.bromoformWeight) &&
                this.bromodichloromethaneWeight.equals(factorWeight.bromodichloromethaneWeight) && this.dibromichloromethaneWeight.equals(factorWeight.dibromichloromethaneWeight);
    }
}
