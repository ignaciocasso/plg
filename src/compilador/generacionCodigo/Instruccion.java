package compilador.generacionCodigo;

import compilador.generacionCodigo.GeneradorDeCodigo.Etiqueta;

//Clase que representa una instrucción-p
public class Instruccion {
	
	private InstruccionP ins;
	public InstruccionP getIns(){
		return ins;
	}
	
	private String param1 = "";
	public void setParam1(String st){
		this.param1 = st;
	}
	
	private String param2 = "";
	public void setParam2(String st){
		this.param2 = st;
	}
	
	private Etiqueta etiqueta = null;
	public Etiqueta getEtiqueta(){
		return etiqueta;
	}
	
	public Instruccion(InstruccionP ins){
		this.ins = ins;
	}
	
	public Instruccion(InstruccionP ins, String p){
		this.ins = ins;
		this.param1 = p;
	}
	
	public Instruccion(InstruccionP ins, String p1, String p2){
		this.ins = ins;
		this.param1 = p1;
		this.param2 = p2;
	}
	
	public Instruccion(InstruccionP ins, Etiqueta et) {
		this.ins = ins;
		this.etiqueta = et;
	}
	public Instruccion(InstruccionP ins, String st, Etiqueta etiqueta) {
		this.ins = ins;
		this.param1 = st;
		this.etiqueta = etiqueta;
	}
	public String toString(){
		return ins.toString().toLowerCase() + " " + param1 + " " + param2 + ";"; //No hace daño tener espacios al final
	}
		
}
