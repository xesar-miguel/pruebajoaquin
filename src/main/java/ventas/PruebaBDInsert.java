package ventas;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PruebaBDInsert {

	public void connectDatabase() {
		Connection connection = null;
		FileWriter out = null;
		Scanner sc = null;
		int valor = 0;

		try {

			do {
				sc = new Scanner(System.in);
				System.out.print("Introduce un numero: ");
				try {
					valor = sc.nextInt();
				} catch (Exception e) {
					System.out.println("Se debe ingresar un valor entero");
				}
			} while (valor < 0);

			// We register the PostgreSQL driver
			// Registramos el driver de PostgresSQL
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException ex) {
				System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
			}

			// Database connect
			// Conectamos con la base de datos
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ventas", "postgres", "12345");
			System.out.println(!connection.isClosed() ? "TEST OK" : "TEST FAIL");

			// create a Statement from the connection
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO venta (id, nombre, fventa) VALUES " + "(" + valor + ", 'Producto" + valor + "', now())";			
			statement.executeUpdate(sql);

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from venta");

			out = new FileWriter("Registros.txt");

			while (rs.next()) {
				String lastName = rs.getString("nombre");
				out.write(lastName + "\n");
			}

		} catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Error Base de datos: " + sqle);
		} catch (IOException e) {
			System.out.println("Error Input/Output: " + e);
		} catch (Exception general) {
			System.out.println("Error: " + general);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (sc != null) {
				sc.close();
			}
		}
	}

	public static void main(String[] args) {
		PruebaBDInsert javaPostgreSQLBasic = new PruebaBDInsert();
		javaPostgreSQLBasic.connectDatabase();
	}
}
