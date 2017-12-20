package compilador.errores;

import compilador.nodos.NodoAST;

@SuppressWarnings("serial")
public class IdException extends Exception{
	
	private NodoAST nodo;
	public NodoAST getNodo() {return nodo;}
	
	private String id;
	public String getId() {return id;}

	public IdException(String msg) {
		super(msg);
	}

	public IdException(NodoAST nodo, String id, String msg) {
		super(msg);
		this.nodo = nodo;
		this.id = id;
	}

}
