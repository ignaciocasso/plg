package compilador.nodos;

import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.Instruccion;
import compilador.generacionCodigo.InstruccionP;
import compilador.generacionCodigo.GeneradorDeCodigo.Etiqueta;
import compilador.nodos.sentencias.NodoSentencia;
import compilador.nodos.sentencias.NodoSentencias;

public class NodoPrograma extends NodoAST{

	public final NodoSentencias sentencias;
	
	public NodoPrograma(List<NodoSentencia> listaSentencias) {
		sentencias = new NodoSentencias(listaSentencias);
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		tabla.inic();
		tabla.abreBloque();
		sentencias.faseIdentificadores(tabla);
		tabla.cierraBloque();
	}

	public void faseTipos() throws TypeException {
		sentencias.faseTipos();
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		
		int maxStackLength = sentencias.getMaxStackLength();
		int initialStackSize = sentencias.asignarDirecciones(5, 1);
		
		gen.inic(initialStackSize);

		gen.add(InstruccionP.SSP(initialStackSize));
		gen.add(InstruccionP.SEP(maxStackLength));
		Etiqueta et = gen.crearEtiqueta("main");
		gen.add(InstruccionP.UJP(et));
		gen.añadirEtiqueta(et);
		gen.setSeccionProcedimientos();
		
		sentencias.generarCodigo(gen);
		
		gen.add(new Instruccion(InstruccionP.STP));
	}

	public int getMaxStackLength() {
		return sentencias.getMaxStackLength();
	}
}
