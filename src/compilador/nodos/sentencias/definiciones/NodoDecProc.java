package compilador.nodos.sentencias.definiciones;

import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.generacionCodigo.GeneradorDeCodigo.Etiqueta;
import compilador.nodos.sentencias.NodoSentencia;
import compilador.nodos.tipos.NodoTipo;

public class NodoDecProc extends NodoDf{
	
	public NodoDecProc(String id, List<NodoDv> args, List<NodoSentencia> cuerpo) {
		super(id, args, cuerpo);
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tabla.insertaId(identificador, this);
		tabla.abreBloque();
		for (NodoDv arg : params){
			arg.faseIdentificadores(tabla);
		}
		cuerpo.faseIdentificadores(tabla);
		tabla.cierraBloque();
	}

	public void faseTipos() throws TypeException {
		for (NodoDv dv : params){
			dv.faseTipos();
		}
		cuerpo.faseTipos();
	}

	public boolean esProcedimiento() {return true;}

	public NodoTipo tipo() {return null;}

	public void generarCodigo(GeneradorDeCodigo gen) {
		
		int initialStackSize = this.asignarDirecciones();
		int expressionStackLength = cuerpo.getMaxStackLength();
		
		gen.iniFuncion(this);
		gen.add(InstruccionP.SSP(initialStackSize));
		gen.add(InstruccionP.SEP(expressionStackLength));
		Etiqueta etiqueta = gen.crearEtiqueta("cuerpo_" + id());
		gen.add(InstruccionP.UJP(etiqueta));
		gen.setSeccionProcedimientos();
		gen.añadirEtiqueta(etiqueta);

		cuerpo.generarCodigo(gen);
		
		gen.add(InstruccionP.RETP());
		
		gen.finFuncion();
	}
}
