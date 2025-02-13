package com.example;

import org.apache.calcite.jdbc.CalciteConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CalciteTest {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:calcite:model=C:/Users/giovanna.ferreira/Desktop/Apache/calcite-pokeapi/model.json");
            CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);

            Statement statement = calciteConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM POKEMON");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + ": " + resultSet.getString("NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}