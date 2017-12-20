package compilador.nodos.expresiones;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.sentencias.definiciones.NodoDv;
import compilador.nodos.sentencias.definiciones.NodoDvCons;
import compilador.nodos.variables.NodoVarUse;
import compilador.nodos.variables.NodoVariable;

public class NodoVarExp extends NodoExpresion{
	
	private final NodoVariable var;

	public NodoVarExp(NodoVariable var) {
		this.var = var;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		var.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException{
		var.faseTipos();
		tipo = var.tipo();
	}

	public boolean esConstante() {return var.esConstante();}

	public void generarCodigo(GeneradorDeCodigo gen) {
		var.generarCodigo(gen);
		if (tipo.size()==1){
			gen.add(InstruccionP.IND());
		}
		else{
			gen.add(InstruccionP.MOVS(tipo.size()));
		}
	}

	public int getMaxStackLength() {
		return var.getMaxStackLength();
	}

	public int getIntValue() {
		
		NodoDv dv = ((NodoVarUse) var).getDeclaracion();
		return (((NodoDvCons)dv).valorInicial().getIntValue());
	}
}
