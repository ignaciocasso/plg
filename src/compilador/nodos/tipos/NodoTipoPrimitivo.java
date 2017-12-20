package compilador.nodos.tipos;

import compilador.TablaDeSimbolos;
import compilador.TipoPrimitivo;

public class NodoTipoPrimitivo extends NodoTipo{
	
	private TipoPrimitivo tipo;
	
	public NodoTipoPrimitivo(TipoPrimitivo tipo){
		this.clase = Clase.PRIMITIVO;
		this.tipo = tipo;
		this.size = tipo.size();
	}

	public boolean equals(NodoTipo tipo) {
		return tipo.clase == Clase.PRIMITIVO && ((NodoTipoPrimitivo)tipo).tipo == this.tipo;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) {}
	public void faseTipos() {}
	
	public final static NodoTipoPrimitivo INT = new NodoTipoPrimitivo(TipoPrimitivo.INT);
	public final static NodoTipoPrimitivo BOOL = new NodoTipoPrimitivo(TipoPrimitivo.BOOL);
}