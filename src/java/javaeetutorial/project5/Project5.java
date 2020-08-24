/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.project5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Project5 {
    private String s1;
    private String s2;
    private String s3;
    private String name;
    private String make;
    private String model;
    private String hp;
    private String color;
    private String isNew;
    private String vin;
    private static String table = "";
    private static String[] data = new String[100];
    ;
    private static int count = 0;
    private static String dbURL = "jdbc:derby://localhost:1527/contact;create=true;user=nbuser;password=nbuser";
    private static String tableName = "APP.Motorcycle";
    private static int x;
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;

    public Project5() {
    }

    public String getName() {
        return name;
    }

    public String getTable() {
        return table;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVin() {
        return vin;
    }

    public String getHp() {
        return hp;
    }

    public String getColor() {
        return color;
    }

    public String getIsNew() {
        return isNew;
    }
    public String getSearch1() {
        return s1;
    }
    public String getSearch2() {
        return s2;
    }
    public String getSearch3() {
        return s3;
    }

    public String getData() {
        x++;
        return data[x - 1];
    }
    

    public int getCount() {
        return count;
    }

    public void setName(String user_name) {
        this.name = user_name;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    public void setSearch1(String s1) {
        this.s1 = s1;
    }
    public void setSearch2(String s2) {
        this.s2 = s2;
    }
    public void setSearch3(String s3) {
        this.s3 = s3;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public void setTable(String t) {
        if ("insert".equalsIgnoreCase(t)) {
            data = new String[100];//clear entire array
            count = 0;//reset counter for array
            x = 0;//reset counter in get
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                conn = DriverManager.getConnection(dbURL);
                stmt = conn.createStatement();
                //stmt.execute("insert into " + tableName + " values (" + "123" + ",'" + t + "','" + t + "'," + 5 + ",'" + t + "'," + "false" + ")");
                stmt.execute("insert into " + tableName + " values ('" + vin + "','" + make + "','" + model + "','" + hp + "','" + color + "','" + isNew + "')");
                stmt.close();
                data[0] = "Insert successful!";
            } catch (Exception sqlExcept) {
                data[0] = "failure:\n" + sqlExcept;
                sqlExcept.printStackTrace();
            }

        } else if ("Display".equalsIgnoreCase(t)) {
            data = new String[100];//clear entire array
            count = 0;//reset counter for array
            x = 0;//reset counter in get
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                //Get a connection
                conn = DriverManager.getConnection(dbURL);
            } catch (Exception except) {
                except.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery("select * from " + tableName);
                ResultSetMetaData rsmd = results.getMetaData();
                int numberCols = rsmd.getColumnCount();
                for (int i = 1; i <= numberCols; i++) {
                    //table += rsmd.getColumnLabel(i);
                    //data[0] = rsmd.getColumnLabel(i);
                }
                while (results.next()) {

                    String VIN = results.getString(1);
                    String make = results.getString(2);
                    String model = results.getString(3);
                    String horsepower = results.getString(4);
                    String color = results.getString(5);
                    String isNew = results.getString(6);
                    data[count] = " VIN: " + VIN + ". Make: " + make + ". Model: "
                            + model + ". HP: " + horsepower + ". Color: " + color
                            + ". Is it new?: " + isNew + " ";;
                    count++;
                }
                results.close();
                stmt.close();
            } catch (SQLException sqlExcept) {
                sqlExcept.printStackTrace();
            }
        } else if ("search".equalsIgnoreCase(t)) {
            data = new String[100];//clear entire array
            count = 0;//reset counter for array
            x = 0;//reset counter in get
            data[0] = "Nothing matches your selection, please try again!\n";
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                //Get a connection
                conn = DriverManager.getConnection(dbURL);
            } catch (Exception except) {
                except.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                ResultSet results = stmt.executeQuery("select "+s3+" from " + tableName +" WHERE "+s1+" = '"+s2+"'");
                ResultSetMetaData rsmd = results.getMetaData();
                int numberCols = rsmd.getColumnCount();
                for (int i = 1; i <= numberCols; i++) {
                    //table += rsmd.getColumnLabel(i);
                    //data[0] = rsmd.getColumnLabel(i);
                }
                while (results.next()) {
                    if(s3.equalsIgnoreCase("*")){
                    String VIN = results.getString(1);
                    String make = results.getString(2);
                    String model = results.getString(3);
                    String horsepower = results.getString(4);
                    String color = results.getString(5);
                    String isNew = results.getString(6);
                    data[count] = " VIN: " + VIN + ". Make: " + make + ". Model: "
                            + model + ". HP: " + horsepower + ". Color: " + color
                            + ". Is it new?: " + isNew + " ";
                    count++;
                    }else{
                    String other = results.getString(1);
                    data[count] = s3+" " + other;
                    count++;
                    }
                }
                results.close();
                stmt.close();
            } catch (SQLException sqlExcept) {
                data[0] = "failure:\n" + sqlExcept;
                sqlExcept.printStackTrace();
            }
        } else {
            data[0] = "Error, please enter display, retrieve, or insert";
        }
    }
}
