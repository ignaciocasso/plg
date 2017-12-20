package compilador.nodos.sentencias;

import java.util.Collections;
import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.nodos.NodoAST;

public class NodoSentencias extends NodoAST{

	public List<NodoSentencia> sentencias;
	public List<NodoSentencia> sentencias() {return sentencias;}
	
	public NodoSentencias(List<NodoSentencia> sentencias) {
		this.sentencias = Collections.unmodifiableList(sentencias);
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		for (NodoSentencia sentencia : sentencias()){
			sentencia.faseIdentificadores(tabla);
		}
	}

	public void faseTipos() throws TypeException {
		for (NodoSentencia sentencia : sentencias){
			sentencia.faseTipos();;
		}
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		for (NodoSentencia sentencia :sentencias){
			sentencia.generarCodigo(gen);
		}
	}

	public int getMaxStackLength() {
		int maxLength = 0;
		for (NodoSentencia sentencia : sentencias){
			maxLength = Math.max(maxLength, sentencia.getMaxStackLength());
		}
		return maxLength;
	}
	
	public int asignarDirecciones(int nextDir, int pa){
		
		int nextDirection = nextDir;
		for (NodoSentencia sentencia : sentencias){
			nextDirection = sentencia.asignarDirecciones(nextDirection, pa);
		}
		
		return nextDirection;
	}
}
