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

public class NodoIfElse extends NodoSentencia{

	private final NodoExpresion cond;
	public NodoExpresion cond() {return cond;}
	
	private NodoSentencias cuerpo1, cuerpo2;

	public NodoIfElse(NodoExpresion cond, List<NodoSentencia> cuerpo1, List<NodoSentencia> cuerpo2) {
		this.cond = cond;
		this.cuerpo1 = new NodoSentencias(cuerpo1);
		this.cuerpo2 = new NodoSentencias(cuerpo2);
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		cond.faseIdentificadores(tabla);
		tabla.abreBloque();
		cuerpo1.faseIdentificadores(tabla);
		tabla.cierraBloque();
		tabla.abreBloque();
		cuerpo2.faseIdentificadores(tabla);
		tabla.cierraBloque();
	}
	
	public void faseTipos() throws TypeException {
		cond.faseTipos();
		NodoTipo tipoCond = cond.tipo();
		if (!tipoCond.equals(NodoTipoPrimitivo.BOOL)) throw new TypeException(this, "la expresion del while no es booleana");
		cuerpo1.faseTipos();
		cuerpo2.faseTipos();
	}

	public void generarCodigo(GeneradorDeCodigo gen) {
		cond.generarCodigo(gen);
		Etiqueta els = gen.crearEtiqueta("else");
		Etiqueta fin = gen.crearEtiqueta("fin");
		gen.add(InstruccionP.FJP(els));
		cuerpo1.generarCodigo(gen);
		gen.add(InstruccionP.UJP(fin));
		gen.añadirEtiqueta(els);
		cuerpo2.generarCodigo(gen);
		gen.añadirEtiqueta(fin);
	}

	public int getMaxStackLength() {
		return Math.max(cond.getMaxStackLength(), Math.max(cuerpo1.getMaxStackLength(), cuerpo2.getMaxStackLength()));
	}
	
	public int asignarDirecciones(int nextDir, int pa) {
		int n = cuerpo1.asignarDirecciones(nextDir, pa);
		return cuerpo2.asignarDirecciones(n, pa);
	}
}
