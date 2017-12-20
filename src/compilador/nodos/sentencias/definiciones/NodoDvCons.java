package compilador.nodos.sentencias.definiciones;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;

public class NodoDvCons extends NodoDv{
	
	private final NodoExpresion valorInicial;
	public final NodoExpresion valorInicial() {return valorInicial;}
	
	public NodoDvCons(String id, NodoTipo tipo, NodoExpresion valor){
		super(id, tipo);
		this.valorInicial = valor;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tabla.insertaId(identificador, this);
		tipo.faseIdentificadores(tabla);
		valorInicial.faseIdentificadores(tabla);
	}
	
	public void faseTipos() throws TypeException {
		tipo.faseTipos();
		valorInicial.faseTipos();
		NodoTipo tipoVI = valorInicial.tipo();
		if(!tipo.equals(tipoVI)) throw new TypeException(this, "error de tipos en inicializacion de variable");
		if (!valorInicial.esConstante()) throw new TypeException(this, "el valor inicial de la variable constante no es constante");
	}

	public boolean esConstante() {return true;}

	public void generarCodigo(GeneradorDeCodigo gen) {
		gen.add(InstruccionP.LDA(0, this.direccion));
		valorInicial.generarCodigo(gen);
		gen.add(InstruccionP.STO());
	}
	
	public int getMaxStackLength() {
		return  1 + valorInicial.getMaxStackLength();
	}
}
