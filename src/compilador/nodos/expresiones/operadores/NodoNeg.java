package compilador.nodos.expresiones.operadores;

import compilador.errores.TypeException;
import compilador.generacionCodigo.Instruccion;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipoPrimitivo;

public class NodoNeg extends NodoOperadorUnario{
	
	public NodoNeg(NodoExpresion e) {
		super(e);
	}

	public NodoTipo calcularTipo(NodoTipo tipo) throws TypeException {
		if (!tipo.esEntero()){
			throw new TypeException(this, "tipos incompatibles con el operandor -");
		}
		return NodoTipoPrimitivo.INT;
	}
	
	public Instruccion instruccion() {return InstruccionP.NEG();}
	
	public int getIntValue(){
		return -this.operando.getIntValue();
	}
}
