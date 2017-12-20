package compilador.nodos.variables;

import compilador.nodos.NodoAST;
import compilador.nodos.tipos.NodoTipo;

public abstract class NodoVariable extends NodoAST{
	
	protected NodoTipo tipo;
	public NodoTipo tipo(){return tipo;}
	
	public abstract boolean esConstante();
}