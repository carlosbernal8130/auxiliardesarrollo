package prueba_auxiliar;

import java.util.Scanner;

public class prueba_java {
	public static void main(String[] args) {
		Scanner teclado=new Scanner(System.in);
		int opcion=0;
		
		System.out.println("Seleccione una opción (numero)");
		System.out.println("(1) Consultar medicamentos de un paciente");
		System.out.println("(2) Agenda de médico");
		
		opcion=teclado.nextInt();
		
		switch (opcion){
			case 1:
				paciente pac = new paciente();
				pac.medicamentos();
			break; 	
			
			case 2:
				medico agenda = new medico();
				agenda.agenda();
			break;
			
			default:
		}
	}

}
