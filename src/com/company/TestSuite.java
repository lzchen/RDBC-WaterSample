package com.company;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Leighton on 9/14/2017.
 */
public class TestSuite {

    private static DBService dbService = null;

    public static void runAllTests(DBService db) {
        dbService = db;
        boolean passed = true;
        Map<String, Boolean> results = new HashMap<>();
        results.put("DBSingletonTest", DBSingletonTest());
        results.put("FindWaterSampleByIdTest", FindWaterSampleByIdTest());
        results.put("FindFactorWeightByIdTest", FindFactorWeightByIdTest());
        results.put("FindAllFactorWeightsTest", FindAllFactorWeightsTest());
        results.put("FindWaterSampleTest", FindWaterSampleTest());
        results.put("WaterSampleFactorTest", WaterSampleFactorTest());
        results.put("WaterSampleHashTest", WaterSampleHashTest());
        results.put("WaterSampleHashWithFactorsTest", WaterSampleHashWithFactorsTest());

        Iterator it = results.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (!(Boolean)pair.getValue()) {
                System.out.println(pair.getKey() + " failed.");
                passed = false;
            }
            it.remove();
        }
        if (passed) {
            System.out.println("All tests passed.");
        }
    }

    // DBService Tests

    private static boolean DBSingletonTest() {
        DBService dbService2 = DBService.getInstance();

        return dbService == dbService2;
    }

    private static boolean FindWaterSampleByIdTest() {
        WaterSample sample1 = new WaterSample();
        sample1.id = 1;
        sample1.site = "Test_Site1";
        sample1.chloroform = 1.1;
        sample1.bromoform = 2.2;
        sample1.bromodichloromethane = 3.3;
        sample1.dibromichloromethane = 4.4;

        dbService.clearWaterSamples();
        dbService.insertWaterSample(sample1);

        WaterSample sample2 = dbService.findWaterSampleById(sample1.id);
        return sample1.equals(sample2);
    }

    private static boolean FindFactorWeightByIdTest() {
        FactorWeight factor1 = new FactorWeight();
        factor1.id = 1;
        factor1.chloroformWeight = 0.2;
        factor1.bromoformWeight = 0.3;
        factor1.bromodichloromethaneWeight = 0.4;
        factor1.dibromichloromethaneWeight = 0.1;

        dbService.clearFactorWeights();
        dbService.insertFactorWeight(factor1);

        FactorWeight factor2 = dbService.findFactorWeightById(factor1.id);
        return factor1.equals(factor2);
    }

    private static boolean FindAllFactorWeightsTest() {
        FactorWeight factor1 = new FactorWeight();
        factor1.id = 1;
        factor1.chloroformWeight = 0.2;
        factor1.bromoformWeight = 0.3;
        factor1.bromodichloromethaneWeight = 0.4;
        factor1.dibromichloromethaneWeight = 0.1;

        FactorWeight factor2 = new FactorWeight();
        factor2.id = 2;
        factor2.chloroformWeight = 1d;
        factor2.bromoformWeight = 1d;
        factor2.bromodichloromethaneWeight = 1d;
        factor2.dibromichloromethaneWeight = 1d;

        dbService.clearFactorWeights();
        dbService.insertFactorWeight(factor1);
        dbService.insertFactorWeight(factor2);

        List<FactorWeight> list = dbService.findAllFactorWeights();
        return list.size() == 2 && list.get(0).equals(factor1) && list.get(1).equals(factor2);
    }

    // WaterSample Tests

    private static boolean FindWaterSampleTest() {
        WaterSample sample1 = new WaterSample();
        sample1.id = 2;
        sample1.site = "Test_Site2";
        sample1.chloroform = 5.5;
        sample1.bromoform = 6.6;
        sample1.bromodichloromethane = 7.7;
        sample1.dibromichloromethane = 8.8;

        dbService.clearWaterSamples();
        dbService.insertWaterSample(sample1);

        WaterSample sample2 = WaterSample.find(sample1.id, dbService);
        return sample1.equals(sample2);
    }

    private static boolean WaterSampleFactorTest() {
        WaterSample sample1 = new WaterSample();
        sample1.id = 3;
        sample1.site = "Test_Site3";
        sample1.chloroform = 0.1;
        sample1.bromoform = 0.2;
        sample1.bromodichloromethane = 0.3;
        sample1.dibromichloromethane = 0.4;

        FactorWeight factor1 = new FactorWeight();
        factor1.id = 4;
        factor1.chloroformWeight = 0.3;
        factor1.bromoformWeight = 0.2;
        factor1.bromodichloromethaneWeight = 0.4;
        factor1.dibromichloromethaneWeight = 0.1;

        Double calculated = sample1.chloroform * factor1.chloroformWeight + sample1.bromoform * factor1.bromoformWeight +
                sample1.bromodichloromethane * factor1.bromodichloromethaneWeight + sample1.dibromichloromethane * factor1.dibromichloromethaneWeight;

        dbService.clearTables();
        dbService.insertWaterSample(sample1);
        dbService.insertFactorWeight(factor1);

        return calculated.equals(sample1.factor(factor1));
    }

    private static boolean WaterSampleHashTest() {
        WaterSample sample1 = new WaterSample();
        sample1.id = 4;
        sample1.site = "Test_Site4";
        sample1.chloroform = 0.1;
        sample1.bromoform = 0.2;
        sample1.bromodichloromethane = 0.3;
        sample1.dibromichloromethane = 0.4;

        dbService.clearWaterSamples();
        dbService.insertWaterSample(sample1);

        Map<String, String> result = sample1.toHash(false, null);
        return result.get("id").equals(sample1.id.toString()) && result.get("site").equals(sample1.site) && result.get("chloroform").equals(sample1.chloroform.toString())
                && result.get("bromoform").equals(sample1.bromoform.toString()) && result.get("bromodichloromethane").equals(sample1.bromodichloromethane.toString())
                && result.get("dibromichloromethane").equals(sample1.dibromichloromethane.toString());
    }

    private static boolean WaterSampleHashWithFactorsTest() {
        WaterSample sample1 = new WaterSample();
        sample1.id = 5;
        sample1.site = "Test_Site5";
        sample1.chloroform = 0.1;
        sample1.bromoform = 0.2;
        sample1.bromodichloromethane = 0.3;
        sample1.dibromichloromethane = 0.4;

        FactorWeight factor1 = new FactorWeight();
        factor1.id = 5;
        factor1.chloroformWeight = 0.3;
        factor1.bromoformWeight = 0.2;
        factor1.bromodichloromethaneWeight = 0.4;
        factor1.dibromichloromethaneWeight = 0.1;

        FactorWeight factor2 = new FactorWeight();
        factor2.id = 6;
        factor2.chloroformWeight = 0.1;
        factor2.bromoformWeight = 0.4;
        factor2.bromodichloromethaneWeight = 0.2;
        factor2.dibromichloromethaneWeight = 0.3;

        dbService.clearTables();
        dbService.insertWaterSample(sample1);
        dbService.insertFactorWeight(factor1);
        dbService.insertFactorWeight(factor2);

        Double calculated1 = sample1.chloroform * factor1.chloroformWeight + sample1.bromoform * factor1.bromoformWeight +
                sample1.bromodichloromethane * factor1.bromodichloromethaneWeight + sample1.dibromichloromethane * factor1.dibromichloromethaneWeight;
        Double calculated2 = sample1.chloroform * factor2.chloroformWeight + sample1.bromoform * factor2.bromoformWeight +
                sample1.bromodichloromethane * factor2.bromodichloromethaneWeight + sample1.dibromichloromethane * factor2.dibromichloromethaneWeight;

        Map<String, String> result = sample1.toHash(true, dbService.findAllFactorWeights());

        return result.get("id").equals(sample1.id.toString()) && result.get("site").equals(sample1.site) && result.get("chloroform").equals(sample1.chloroform.toString())
                && result.get("bromoform").equals(sample1.bromoform.toString()) && result.get("bromodichloromethane").equals(sample1.bromodichloromethane.toString())
                && result.get("dibromichloromethane").equals(sample1.dibromichloromethane.toString())
                && result.get("factor_" + factor1.id).equals(calculated1.toString())
                && result.get("factor_" + factor2.id).equals(calculated2.toString());
    }
}

