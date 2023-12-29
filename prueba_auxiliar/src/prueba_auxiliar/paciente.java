package prueba_auxiliar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class paciente {
	
	private static Statement st;
	private static Statement st1;
	private static int documento=0;
	
	public void medicamentos() {
		// Pantalla inicial - ingreso de documento de paciente
		try (Scanner teclado = new Scanner(System.in)) {
			System.out.println("CONSULTA CITAS Y MEDICAMENTOS \n");
			System.out.println("Digite número de Documento");
			documento = teclado.nextInt();
			System.out.println("");
		}
		
		//Conexion y consultas que se realizan en BD 
		Properties propiedades = new Properties();
		try {
			//cargue de propiedades
			propiedades.load(new FileInputStream(new File("src/prueba_auxiliar/propiedades.properties")));
			
			Class.forName(propiedades.getProperty("Driver"));
			Connection con = null;
			st = null;
			con = DriverManager.getConnection(propiedades.getProperty("Base"), propiedades.getProperty("Usuario"), 
					propiedades.getProperty("Clave"));
			st = con.createStatement();
			
			//Resultado de medicamentos
			ResultSet rs = st.executeQuery("SELECT m.nombre from cita c INNER JOIN medicamento as m ON c.id=m.id_cita "
					+ "INNER JOIN paciente as p ON c.id_paciente=p.id WHERE p.nro_identificacion='" + documento + "';");
			System.out.println("Sus medicamentos ordenados son:");	
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			
			System.out.println("");
			
			st1 = con.createStatement();
			
			//Resultado de historial de citas
			System.out.println("Historial de citas:");
			ResultSet rs1 = st1.executeQuery("SELECT m.nombre,m.apellido, c.fecha, c.hora, hora_fin from cita c "
					+ "INNER JOIN medico as m ON c.id_medico=m.id INNER JOIN paciente as p ON c.id_paciente=p.id "
					+ "WHERE p.nro_identificacion='" + documento + "';");
			
			System.out.println("Fecha cita" + "\t" + "Hora inicio" + "\t" + "Hora fin" + "\t" + "Nombre y apellido Médico");
			while (rs1.next()) {
				System.out.println(rs1.getString(3) + "\t" + rs1.getString(4) + "\t" + rs1.getString(5) + 
						"\t" + rs1.getString(1) + "\t" + rs1.getString(2));
			}
			
			//cierre de conexion a BD y statements
			rs.close();
			st.close();
			rs1.close();
			st1.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) { //en caso de errores de excepcion para clases y de consulta a la BD
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
}
