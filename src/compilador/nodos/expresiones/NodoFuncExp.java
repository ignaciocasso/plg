package compilador.nodos.expresiones;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.nodos.sentencias.NodoCall;

public class NodoFuncExp extends NodoExpresion{
	
	private final NodoCall func;
	
	public NodoFuncExp(NodoCall func){
		this.func = func;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		func.faseIdentificadores(tabla);
	}
	
	public void faseTipos() throws TypeException {
		
		func.faseTipos();
		tipo = func.tipo();
		if (tipo == null) throw new TypeException(this, "la funcion es void");
	}

	public boolean esConstante() {return false;}

	public void generarCodigo(GeneradorDeCodigo gen) {
		func.generarCodigo(gen);
	}

	public int getMaxStackLength() {return 1;}

	public int getIntValue() {
		throw new UnsupportedOperationException();
	}
}
