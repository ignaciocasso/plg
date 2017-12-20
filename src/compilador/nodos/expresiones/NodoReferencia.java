package compilador.nodos.expresiones;

import compilador.Nodos;
import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.nodos.variables.NodoVariable;

public class NodoReferencia extends NodoExpresion{
	
	private NodoVariable var;
	public NodoReferencia(NodoVariable var){
		this.var = var;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		var.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		var.faseTipos();
		this.tipo = Nodos.nodoTipoPuntero(this.getLocation(), var.tipo());
		if (var.esConstante()) throw new TypeException(this, "no se puede referenciar una constante");
	}

	public boolean esConstante() {
		return true;
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		var.generarCodigo(gen);
	}

	public int getMaxStackLength() {
		return var.getMaxStackLength();
	}

	public int getIntValue() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
