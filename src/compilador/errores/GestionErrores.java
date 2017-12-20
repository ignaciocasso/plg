package compilador.errores;

import java.util.List;

import compilador.alex.UnidadLexica;
import compilador.nodos.NodoAST;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class GestionErrores {
	public void errorLexico(int fila, int columna, String lexema) {
		System.out.println("Error léxico (fila " + fila + ", columna " + columna + "): Caracter inexperado: "+lexema); 
		System.exit(1);
	}
	
	public void errorSintactico(UnidadLexica encontrada, List<String> esperadas) {
		System.out.println("Error sintáctico (fila "+encontrada.xleft.getLine()+", columna " + encontrada.xleft.getColumn() + ")");
		System.out.print("Elemento inesperado "+encontrada.value);
//		System.out.println(" , se esperaba uno de los siguientes:");
//		for (String s : esperadas){
//			System.out.println(s);
//		}
		System.exit(1);
	}
	
	public static void errorIdentificadores(IdException exception){
		Location loc = exception.getNodo().getLocation();
		int fila = 0;
		int columna = 0;
		if (loc != null){
			fila = loc.getLine();
			columna = loc.getColumn();
		}
		System.out.println("Error de identicadores (id " + exception.getId() + ", fila " + fila
				+ ", columna " + columna + "): "+ exception.getMessage());
		System.exit(1);
	}
	
	public static void errorTipos(TypeException exception){
		Location loc = exception.getNodo().getLocation();
		int fila = 0;
		int columna = 0;
		if (loc != null){
			fila = loc.getLine();
			columna = loc.getColumn();
		}
		System.out.println("Error de tipos (fila " + fila + ", columna " + columna + "): "+ exception.getMessage());
		System.exit(1);
	}
	
}
