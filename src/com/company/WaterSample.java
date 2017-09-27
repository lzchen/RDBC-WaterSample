package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leighton on 9/12/2017.
 * Class representing a WaterSample
 */
public class WaterSample {
    // id: The unique id of the WaterSample
    // site: The origin of where the WaterSample was found
    // rest: the levels of each compound found in the WaterSample
    Integer id;
    String site;
    Double chloroform;
    Double bromoform;
    Double bromodichloromethane;
    Double dibromichloromethane;

    /*  Given an id sampleId, returns a WaterSample object with the associated fields
     * @params: sampleId - The id used to find the WaterSample
     *          dbService - The DBService used to access the database to find the WaterSample info
     * @returns: A WaterSample object
     */
    public static WaterSample find(int sampleId, DBService dbService) {
        if (dbService == null) {
            System.out.println("Database not found in function find.");
            return null;
        }
        return dbService.findWaterSampleById(sampleId);
    }

    /*
     * Given a FactorWeight factorWeight with specific compound weightings, returns the factor computed
     * from the current WaterSample and the given FactorWeight.
     * A factor is defined as the linear combination of the compound levels of this WaterSample
     * weighted by the associated weight levels of the given FactorWeight
     * @params: factorWeight - The FactorWeight used for weighted calculations
     * @returns: A Double result representing the linear combination of the compounds of this WaterSample
     */
    public Double factor(FactorWeight factorWeight) {
        if (this.id == null) {
            System.out.println("This WaterSample is not initialized yet in function factor.");
            return null;
        }
        if (factorWeight == null) {
            System.out.println("Factor is not valid in function factor.");
            return null;
        }
        return this.combineLinear(factorWeight);
    }

    /*
     * Helper function to simply linearly combine the compounds of this WaterSample and the given FactorWeight
     * @params: factor - the FactorWeight used
     * @returns: The weighted linear combination result
     */
    private Double combineLinear(FactorWeight factorWeight) {
        Double total = 0d;
        total += this.chloroform * factorWeight.chloroformWeight + this.bromoform * factorWeight.bromoformWeight +
                this.bromodichloromethane * factorWeight.bromodichloromethaneWeight + this.dibromichloromethane * factorWeight.dibromichloromethaneWeight;
        return total;
    }

    /*
     * Converts the WaterSample object to a hash, implemented using a map with key-value pairs
     * If includeFactors is true, also includes the factors of this WaterSample with every FactorWeight
     * in list factorWeights as key-value pairs
     * @params: includeFactors - indicates whether to include factor hashes
     *          factorWeights - list of FactorWeights to hash with if includeFactors is true
     * @return: A hash representation of the current WaterSample, implemented using a map
     */
    public Map<String, String> toHash(boolean includeFactors, List<FactorWeight> factorWeights) {
        Map<String, String> hash = new HashMap<>();
        hash.put("id", this.id.toString());
        hash.put("site", this.site);
        hash.put("chloroform", this.chloroform.toString());
        hash.put("bromoform", this.bromoform.toString());
        hash.put("bromodichloromethane", this.bromodichloromethane.toString());
        hash.put("dibromichloromethane", this.dibromichloromethane.toString());
        if (includeFactors && factorWeights != null) {
            for (FactorWeight factorWeight : factorWeights) {
                hash.put("factor_" + factorWeight.id, this.combineLinear(factorWeight).toString());
            }
        }
        return hash;
    }

    public boolean equals(WaterSample sample) {
        if (sample == null) { return false; }
        return this.id == sample.id && this.site.equals(sample.site) && this.chloroform.equals(sample.chloroform) && this.bromoform.equals(sample.bromoform) &&
                this.bromodichloromethane.equals(sample.bromodichloromethane) && this.dibromichloromethane.equals(sample.dibromichloromethane);
    }
}
