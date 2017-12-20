package compilador.nodos.sentencias;

import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.GeneradorDeCodigo.Etiqueta;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipoPrimitivo;

public class NodoWhile extends NodoSentencia{

	private final NodoExpresion cond;
	public NodoExpresion cond() {return cond;}
	
	private final NodoSentencias cuerpo;

	public NodoWhile(NodoExpresion cond, List<NodoSentencia> cuerpo) {
		this.cond = cond;
		this.cuerpo = new NodoSentencias(cuerpo);
	}

	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		cond.faseIdentificadores(tabla);
		tabla.abreBloque();
		cuerpo.faseIdentificadores(tabla);
		tabla.cierraBloque();
	}

	public void faseTipos() throws TypeException {
		cond.faseTipos();
		NodoTipo tipoCond = cond.tipo();
		if (!tipoCond.equals(NodoTipoPrimitivo.BOOL)) throw new TypeException(this, "la expresion del while no es booleana");
		cuerpo.faseTipos();
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		Etiqueta ini = gen.crearEtiqueta("iniWhile");
		Etiqueta fin = gen.crearEtiqueta("finWhile");
		gen.añadirEtiqueta(ini);
		cond.generarCodigo(gen);
		gen.add(InstruccionP.FJP(fin));
		cuerpo.generarCodigo(gen);
		gen.add(InstruccionP.UJP(ini));
		gen.añadirEtiqueta(fin);
	}

	public int getMaxStackLength() {
		return Math.max(cond.getMaxStackLength(), cuerpo.getMaxStackLength());
	}

	public int asignarDirecciones(int nextDir, int pa) {
		return cuerpo.asignarDirecciones(nextDir, pa);
	}
}
