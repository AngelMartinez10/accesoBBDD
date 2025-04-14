package connection;

import model.AlmacenDB;
import model.Cliente;
import model.ClienteDB;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MyDataSource.conectarMYSQL();

        AlmacenDB datos = new ClienteDB();
        List<Cliente> clientes = datos.getAllClientes();
        System.out.println(clientes);

        Cliente cliente = new Cliente("JOAQUIN", "ALONSO SAIZ", "53052298S", LocalDate.now());
        datos.updateCliente(cliente);

        Cliente cliente1 = new Cliente("XAVIER", "ROSILLO", "123X", LocalDate.of(1993,2,14));

//        datos.addCliente(cliente1);
//        datos.deleteCliente("123X");
        System.out.println(clientes);

//        System.out.println(datos.getCliente("123X"));
        System.out.println(datos.articulosPrecio(100));
        System.out.println(datos.crearFactura(1));
    }

}
