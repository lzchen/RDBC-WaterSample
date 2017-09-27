package com.company;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leighton on 9/13/2017.
 * A service used to communicate with the database
 * Follows a singleton pattern in which only one instance will ever be instantiated
 */
public class DBService {

    // connection string used to connect to the sqlite database
    // We are using JDBC to connect to databases
    private static final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + File.separator + "info.db";
    private static DBService dbService = null;
    private static Connection conn = null;

    // We have a private constructor to implement the singleton pattern
    // The database will be instantiated once and everywhere reference to it will be done through
    // getInstance() which points to the original database created
    private DBService() {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DBService getInstance() {
        if (dbService == null) {
            dbService = new DBService();
        }
        return dbService;
    }

    /*
     * Given an id, queries the database and return a WaterSample object with id id
     * @params: id - the id to query with
     * @returns: a WaterSample object, null if id does not exist in database or if the database is not initialized
     */
    public WaterSample findWaterSampleById(int id) {
        if (conn == null) {
            System.out.println("Database not found. in function findWaterSampleById.");
            return null;
        }
        String sql = "SELECT * FROM water_samples WHERE id=" + id;
        WaterSample sample = null;
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            ResultSet rs    = pstmt.executeQuery();
            if (rs.next()) {
                sample = new WaterSample();
                mapWaterSampleFromResultSet(sample, rs);
            }
        } catch (SQLException e) {
            System.out.println("findWaterSampleById: " + e.getMessage());
        }
        return sample;
    }

