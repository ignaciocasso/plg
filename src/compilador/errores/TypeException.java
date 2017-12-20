package compilador.errores;

import compilador.nodos.NodoAST;

@SuppressWarnings("serial")
public class TypeException extends Exception{
	private NodoAST nodo;
	public NodoAST getNodo() {return nodo;}
	
	public TypeException(NodoAST nodo, String msg){
		super(msg);
		
		this.nodo = nodo;
	}
}
