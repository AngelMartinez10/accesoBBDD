package connection;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MyDataSource {

    public static DataSource getMySQLDataSource() {
        Properties properties = new Properties();

        MysqlDataSource mysqlDataSource = null;

        try(FileInputStream fis = new FileInputStream("db.properties")) {
            properties.load(fis);

            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mysqlDataSource;
    }

    public static Connection conectarMYSQL() {
        Connection connection = null;
        try {
            DataSource dataSource = getMySQLDataSource();
            connection = dataSource.getConnection();
            if (connection != null) {
                System.out.println("Conexión establecida");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


}
