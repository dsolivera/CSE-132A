/**
 * Name: Oliver Sumo
 * PID: A11545047
 * Login: osumo
 * This Java program exemplifies the basic usage of JDBC.
 * Requirements:
 * (1) JDK 1.6+.
 * (2) SQLite3.
 * (3) SQLite3 JDBC jar (https://bitbucket.org/xerial/sqlitejdbc/downloads/sqlite-jdbc-3.8.7.jar).
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class PA2 {
 
 
 public static void main(String[] args) {
 Connection conn = null; // Database connection.
 try {
	 // Load the JDBC class.
	 Class.forName("org.sqlite.JDBC");
	 // Get the connection to the database.
	 // - "jdbc" : JDBC connection name prefix.
	 // - "sqlite" : The concrete database implementation
	 // (e.g., sqlserver, postgresql).
	 // - "pa2.db" : The name of the database. In this project,
	 // we use a local database named "pa2.db". This can also
	 // be a remote database name.
	 conn = DriverManager.getConnection("jdbc:sqlite:pa2.db");
	 System.out.println("Opened database successfully."); 
	 // Use case #1: Create and populate a table.
	 // Get a Statement object.
	 Statement stmt = conn.createStatement();
	 //stmt.executeUpdate("DROP TABLE IF EXISTS Student;");	

	 
	 stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
	 stmt.executeUpdate("DROP TABLE IF EXISTS changed;");
	 stmt.executeUpdate("DROP TABLE IF EXISTS T_old;");
	 stmt.executeUpdate("DROP TABLE IF EXISTS Connected;");


	 stmt.executeUpdate("CREATE TABLE Connected(Airline char(32), Origin char(32), " +
	 	"Destination char(32), Stop int);");
	 stmt.executeUpdate("CREATE TABLE Current AS SELECT * FROM Flight;");
	 stmt.executeUpdate("CREATE TABLE changed AS SELECT * FROM Flight;");
	 stmt.executeUpdate("CREATE TABLE T_old(Airline char(32), Origin char(32), " + 
	 	"Destination char(32));");	

	int delta = stmt.executeUpdate("INSERT INTO Connected SELECT Airline, " + 	
	"Origin, Destination, 0 FROM Flight;");

	/*System.out.println("If it ain't sexy I ain't eating!!\n");
	stmt.executeQuery("SELECT * FROM Flight;");

	 ResultSet rset = stmt.executeQuery("SELECT * from Flight;");
	 // Print the FirstName and LastName columns.
	 System.out.println ("\nStatement result:");
	 // This shows how to traverse the ResultSet object.
	 while (rset.next()) {
	 // Get the attribute value.
	 System.out.print(rset.getString("Origin"));
	 System.out.print("\t\t");
	 System.out.println(rset.getString("Destination"));
	 }*/

	int tau = 1;
	while(delta != 0)
	{
		stmt.executeUpdate("DROP TABLE IF EXISTS T_old;");
		stmt.executeUpdate("CREATE TABLE T_old AS SELECT * FROM Current;");
		stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
        
        String firstQuery = "CREATE TABLE Current AS SELECT * FROM T_old UNION " + 
        		"SELECT f.Airline, f.Origin, c.Destination FROM Flight f, Changed c " + 
        		"WHERE f.Destination = c.Origin AND f.Airline = c.Airline AND " + 
        		"f.Origin <> c.Origin AND f.Destination <> c.Destination AND " + "f.Origin <> c.Destination;";
        
        stmt.executeUpdate(firstQuery);
        stmt.executeUpdate("DROP TABLE IF EXISTS Changed;");
                
        String secondQuery = "CREATE TABLE Changed AS SELECT * FROM Current " + 
        		"EXCEPT SELECT * FROM T_old;";
        stmt.executeUpdate(secondQuery);

        String thirdQuery = "INSERT INTO Connected SELECT Airline, Origin, " + 
        		"Destination, " + tau + " FROM Changed;";
                delta = stmt.executeUpdate(thirdQuery);
        tau++;
	}

	 // Student table is being created just as an example. You
	 // do not need Student table in PA2
	//stmt.executeUpdate(
	/* "CREATE TABLE Student(FirstName, LastName);");
	 stmt.executeUpdate(
	"INSERT INTO Student VALUES('F1','L1'),('F2','L2');");
	 // Use case #2: Query the Student table with Statement.
	 // Returned query results are stored in a ResultSet
	 // object.
	 ResultSet rset = stmt.executeQuery("SELECT * from Student;");
	 // Print the FirstName and LastName columns.
	 System.out.println ("\nStatement result:");
	 // This shows how to traverse the ResultSet object.
	 while (rset.next()) {
	 // Get the attribute value.
	 System.out.print(rset.getString("FirstName"));
	 System.out.print("---");
	 System.out.println(rset.getString("LastName"));
	 }
	 // Use case #3: Query the Student table with
	 // PreparedStatement (having wildcards).
	 PreparedStatement pstmt = conn.prepareStatement(
	 "SELECT * FROM Student WHERE FirstName = ?;");
	 // Assign actual value to the wildcard.
	 pstmt.setString (1, "F1");
	 rset = pstmt.executeQuery ();
	 System.out.println ("\nPrepared statement result:");
	 while (rset.next()) {
	 System.out.print(rset.getString("FirstName"));
	 System.out.print("---");
	 System.out.println(rset.getString("LastName")); 
	 } // Close the ResultSet and Statement objects.
	 rset.close();
	*/
    stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
    stmt.executeUpdate("DROP TABLE IF EXISTS Changed;");
    stmt.executeUpdate("DROP TABLE IF EXISTS T_old;");
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