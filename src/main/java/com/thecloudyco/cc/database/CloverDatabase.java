/*
 * Copyright (C) 2022 The Cloudy Co.
 *
 * This file was created by Atticus Zambrana
 */

package com.thecloudyco.cc.database;

import cc.leafed.tcc_core.TCC_Core;

import java.sql.*;

public class CloverDatabase {

    private static Connection conn;
    private static int counter = 0;
    private static boolean isConnected;

    private static boolean debug = false;

    public static void startTracker() {
        Thread thr = new Thread(() -> {
            while(true) {
                try {
                    if(counter >= 600) {
                        if(isConnected) {
                            try {
                                disconnect();
                            } catch(Exception ex) {
                                //idk???
                            }
                        }
                        counter = 0;
                    }
                    counter++;
                    //System.out.println(counter);
                    Thread.sleep(1000);
                } catch (InterruptedException  e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
        thr.start();
    }

    public static void connect(boolean minecraft) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //TODO: Remove database credentials from being hardcoded, and move them into a config file or something safer
        if(minecraft) {
            //minecraft_connection = DriverManager.getConnection("jdbc:mysql://10.0.0.28:3306/terminal", "ccl", "kPSKuLO7L0fMT5fA");
        } else {
            conn = DriverManager.getConnection("jdbc:mysql://" + TCC_Core.getCore().getConfig().getString("database.host") + ":" + TCC_Core.getCore().getConfig().getInt("database.port") + "/" + TCC_Core.getCore().getConfig().getString("database.database"), TCC_Core.getCore().getConfig().getString("database.username"), TCC_Core.getCore().getConfig().getString("database.password"));
        }
        //conn = DriverManager.getConnection("jdbc:mysql://ccl:3306/terminal", "ccl", "kPSKuLO7L0fMT5fA");
        isConnected = true;
    }

    public static void disconnect() throws SQLException {
        conn.close();
        isConnected = false;
    }

    // Static class, that manages database connections and such
    public static ResultSet query(String sql) throws SQLException {
        disconnect();
        if(debug)
            System.out.println("[Database Debugger (QUERY)]: " + sql);
        if(!isConnected) {
            //System.out.println("[Clover Database] Reconnecting to DB upon request...");
            connect(false);
        } else {
            // If we are already connected, reset the counter, so we don't disconnect
            counter = 0;
        }
        // STOP SQL INJECTIONS BEFORE THEY CAN HAPPEN
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        return results;
    }

    public static int update(String sql) throws SQLException {
        disconnect();
        if(debug)
            System.out.println("[Database Debugger (UPDATE)]: " + sql);
        if(!isConnected) {
            connect(false);
        }
        Statement stmt = conn.createStatement();
        int results = stmt.executeUpdate(sql);
        return results;
    }



    // Minecraft Database connections
//    public static ResultSet m_query(String sql) throws SQLException {
//        disconnect();
//        if(debug)
//            System.out.println("[Database Debugger (QUERY)]: " + sql);
//        if(!isConnected) {
//            //System.out.println("[Clover Database] Reconnecting to DB upon request...");
//            connect(true);
//        } else {
//            // If we are already connected, reset the counter, so we don't disconnect
//            counter = 0;
//        }
//        // STOP SQL INJECTIONS BEFORE THEY CAN HAPPEN
//        Statement stmt = minecraft_connection.createStatement();
//        ResultSet results = stmt.executeQuery(sql);
//        return results;
//    }

//    public static int m_update(String sql) throws SQLException {
//        disconnect();
//        if(debug)
//            System.out.println("[Database Debugger (UPDATE)]: " + sql);
//        if(!isConnected) {
//            connect(true);
//        }
//        Statement stmt = minecraft_connection.createStatement();
//        int results = stmt.executeUpdate(sql);
//        return results;
//    }
}