    /*
 * Queries the database for all WaterSample entries
 * @returns: A list of all WaterSample objects from querying the database, null if the database is not initialized
 */
    public List<WaterSample> findAllWaterSamples() {
        if (conn == null) {
            System.out.println("Database not found in function findAllWaterSamples.");
            return null;
        }
        String sql = "SELECT * FROM water_samples";
        List<WaterSample> sampleList = new ArrayList<>();
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            ResultSet rs    = pstmt.executeQuery();
            while (rs.next()) {
                WaterSample sample = new WaterSample();
                mapWaterSampleFromResultSet(sample, rs);
                sampleList.add(sample);
            }
        } catch (SQLException e) {
            System.out.println("findAllWaterSamples: " + e.getMessage());
        }
        return sampleList;
    }

    /*
     * Given an id, queries the database and return a FactorWeight object with id id
     * @params: id - the id to query with
     * @returns: a FactorWeight object, null if id does not exist in database or if the database is not initialized
     */
    public FactorWeight findFactorWeightById(int id) {
        if (conn == null) {
            System.out.println("Database not found in findFactorWeightById.");
            return null;
        }
        String sql = "SELECT * FROM factor_weights WHERE id=" + id;
        FactorWeight factor = null;
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            ResultSet rs    = pstmt.executeQuery();
            if (rs.next()) {
                factor = new FactorWeight();
                mapFactorWeightFromResultSet(factor, rs);
            }
        } catch (SQLException e) {
            System.out.println("findFactorWeightById: " + e.getMessage());
        }
        return factor;
    }

    /*
     * Queries the database for all FactorWeight entries
     * @returns: A list of all FactorWeight objects from querying the database, null if the database is not initialized
     */
    public List<FactorWeight> findAllFactorWeights() {
        if (conn == null) {
            System.out.println("Database not found in function findAllFactorWeights.");
            return null;
        }
        String sql = "SELECT * FROM factor_weights";
        List<FactorWeight> factorsList = new ArrayList<>();
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            ResultSet rs    = pstmt.executeQuery();
            while (rs.next()) {
                FactorWeight factor = new FactorWeight();
                mapFactorWeightFromResultSet(factor, rs);
                factorsList.add(factor);
            }
        } catch (SQLException e) {
            System.out.println("findAllFactorWeights: " + e.getMessage());
        }
        return factorsList;
    }

    // Helper function to build the WaterSample object from the given ResultSet rs
    private void mapWaterSampleFromResultSet(WaterSample sample, ResultSet rs) throws SQLException {
        sample.id = rs.getInt("id");
        sample.site = rs.getString("site");
        sample.chloroform = rs.getDouble("chloroform");
        sample.bromoform = rs.getDouble("bromoform");
        sample.bromodichloromethane = rs.getDouble("bromodichloromethane");
        sample.dibromichloromethane = rs.getDouble("dibromichloromethane");
    }

    // Helper function to build the FactorWeight object from the given ResultSet rs
    private void mapFactorWeightFromResultSet(FactorWeight factorWeight, ResultSet rs) throws SQLException {
        factorWeight.id = rs.getInt("id");
        factorWeight.chloroformWeight = rs.getDouble("chloroform_weight");
        factorWeight.bromoformWeight = rs.getDouble("bromoform_weight");
        factorWeight.bromodichloromethaneWeight = rs.getDouble("bromodichloromethane_weight");
        factorWeight.dibromichloromethaneWeight = rs.getDouble("dibromichloromethane_weight");
    }

    ////////////////////////////////
    // Functions used for testing //

    public void clearTables() {
        clearWaterSamples();
        clearFactorWeights();
    }

    public void clearWaterSamples() {
        if (conn == null) {
            System.out.println("Database not found in function clearWaterSamples.");
            return;
        }
        String sql = "DELETE FROM water_samples";
        try {
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("clearWaterSamples: " + e.getMessage());
        }
    }

    public void clearFactorWeights() {
        if (conn == null) {
            System.out.println("Database not found in function clearFactorWeights.");
            return;
        }
        String sql = "DELETE FROM factor_weights";
        try {
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("clearFactorWeights: " + e.getMessage());
        }
    }

    public void insertWaterSample(WaterSample sample) {
        if (conn == null) {
            System.out.println("Database not found in function insertWaterSample.");
            return;
        }
        String sql = "INSERT INTO water_samples VALUES (" + sample.id + ", '" + sample.site + "', " + sample.chloroform + ", " +
                sample.bromoform + ", " + sample.bromodichloromethane + ", " + sample.dibromichloromethane + ")";
        try {
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("insertWaterSample: " + e.getMessage());
        }
    }

    public void insertFactorWeight(FactorWeight factorWeight) {
        if (conn == null) {
            System.out.println("Database not found in function insertFactorWeight.");
            return;
        }
        String sql = "INSERT INTO factor_weights VALUES (" + factorWeight.id + ", " + factorWeight.chloroformWeight + ", " +
                factorWeight.bromoformWeight + ", " + factorWeight.bromodichloromethaneWeight + ", " + factorWeight.dibromichloromethaneWeight + ")";
        try {
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("insertFactorWeight: " + e.getMessage());
        }
    }

    ////////////////////////////////
    // Functions used for demo //

    public void setUpSampleData() {
        if (conn == null) {
            System.out.println("Database not found in function setUpSampleData.");
            return;
        }
        this.clearTables();
        String sql1 = "INSERT INTO `water_samples` VALUES (1,'LA Aquaduct Filteration Plant Effluent',0.00104,0,0.00149,0.00275)," +
                "(2,'North Hollywood Pump Station (well blend)',0.00291,0.00487,0.00547,0.0109)," +
                "(3,'Jensen Plant Effluent',0.00065,0.00856,0.0013,0.00428)," +
                "(4,'Weymouth Plant Effluent',0.00971,0.00317,0.00931,0.0116)," +
                "(5,'Cleaner than soap',0.01,0.002,0.0003,0.00004)," +
                "(6,'Dirtiest Place Ever',0.9,0.09,0.009,0.0009)";
        String sql2 = "INSERT INTO `factor_weights` VALUES (1,0.8,1.2,1.5,0.7),(2,1,1,1,1),(3,0.9,1.1,1.3,0.6),(4,0,1,1,1.7),(5,0,0,0,0),(6,1,1,1,1),(7,0.5,0.5,0.5,0.5)";
        try {
            Statement stmt  = conn.createStatement();
            stmt.execute(sql1);
            stmt.execute(sql2);
        } catch (SQLException e) {
            System.out.println("setUpSampleData: " + e.getMessage());
        }
    }
}
