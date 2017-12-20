package compilador.nodos.sentencias;

import compilador.nodos.NodoAST;

public abstract class NodoSentencia extends NodoAST{
	
	public abstract int asignarDirecciones(int nextDir, int pa);
}
