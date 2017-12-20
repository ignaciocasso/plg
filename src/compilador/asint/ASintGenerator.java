package compilador.asint;

import java.io.File;
import java.io.IOException;

import java_cup.internal_error;

public class ASintGenerator {

	public static void main(String[] args) throws internal_error, IOException, Exception {
		String[] argumentos = {"-parser", "AnalizadorSintactico",
							   "-symbols", "ClaseLexica",
							   "-locations",
							   "src/compilador/asint/especificacion_sintactica.cup"};
		java_cup.Main.main(argumentos);
		new File("AnalizadorSintactico.java").renameTo
			(new File("src/compilador/asint/AnalizadorSintactico.java"));
		new File("ClaseLexica.java").renameTo
		(new File("src/compilador/asint/ClaseLexica.java"));
	}
}