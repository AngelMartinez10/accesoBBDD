package model;

import connection.MyDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDB implements AlmacenDB {


    @Override
    public List<Cliente> getAllClientes() {

        List<Cliente> clientes = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMySQLDataSource();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from Cliente")) {

            while (resultSet.next()) {
                clientes.add(new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("dni"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientes;
    }
}
