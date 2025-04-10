package model;

import connection.MyDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public int updateCliente(Cliente cliente) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result = -1; // ponemos -1 como resultado erróneo o no conseguido

        try (Connection connection = dataSource.getConnection();
                Statement st = connection.createStatement()) {

            String query = "update Cliente set " + "nombre = '" + cliente.getNombre() + "',"
                    + "apellidos = '" + cliente.getApellidos() + "',"
                    + "fecha_nacimiento = '" + cliente.getFecha_nacimiento() + "'"
                    + "where dni = '" + cliente.getDni() + "'";

            result = st.executeUpdate(query);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int addCliente(Cliente cliente) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result = -1; // ponemos -1 como resultado erróneo o no conseguido

        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {

            String query = "insert Cliente" + "(nombre, apellidos, DNI, fecha_nacimiento)" + "values (" +
                    " '" + cliente.getNombre() + "'," +
                    " '" + cliente.getApellidos() + "'," +
                    " '" + cliente.getDni() + "'," +
                    " '" + cliente.getFecha_nacimiento() + "')";

            result = st.executeUpdate(query);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteCliente(String DNI) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result = -1; // ponemos -1 como resultado erróneo o no conseguido

        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {

            String query = "delete from Cliente where dni = '" +
                    DNI + "'";

            result = st.executeUpdate(query);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
}
