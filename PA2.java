import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PA2 {
    final static boolean DEBUG = true;
    public static void main(String[] args) {
         Connection conn = null; // Database connection.
        try {
            // Load the JDBC class.
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:pa2.db");
            System.out.println("Opened database successfully.");

            // Use case #1: Create and populate a table.
            // Get a Statement object.
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS Connected;");
            stmt.executeUpdate(
                "CREATE TABLE Connected(Airline char(32), Origin char(32), "
                    + "Destination char(32), Stops int);");

            stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Change;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Prev;");

            stmt.executeUpdate("CREATE TABLE Current AS SELECT * FROM Flight;");
            stmt.executeUpdate("CREATE TABLE Change AS SELECT * FROM Flight;");
            stmt.executeUpdate("CREATE TABLE Prev(Airline char(32), Origin char(32), "
                + "Destination char(32));");

            int rows = stmt.executeUpdate("INSERT INTO Connected SELECT Airline, "
                + "Origin, Destination, 0 FROM Flight;");
            int count = 1;
            while( rows != 0 ) {
                stmt.executeUpdate("DROP TABLE IF EXISTS Prev;");
                stmt.executeUpdate("CREATE TABLE Prev AS SELECT * FROM Current;");

                stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
                String query = "CREATE TABLE Current AS SELECT * FROM Prev union "
                    + "SELECT f.Airline, f.Origin, c.Destination FROM Flight f, Change c "
                    + "WHERE f.Destination=c.Origin AND f.Airline=c.Airline AND "
                    + "f.Origin!=c.Origin AND f.Destination!=c.Destination AND "
                    + "f.Origin!=c.Destination;";
                stmt.executeUpdate(query);

                stmt.executeUpdate("DROP TABLE IF EXISTS Change;");
                String query2 = "CREATE TABLE Change AS SELECT * FROM Current "
                        + "EXCEPT SELECT * FROM Prev;";
                stmt.executeUpdate(query2);

                String query3 = "INSERT INTO Connected SELECT Airline, Origin, "
                    + "Destination, " + count + " FROM CHANGE;";
                rows = stmt.executeUpdate(query3);

                if(DEBUG == true) {
                    System.out.println(rows);
                }
                count++;
            }

            if(DEBUG == true) {
                stmt.executeUpdate("DROP VIEW IF EXISTS diff;");
                stmt.executeUpdate("CREATE VIEW diff AS SELECT * FROM Connected "
                    + "EXCEPT SELECT * FROM Solution;");
                stmt.executeUpdate("DROP VIEW IF EXISTS diff2;");
                stmt.executeUpdate("CREATE VIEW diff2 AS SELECT * FROM Connected "
                    + "EXCEPT SELECT * FROM Solution;");
            }

            stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Change;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Prev;");

            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("There was a runtime problem!", e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Cannot close the connection!", e);
            }
        }
    }
}
