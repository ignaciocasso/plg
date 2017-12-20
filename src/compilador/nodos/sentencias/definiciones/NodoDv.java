package compilador.nodos.sentencias.definiciones;

import compilador.nodos.sentencias.NodoSentencia;
import compilador.nodos.tipos.NodoTipo;

public abstract class NodoDv extends NodoSentencia{

	protected final String identificador;
	public String id() {return identificador;}
	protected NodoTipo tipo;
	public NodoTipo tipo(){return tipo;}
	
	protected int direccion;
	public int direccion(){return direccion;}
	public void setDireccion(int dir){this.direccion = dir;}
	
	private int pa_def;
	public int pa() {return pa_def;}

	public NodoDv(String id, NodoTipo tipo) {
		this.identificador = id;
		this.tipo = tipo;
	}
	
	public abstract boolean esConstante();
	
	public int asignarDirecciones(int nextDir, int pa) {
		direccion = nextDir;
		pa_def = pa;
		return nextDir + tipo.size();
	}
}