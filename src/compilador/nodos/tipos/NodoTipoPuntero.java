package compilador.nodos.tipos;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;

public class NodoTipoPuntero extends NodoTipo{
	
	private NodoTipo tipoInferior;
	
	public NodoTipoPuntero(NodoTipo tipoInferior){
		this.tipoInferior = tipoInferior;
		this.clase = Clase.PUNTERO;
		this.size = 1;
	}

	public boolean equals(NodoTipo tipo) {
		return tipo.clase==Clase.PUNTERO && tipo.getTipoInferior().equals(this.tipoInferior);
	}
	
	public NodoTipo getTipoInferior(){
		return this.tipoInferior;
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tipoInferior.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		tipoInferior.faseTipos();
	}
}
