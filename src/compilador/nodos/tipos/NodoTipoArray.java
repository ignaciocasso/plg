package compilador.nodos.tipos;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.nodos.expresiones.NodoExpresion;

public class NodoTipoArray extends NodoTipo{

	private final NodoExpresion nodoDimension;
	private int dimension;
	
	private final NodoTipo tipoInferior;
	public NodoTipo getTipoInferior() {return tipoInferior;}
	
	public NodoTipoArray(NodoTipo tipo, NodoExpresion exp) {
		this.clase = Clase.ARRAY;
		this.tipoInferior = tipo;
		nodoDimension = exp;
	}

	public NodoTipoArray(NodoTipo tipo) {
		this.clase = Clase.ARRAY;
		this.tipoInferior = tipo;
		this.nodoDimension = null;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tipoInferior.faseIdentificadores(tabla);
		if (nodoDimension != null) nodoDimension.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		tipoInferior.faseTipos();
		if (nodoDimension != null) {
			nodoDimension.faseTipos();
			if (!nodoDimension.tipo().esEntero()) throw new TypeException(this, "la dimension del array no es una expresion entera");
			if(!nodoDimension.esConstante()) throw new TypeException(this, "dimension del array no constante");
			this.dimension = nodoDimension.getIntValue();
			this.size = tipoInferior.size()*dimension;
		}

	}
	
	public int size() {
		if (nodoDimension == null) throw new UnsupportedOperationException("No se ha especificado el tamaño del array");
		else return this.size;
	}

	public boolean equals(NodoTipo tipo) {
		if (tipo.clase != Clase.ARRAY) {return false;}
		NodoTipoArray tipoArray = ((NodoTipoArray)tipo);
		return tipoArray.tipoInferior.equals(this.tipoInferior);// && tipoArray.dimension==this.dimension;
	}
}
