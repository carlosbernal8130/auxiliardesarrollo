package prueba_auxiliar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class paciente {
	
	private static Statement st;
	private static Statement st1;
	private static int documento=0;
	
	public void medicamentos() {
		// Pantalla inicial - ingreso de documento de paciente
		try (Scanner teclado = new Scanner(System.in)) {
			System.out.println("CONSULTA CITAS Y MEDIMENTOS \n");
			System.out.println("Digite número de Documento");
			documento = teclado.nextInt();
			System.out.println("");
		}
		
		//Conexion y consultas que se realizan en BD 
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = null;
			st = null;
			con = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/prueba_auxiliar"
					, "carlos", "majito08");
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
		}
	}
}
