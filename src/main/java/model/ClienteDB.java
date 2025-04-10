package model;

import connection.MyDataSource;

import javax.sql.DataSource;
import java.sql.*;
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

//    @Override
//    public int updateCliente(Cliente cliente) {
//
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        int result = -1; // ponemos -1 como resultado erróneo o no conseguido
//
//        try (Connection connection = dataSource.getConnection();
//                Statement st = connection.createStatement()) {
//
//            String query = "update Cliente set " + "nombre = '" + cliente.getNombre() + "',"
//                    + "apellidos = '" + cliente.getApellidos() + "',"
//                    + "fecha_nacimiento = '" + cliente.getFecha_nacimiento() + "'"
//                    + "where dni = '" + cliente.getDni() + "'";
//
//            result = st.executeUpdate(query);
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return 0;
//    }

//    @Override
//    public int addCliente(Cliente cliente) {
//
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        int result = -1; // ponemos -1 como resultado erróneo o no conseguido
//
//        try (Connection connection = dataSource.getConnection();
//             Statement st = connection.createStatement()) {
//
//            String query = "insert Cliente" + "(nombre, apellidos, DNI, fecha_nacimiento)" + "values (" +
//                    " '" + cliente.getNombre() + "'," +
//                    " '" + cliente.getApellidos() + "'," +
//                    " '" + cliente.getDni() + "'," +
//                    " '" + cliente.getFecha_nacimiento() + "')";
//
//            result = st.executeUpdate(query);
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return 0;
//    }

    @Override
    public int deleteCliente(String DNI) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result = -1; // ponemos -1 como resultado erróneo o no conseguido
        String query = "delete from Cliente where dni = ? ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, DNI);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Cliente getCliente(String dni) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        Cliente cliente = null;
        String query = "select * from Cliente where dni = ? ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, dni); // por cada interrogante es un +1
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            cliente = new Cliente(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5).toLocalDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public int addCliente(Cliente cliente) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result = -1; // ponemos -1 como resultado erróneo o no conseguido
        String query = "insert Cliente(nombre, apellidos, dni, fecha_nacimiento) values ( ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidos());
            preparedStatement.setString(3, cliente.getDni());
            preparedStatement.setDate(4, Date.valueOf(cliente.getFecha_nacimiento()));

            result = preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }
    @Override
    public int updateCliente(Cliente cliente) {

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result = -1; // ponemos -1 como resultado erróneo o no conseguido
        String query = "update Cliente set nombre = ?, apellidos = ?, fecha_nacimiento = ? where dni = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidos());
            preparedStatement.setString(4, cliente.getDni());
            preparedStatement.setDate(3, Date.valueOf(cliente.getFecha_nacimiento()));

            result = preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void eliminarFactura(int numeroFactura) {
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        String query = "call eliminar_factura(?)";

        try (Connection connection = dataSource.getConnection();
        CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setInt(1, numeroFactura);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
