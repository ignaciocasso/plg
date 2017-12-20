package compilador.nodos.variables;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipo.Clase;

public class NodoAccesoPuntero extends NodoVariable{

	private NodoVariable var;	
	
	public NodoAccesoPuntero(NodoVariable var) {
		this.var = var;
	}

	public boolean esConstante() {
		return false;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		var.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		var.faseTipos();
		NodoTipo varTipo = var.tipo;
		if (!varTipo.is(Clase.PUNTERO)) throw new TypeException(this, "La variable no es un puntero");
		this.tipo = varTipo.getTipoInferior();
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		var.generarCodigo(gen);
		gen.add(InstruccionP.IND());
	}

	public int getMaxStackLength() {
		return var.getMaxStackLength();
	}
}
