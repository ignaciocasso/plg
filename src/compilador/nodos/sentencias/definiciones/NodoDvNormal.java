package compilador.nodos.sentencias.definiciones;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.nodos.tipos.NodoTipo;

public class NodoDvNormal extends NodoDv{

	public NodoDvNormal(String id, NodoTipo tipo) {
		super(id, tipo);
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tabla.insertaId(identificador, this);
		tipo.faseIdentificadores(tabla);
	}

	public void faseTipos() throws TypeException {
		tipo.faseTipos();
	}

	public boolean esConstante() {return false;}

	public void generarCodigo(GeneradorDeCodigo gen) {}

	public int getMaxStackLength() {return 0;}
}
