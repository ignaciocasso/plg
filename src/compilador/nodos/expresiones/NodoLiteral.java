package compilador.nodos.expresiones;

import compilador.TablaDeSimbolos;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.tipos.NodoTipoPrimitivo;

public class NodoLiteral extends NodoExpresion{

	private final String value;
	
	public NodoLiteral(NodoTipoPrimitivo tipo, String val) {
		this.tipo = tipo;
		this.value = val;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla){}
	public void faseTipos(){}

	public boolean esConstante() {return true;}

	public void generarCodigo(GeneradorDeCodigo gen) {
		gen.add(InstruccionP.LDC(value)); //Solo hay literales para tipos primitivos
	}

	public int getMaxStackLength() {return 1;}

	public int getIntValue() {
		return Integer.parseInt(value);
	}
}