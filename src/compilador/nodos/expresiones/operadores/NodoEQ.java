package compilador.nodos.expresiones.operadores;

import compilador.errores.TypeException;
import compilador.generacionCodigo.Instruccion;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipoPrimitivo;

public class NodoEQ extends NodoOperadorBinario{

	public NodoEQ(NodoExpresion e1, NodoExpresion e2) {
		super(e1, e2);
	}

	public NodoTipo calcularTipo(NodoTipo tipo1, NodoTipo tipo2) throws TypeException {
		if (!tipo1.equals(tipo2)){
			throw new TypeException(this, "tipos incompatibles con el operandor ==");
		}
		return NodoTipoPrimitivo.BOOL;
	}

	public Instruccion instruccion() {return InstruccionP.EQU();}
}
