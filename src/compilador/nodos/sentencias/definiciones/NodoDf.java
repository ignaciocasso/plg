package compilador.nodos.sentencias.definiciones;

import java.util.Collections;
import java.util.List;

import compilador.nodos.sentencias.NodoSentencia;
import compilador.nodos.sentencias.NodoSentencias;
import compilador.nodos.tipos.NodoTipo;

public abstract class NodoDf extends NodoSentencia{
	
	protected final String identificador;
	public String id() {return identificador;}
	
	protected final List<NodoDv> params;
	public List<NodoDv> params() {return params;}
	
	protected final NodoSentencias cuerpo;
	
	protected int pa;
	public int getPA() {return pa;}
	
	
	public NodoDf(String id, List<NodoDv> args, List<NodoSentencia> cuerpo) {	
		this.identificador = id;
		this.params = Collections.unmodifiableList(args);
		this.cuerpo = new NodoSentencias(cuerpo);
	}
	
	public abstract NodoTipo tipo();
	
	public int getMaxStackLength() {return  0;}
	
	public int asignarDirecciones(){
		int nextDir = this.asignarDireccionesParametros(pa+1);
		return cuerpo.asignarDirecciones(nextDir, pa+1);
	}
	
	public int asignarDireccionesParametros(int pa){
		int nextDir = 5;
		
		for (NodoDv nodo : params){
			
			nextDir = nodo.asignarDirecciones(nextDir, pa);
			
		}
		
		return nextDir;
	}
	
	public int asignarDirecciones(int nextDir, int pa) {
		this.pa = pa;
		return nextDir;
	}
}
