package compilador.nodos.tipos;

import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.nodos.NodoAST;

public abstract class NodoTipo extends NodoAST{
	
	public enum Clase{
		PRIMITIVO,
		ARRAY,
		PUNTERO;
	}
	
	public Clase clase;
	public boolean is(Clase clase){return clase == this.clase;}
	
	public abstract boolean equals(NodoTipo tipo);
	
	protected int size;
	public int size() {return size;}
	
	public boolean esEntero() {return this.equals(NodoTipoPrimitivo.INT);}
	public boolean esBooleano() {return this.equals(NodoTipoPrimitivo.BOOL);}
	
	public void generarCodigo(GeneradorDeCodigo gen) {
		throw new UnsupportedOperationException();
	}
	
	public int getMaxStackLength() {return 0;}

	public NodoTipo getTipoInferior() {
		throw new UnsupportedOperationException();
	}
}
