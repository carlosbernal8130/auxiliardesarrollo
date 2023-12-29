package prueba_auxiliar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class medico {
	private static Statement st;
	private static int registro_med = 0;
	private static String fecha_agenda;
	
	public void agenda() {
		// Pantalla inicial - ingreso de documento de paciente
		try (Scanner teclado = new Scanner(System.in)) {
			System.out.println("CONSULTA CITAS Y MEDIMENTOS \n");
			System.out.println("Digite número de Registro Médico");
			registro_med = teclado.nextInt();
			System.out.println("Digite fecha de agenda a consultar (AAAA-MM-DD)");
			fecha_agenda = teclado.next();
			System.out.println("");
		}
		
		
		Properties propiedades = new Properties();
		try {
			propiedades.load(new FileInputStream(new File("src/prueba_auxiliar/propiedades.properties")));
			Class.forName(propiedades.getProperty("Driver"));
			Connection con = null;
			st = null;
			con = DriverManager.getConnection(propiedades.getProperty("Base"), propiedades.getProperty("Usuario"), 
					propiedades.getProperty("Clave"));
			st = con.createStatement();
			
			//Resultado de medicamentos
			ResultSet rs = st.executeQuery("SELECT c.hora, c.hora_fin, p.nombre, p.apellido from cita c "
					+ "INNER JOIN medico as m ON c.id_medico=m.id INNER JOIN paciente as p ON c.id_paciente=p.id "
					+ "WHERE m.nro_licencia='" + registro_med + "' and c.fecha='" + fecha_agenda + "' order by c.hora;");
			System.out.println("Las citas para médico " + registro_med + " el día "+ fecha_agenda +" son: \n");
			
			System.out.println("Hora inicio" + "\t" + "Hora final" + "\t" + "Nombres y apellidos de paciente");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
			}
			
			//cierre de conexion a BD y statements
			rs.close();
			st.close();
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
