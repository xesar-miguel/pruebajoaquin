package ventas;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaBD {

	public void connectDatabase() {
		Connection connection = null;
		FileWriter out=null;
		try {
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

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from venta");
			
			
			out=new FileWriter("Registros.txt");

			while (rs.next()) {
				String lastName = rs.getString("nombre");
				out.write(lastName + "\n");
			}			

		} catch (java.sql.SQLException sqle) {
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
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		PruebaBD javaPostgreSQLBasic = new PruebaBD();
		javaPostgreSQLBasic.connectDatabase();
	}
}
