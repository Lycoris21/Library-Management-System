package utility;

//import com.sun.jdi.connect.spi.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Christine Ann Dejito
 */
public class Database {
    
    private String username = "admin";
    private String password = "admin";
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=LMS_DB;trustServerCertificate=true";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
}
