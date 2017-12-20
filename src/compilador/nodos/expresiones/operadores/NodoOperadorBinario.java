package compilador.nodos.expresiones.operadores;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.Instruccion;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;

public abstract class NodoOperadorBinario extends NodoExpresion{

	protected final NodoExpresion operando1, operando2;
	
	public NodoOperadorBinario(NodoExpresion e1, NodoExpresion e2){
		this.operando1 = e1;
		this.operando2 = e2;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		operando1.faseIdentificadores(tabla);
		operando2.faseIdentificadores(tabla);
	}
	
	public boolean esConstante() {return operando1.esConstante() && operando2.esConstante();}
	
	public void faseTipos() throws TypeException {
		operando1.faseTipos();
		operando2.faseTipos();
		NodoTipo tipo1 = operando1.tipo();
		NodoTipo tipo2 = operando2.tipo();
		NodoTipo tipoRet = this.calcularTipo(tipo1, tipo2);
		this.tipo = tipoRet;
	}
	
	public abstract NodoTipo calcularTipo(NodoTipo tipo1, NodoTipo tipo2) throws TypeException;
	
	
	public void generarCodigo(GeneradorDeCodigo gen) {
		operando1.generarCodigo(gen);
		operando2.generarCodigo(gen);
		gen.add(this.instruccion());
	}
	
	public abstract Instruccion instruccion();
	
	public int getMaxStackLength() {
		return Math.max(operando1.getMaxStackLength(), operando2.getMaxStackLength()+1);
	}
	
	public int getIntValue(){
		return this.operar(operando1.getIntValue(), operando2.getIntValue());
	}
	
	public int operar(int n1, int n2){
		throw new UnsupportedOperationException();
	};
}