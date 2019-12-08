package com.faceless.sql.migration;

import com.faceless.sql.Database;

import java.sql.SQLException;

public class CreateVmAndUsersTables extends Migration
{
    // 13.10.19 VM and Users tables were created

    private Database database;
    public CreateVmAndUsersTables(Database db)
    {
        database = db;
    }

    @Override
    public void up() throws SQLException
    {
        database.executeUpdate("USE VMDB;");
        database.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, login VARCHAR(255), password VARCHAR(255));");
        database.executeUpdate("CREATE TABLE IF NOT EXISTS vms (id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                " owner VARCHAR(255),\n" +
                " vmname VARCHAR(255),\n" +
                " cpuvendor VARCHAR(255),\n" +
                " cpufrequency VARCHAR(255),\n" +
                " cpucorecount INT,\n" +
                " ramvolume INT,\n" +
                " hddvolume INT,\n" +
                " monitor BOOL,\n" +
                "os VARCHAR(255));");
    }

    @Override
    public void down() throws SQLException
    {
        database.executeUpdate("DROP TABLE users");
        database.executeUpdate("DROP TABLE vms");
    }
}
