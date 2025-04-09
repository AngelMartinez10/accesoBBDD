package connection;

import model.AlmacenDB;
import model.Cliente;
import model.ClienteDB;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        MyDataSource.conectarMYSQL();

        AlmacenDB datos = new ClienteDB();
        List<Cliente> clientes = datos.getAllClientes();
        System.out.println(clientes);

    }

}
