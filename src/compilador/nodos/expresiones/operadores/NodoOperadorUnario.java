package compilador.nodos.expresiones.operadores;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.Instruccion;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;

public abstract class NodoOperadorUnario extends NodoExpresion{
	
	protected final NodoExpresion operando;
	
	public NodoOperadorUnario(NodoExpresion e){
		this.operando = e;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		operando.faseIdentificadores(tabla);
	}
	
	public boolean esConstante() {return operando.esConstante();}
	
	public void faseTipos() throws TypeException {
		operando.faseTipos();
		NodoTipo tipo = operando.tipo();
		NodoTipo tipoRet = this.calcularTipo(tipo);
		this.tipo = tipoRet;
	}
	
	public abstract NodoTipo calcularTipo(NodoTipo tipo) throws TypeException;
	
	public void generarCodigo(GeneradorDeCodigo gen) {
		operando.generarCodigo(gen);
		gen.add(this.instruccion());
	}
	
	public abstract Instruccion instruccion();
	
	public int getMaxStackLength() {
		return operando.getMaxStackLength();
	}
	
	public int getIntValue(){
		throw new UnsupportedOperationException();
	}
}
