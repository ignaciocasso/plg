package compilador.alex;

import java.io.File;
import java.io.IOException;

public class ALexGenerator {

	public static void main(String[] args) throws IOException {
		String[] especificacion_lexica = {"src/compilador/alex/especificacion_lexica.lex"};
		JLex.Main.main(especificacion_lexica);
		new File("src/compilador/alex/especificacion_lexica.lex.java").renameTo
				(new File("src/compilador/alex/AnalizadorLexico.java"));
	}
}
