package com.company;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static DBService dbService = DBService.getInstance();
    public static void main(String[] args) {
        dbService = DBService.getInstance();
//        TestSuite.runAllTests(dbService);
        dbService.setUpSampleData();

        Scanner userInput = new Scanner(System.in);
        boolean end = false;
        while(!end) {
            printInstructions();
            String input = userInput.nextLine();
            switch (input) {
                case "ws":
                    showWaterSamples();
                    break;
                case "fw":
                    showFactorWeights();
                    break;
                case "fi":
                    System.out.println("Enter ID to find: ");
                    String idInput = userInput.nextLine();
                    try {
                        int id = Integer.parseInt(idInput);
                        WaterSample sample = WaterSample.find(id, dbService);
                        showWaterSample(sample);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number.");
                    }
                    break;
                case "fa":
                    System.out.println("Enter ID of WaterSample: ");
                    String sampleIDInput = userInput.nextLine();
                    System.out.println("Enter ID of FactorWeight: ");
                    String factorIDInput = userInput.nextLine();
                    try {
                        int sampleId = Integer.parseInt(sampleIDInput);
                        int factorId = Integer.parseInt(factorIDInput);
                        WaterSample sample = WaterSample.find(sampleId, dbService);
                        if (sample == null) {
                            System.out.println("WaterSample not found.");
                            break;
                        }
                        FactorWeight factorWeight = dbService.findFactorWeightById(factorId);
                        if (factorWeight == null) {
                            System.out.println("FactorWeight not found.");
                            break;
                        }
                        System.out.println("Factor " + factorId + " of Watersample " + sampleId + " is " + sample.factor(factorWeight));
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid id.");
                    }
                    break;
                case "h":
                    System.out.println("Enter ID of WaterSample: ");
                    String sampleId = userInput.nextLine();
                    try {
                        int id = Integer.parseInt(sampleId);
                        WaterSample sample = WaterSample.find(id, dbService);
                        if (sample == null) {
                            System.out.println("WaterSample not found.");
                            break;
                        }
                        System.out.println("Include all factors? (y/n): ");
                        String include = userInput.nextLine();
                        boolean includeFactors = false;
                        includeFactors = include.equals("y");
                        showHash(sample.toHash(includeFactors, dbService.findAllFactorWeights()));
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number.");
                    }
                    break;
                case "r":
                    System.out.println("Resetting Database.");
                    dbService.clearTables();
                    break;
                case "s":
                    System.out.println("Setting up sample data.");
                    dbService.setUpSampleData();
                    break;
                case "x":
                    System.out.println("Exiting.");
                    end = true;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }

    }

    private static void printInstructions() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("ws - Show all Water Samples" +  "\t  " +
                "fw - Show All Factor Weights");
        System.out.println("fi - Find"  +  "\t  " +
                    "fa - Factor"  +  "\t  " +
                    "h - Hash");
        System.out.println("r - Reset DB"  +  "\t  " +
                "s - Set up sample data"  +  "\t  " +
                "x - Exit");
    }

    private static void showWaterSamples() {
        showWaterSampleHeadings();
        for (WaterSample sample : dbService.findAllWaterSamples()) {
            System.out.println(sample.id +  "\t  |" +
                            sample.site + "\t  |" +
                            sample.chloroform + "\t  |" +
                            sample.bromoform + "\t  |" +
                            sample.bromodichloromethane + "\t  |" +
                            sample.dibromichloromethane);
        }
    }

    private static void showWaterSample(WaterSample waterSample) {
        if (waterSample == null) {
            System.out.println("WaterSample not found.");
            return;
        }
        showWaterSampleHeadings();
        System.out.println(waterSample.id +  "\t  |" +
                waterSample.site + "\t  |" +
                waterSample.chloroform + "\t  |" +
                waterSample.bromoform + "\t  |" +
                waterSample.bromodichloromethane + "\t  |" +
                waterSample.dibromichloromethane);
    }

    private static void showWaterSampleHeadings() {
        System.out.println("id" +  "\t  |" +
                "Site"+ "\t  |" +
                "Chloroform" + "\t  |" +
                "Bromoform" + "\t  |" +
                "Bromodichloromethane" + "\t  |" +
                "Dibromichloromethane");
    }

    private static void showFactorWeights() {
        showFactorWeightHeadings();
        for (FactorWeight weight : dbService.findAllFactorWeights()) {
            System.out.println(weight.id +  "\t  |" +
                    weight.chloroformWeight + "\t  |" +
                    weight.bromoformWeight + "\t  |" +
                    weight.bromodichloromethaneWeight + "\t  |" +
                    weight.dibromichloromethaneWeight);
        }
    }

    private static void showFactorWeight(FactorWeight factorWeight) {
        if (factorWeight == null) {
            System.out.println("FactorWeight not found.");
            return;
        }
        showFactorWeightHeadings();
        System.out.println(factorWeight.id +  "\t  |" +
                factorWeight.chloroformWeight + "\t  |" +
                factorWeight.bromoformWeight + "\t  |" +
                factorWeight.bromodichloromethaneWeight + "\t  |" +
                factorWeight.dibromichloromethaneWeight);
    }

    private static void showFactorWeightHeadings() {
        System.out.println("id" +  "\t  |" +
                "Chloroform_Weight" + "\t  |" +
                "Bromoform_Weight" + "\t  |" +
                "Bromodichloromethane_Weight" + "\t  |" +
                "Dibromichloromethane_Weight");
    }

    private static void showHash(Map<String, String> results) {
        if (results == null) { return; }
        System.out.println("id" +  "\t  " +
                "Value");
        System.out.println("------------");
        Iterator it = results.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() +  "\t  " +
                    pair.getValue());
        }
    }
}
