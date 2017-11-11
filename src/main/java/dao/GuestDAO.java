package dao;

import java.sql.*;
import java.util.ArrayList;
import model.GuestModel;

public class GuestDAO {

    public static void insertMessage(String message, String name, String date) {
        Connection dbConnection = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:database.db");
            dbConnection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String insertTableSQL = "INSERT INTO guest (Message, Name, Date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, message);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, date);
            preparedStatement .executeUpdate();

            preparedStatement.close();
            dbConnection.commit();
            dbConnection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static ArrayList<GuestModel> getBook() {

        ArrayList<GuestModel> book = new ArrayList<>();
        Connection dbConnection;

        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:database.db");
            System.out.println("Opened database successfully");

            String selectSQL = "SELECT * FROM guest;";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String guestMessage = rs.getString("Message");
                String guestName = rs.getString("Name");
                String messageDate = rs.getString("Date");

                GuestModel guest = new GuestModel(guestMessage, guestName, messageDate);
                book.add(guest);
            }

            preparedStatement.close();
            dbConnection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records read successfully");

        return book;

    }

}