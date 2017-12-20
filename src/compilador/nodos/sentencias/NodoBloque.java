package compilador.nodos.sentencias;

import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;

public class NodoBloque extends NodoSentencia{

	private final NodoSentencias sentencias;
	public NodoSentencias sentencias() {return sentencias;}
	
	public NodoBloque(List<NodoSentencia> listaSentencias) {
		this.sentencias = new NodoSentencias(listaSentencias);
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tabla.abreBloque();
		sentencias.faseIdentificadores(tabla);
		tabla.cierraBloque();
	}

	public void faseTipos() throws TypeException {
		sentencias.faseTipos();
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		sentencias.generarCodigo(gen);
	}

	public int getMaxStackLength() {return sentencias.getMaxStackLength();}
	
	public int asignarDirecciones(int nextDir, int pa) {return sentencias.asignarDirecciones(nextDir, pa);}
}
