import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC中的事务隔离级别
 * TRANSACTION_NONE  0
 * TRANSACTION_READ_COMMITTED  1
 * TRANSACTION_READ_UNCOMMITTED  2
 * TRANSACTION_REPEATABLE_READ  4
 * TRANSACTION_SERIALIZABLE  8
 *
 * @author Apollo4634
 * @create 2019/09/12
 * @see java.sql.Connection
 */

public class TransationIsolationLevelTest {

    public static void main(String[] args) throws SQLException {
        String url = "com.mysql.jdbc.Driver";
        Connection connection = DriverManager.getConnection(url);
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData.getDefaultTransactionIsolation());
        System.out.println(metaData.supportsTransactionIsolationLevel(1));
    }
}
