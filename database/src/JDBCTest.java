import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Apollo4634
 * @create 2019/09/02
 */

public class JDBCTest {

    public static void main(String[] args) throws SQLException {
        Driver driver = DriverManager.getDriver("com.mysql.jdbc.Driver");
    }
}
