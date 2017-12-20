package compilador.nodos.variables;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipoArray;

public class NodoAccesoArray extends NodoVariable{
	
	private final NodoVariable variable;	public NodoVariable variable() {return variable;}
	private final NodoExpresion indice;		public NodoExpresion indice() {return indice;}

	public NodoAccesoArray(NodoVariable var, NodoExpresion exp) {
		this.variable = var;
		this.indice = exp;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		variable.faseIdentificadores(tabla);
		indice.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		variable.faseTipos();
		NodoTipo tipoVar = variable.tipo();
		if (!tipoVar.is(NodoTipo.Clase.ARRAY)) throw new TypeException(this, "la variable no es un array");
		indice.faseTipos();
		NodoTipo tipoIndice = indice.tipo();
		if (!tipoIndice.esEntero()) throw new TypeException(this, "el indice no es un numero entero");
		tipo = ((NodoTipoArray)tipoVar).getTipoInferior();
	}

	public boolean esConstante() {return variable.esConstante();}

	public void generarCodigo(GeneradorDeCodigo gen) {
		variable.generarCodigo(gen);
		indice.generarCodigo(gen);
		gen.add(InstruccionP.IXA(variable.tipo().getTipoInferior().size()));
	}

	public int getMaxStackLength() {
		return Math.max(variable.getMaxStackLength(), indice.getMaxStackLength()+1);
	}
}