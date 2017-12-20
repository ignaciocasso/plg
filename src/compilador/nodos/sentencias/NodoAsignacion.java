package compilador.nodos.sentencias;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.variables.NodoVariable;

public class NodoAsignacion extends NodoSentencia{
		
	private final NodoVariable var;
	public NodoVariable var(){return var;}
	
	private final NodoExpresion exp;
	public NodoExpresion exp(){return exp;}

	public NodoAsignacion(NodoVariable var, NodoExpresion exp) {
		this.var = var;
		this.exp = exp;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		var.faseIdentificadores(tabla);
		exp.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		var.faseTipos();
		NodoTipo tipoVar = var.tipo();
		exp.faseTipos();
		NodoTipo tipoExp = exp.tipo();
		if (!tipoVar.equals(tipoExp)) throw new TypeException(this, "Los tipos de los dos lados de la asignacion no coinciden");
		if (var.esConstante()) throw new TypeException(this, "asignacion a constante");
		if (tipoVar.size()!=1) throw new TypeException(this, "solo se pueden realizar asignaciones sobre tipos de tamaño 1");
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		var.generarCodigo(gen);
		exp.generarCodigo(gen);
		gen.add(InstruccionP.STO());
		//solo se pueden hacer asignaciones con tipos de tamaño uno
	}

	public int getMaxStackLength() {
		return  Math.max(var.getMaxStackLength(), exp.getMaxStackLength()+1);
	}

	public int asignarDirecciones(int nextDir, int pa) {return nextDir;}
}
