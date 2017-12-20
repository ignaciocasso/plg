package compilador.nodos.expresiones;

import compilador.nodos.NodoAST;
import compilador.nodos.tipos.NodoTipo;

public abstract class NodoExpresion extends NodoAST{
	
	protected NodoTipo tipo;
	public NodoTipo tipo(){
		return tipo;
	};
	
	public abstract boolean esConstante();
	
	public abstract int getIntValue();
}