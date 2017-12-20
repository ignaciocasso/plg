package compilador.nodos.sentencias.definiciones;

import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.generacionCodigo.GeneradorDeCodigo.Etiqueta;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.sentencias.NodoSentencia;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipo.Clase;

public class NodoDecFunc extends NodoDf{
	
	private final NodoExpresion retorno;
	public NodoExpresion getReturn() {return retorno;}
	
	private final NodoTipo tipoRetorno;
	public NodoTipo getTipoReturn() {return tipoRetorno;}
	
	public NodoDecFunc(String id, List<NodoDv> args, List<NodoSentencia> cuerpo, NodoTipo tipoRetorno, NodoExpresion retorno) {
		super(id, args, cuerpo);
		this.retorno = retorno;
		this.tipoRetorno = tipoRetorno;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tabla.insertaId(identificador, this);
		tipoRetorno.faseIdentificadores(tabla);
		tabla.abreBloque();
		for (NodoDv arg : params){
			arg.faseIdentificadores(tabla);
		}
		cuerpo.faseIdentificadores(tabla);
		retorno.faseIdentificadores(tabla);
		tabla.cierraBloque();
	}

	public void faseTipos() throws TypeException {
		for (NodoDv dv : params){
			dv.faseTipos();
		}
		cuerpo.faseTipos();
		tipoRetorno.faseTipos();
		retorno.faseTipos();
		NodoTipo tipoRetReal = retorno.tipo();
		if (!tipoRetorno.equals(tipoRetReal)) throw new TypeException(this, "tipo del return incorrecto");
		if (!tipoRetorno.is(Clase.PRIMITIVO)) throw new TypeException(this, "el tipo de retorno debe ser un tipo primitivo");
	}


	public boolean esFuncion() {return true;}

	public NodoTipo tipo() {return tipoRetorno;}

	public void generarCodigo(GeneradorDeCodigo gen) {
		
		int initialStackSize = this.asignarDirecciones();
		int expressionStackLength = Math.max(cuerpo.getMaxStackLength(), retorno.getMaxStackLength());
		
		gen.iniFuncion(this);
		gen.add(InstruccionP.SSP(initialStackSize));
		gen.add(InstruccionP.SEP(expressionStackLength));
		Etiqueta etiqueta = gen.crearEtiqueta("cuerpo_" + id());
		gen.add(InstruccionP.UJP(etiqueta));
		gen.setSeccionProcedimientos();
		gen.añadirEtiqueta(etiqueta);

		cuerpo.generarCodigo(gen);
		retorno.generarCodigo(gen);
		gen.add(InstruccionP.STR(0,0));
		gen.add(InstruccionP.RETF());
		
		gen.finFuncion();
	}
}
