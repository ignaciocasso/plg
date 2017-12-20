package compilador.nodos.expresiones.operadores;

import compilador.errores.TypeException;
import compilador.generacionCodigo.Instruccion;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipoPrimitivo;

public class NodoNot extends NodoOperadorUnario{

	public NodoNot(NodoExpresion e) {
		super(e);
	}

	public NodoTipo calcularTipo(NodoTipo tipo) throws TypeException {
		if (!tipo.esBooleano()){
			throw new TypeException(this, "tipos incompatibles con el operandor !");
		}
		return NodoTipoPrimitivo.BOOL;
	}

	public Instruccion instruccion() {return InstruccionP.NOT();}
}
