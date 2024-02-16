package com.takinlib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class heh {
    public static void main(String[] args) throws SQLException {
         Connection connection = DriverManager.getConnection("jdbc:sqlite:livros.db");
            String sql = "SELECT * FROM livros";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
            }
    }
}
