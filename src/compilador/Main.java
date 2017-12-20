package compilador;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import compilador.alex.AnalizadorLexico;
import compilador.asint.AnalizadorSintactico;
import compilador.errores.GestionErrores;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.nodos.NodoPrograma;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

public class Main {

	public static void main(String[] args) throws Exception {

		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexico alex = new AnalizadorLexico(input);
		SymbolFactory factory = new ComplexSymbolFactory();
		
		AnalizadorSintactico asint = new AnalizadorSintactico(alex, factory);
		
		// asint.setScanner(alex);
		Symbol sym = asint.parse();
		NodoPrograma pr = (NodoPrograma) sym.value;
		TablaDeSimbolos tabla = new TablaDeSimbolos();
		try{
			pr.faseIdentificadores(tabla);
		}
		catch (IdException e){
			 GestionErrores.errorIdentificadores(e);
		}
			
		try{
			pr.faseTipos();
		}
		catch (TypeException e){
			GestionErrores.errorTipos(e);
		}
		
		GeneradorDeCodigo gen = new GeneradorDeCodigo();
		pr.generarCodigo(gen);
		gen.toFile("../../maquina-P/programa.txt");
	}
}