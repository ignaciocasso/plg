package compilador.nodos.sentencias;

import java.util.List;

import compilador.TablaDeSimbolos;
import compilador.TablaDeSimbolos.Referencia;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.sentencias.definiciones.NodoDf;
import compilador.nodos.sentencias.definiciones.NodoDv;
import compilador.nodos.tipos.NodoTipo;

public class NodoCall extends NodoSentencia{
	
	private final String identificador;
	public String id() {return identificador;}
	
	private final List<NodoExpresion> params;
	public List<NodoExpresion> params() {return params;}
	
	private NodoDf declaracion = null;
	
	private int pa_rel;
	
	public NodoCall(String id, List<NodoExpresion> args) {
		this.identificador = id;
		this.params = args;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		
		for (NodoExpresion exp : params){
			exp.faseIdentificadores(tabla);
		}
		
		Referencia ref = tabla.buscaId(identificador);
		if (ref!=null){	
			try{
				this.declaracion = (NodoDf)ref.getDeclaracion();
				this.pa_rel = ref.getPa();
			}
			catch (ClassCastException e){
				throw new IdException(this, this.identificador, "el identificador no corresponde a una funcion");
			}
		}
		else{
			
			tabla.insertaPendiente(identificador, new TablaDeSimbolos.Pendiente() {
			
				public void idNotFound() throws IdException {
					throw new IdException(NodoCall.this, NodoCall.this.identificador, "declaracion no encontrada");
				}
				
				public void idFound(Referencia ref) throws IdException{
					try {
						declaracion = (NodoDf) ref.getDeclaracion();
						pa_rel = ref.getPa();
					}
					catch (ClassCastException e){
						throw new IdException(NodoCall.this, NodoCall.this.identificador, "el identificador no corresponde a un procedimiento o funcion");
					}
				}
			});	
		}
	}
	
	public void faseTipos() throws TypeException {
		List<NodoDv> decParams = declaracion.params();
		if (decParams.size()!=params.size()) throw new TypeException(this, "llamada a funcion con numero incorrecto de paramentros");
		for (int i=0; i<params.size(); i++){
			params.get(i).faseTipos();
			NodoTipo tipoParam = params.get(i).tipo();
			if (!tipoParam.equals(decParams.get(i).tipo())) throw new TypeException(this, "parametro " + decParams.get(i).id() + " de tipo incorrecto");
		}
	}
	
	public NodoTipo tipo(){return declaracion.tipo();}

	
	public void generarCodigo(GeneradorDeCodigo gen) {
		gen.add(InstruccionP.MST(gen.pa - declaracion.getPA()));
		int sizeParams = 0;
		for (NodoExpresion param : params){
			param.generarCodigo(gen);
			int sizeParam = param.tipo().size();
			sizeParams += sizeParam;
		}
		gen.add(InstruccionP.CUP(sizeParams, gen.getEtiqueta(declaracion)));
														//etiqueta de la funcion
		
		//El nodo call sirve para llamar tanto a funciones como procedimientos, y nuestro
		//lenguaje permite llamar a funciones como si fueran procedimientos. En ese caso,
		//habría que desapilar el valor de retorno de la pila. Por ahora no lo haremos, ya
		//que como no hay instruccion-p para eso habría que reestructurar nuestro ast, y lo
		//único que este error produce es pérdida de memoria y por lo demas no altera el
		//comportamiento de nuestro programa.
	}

	public int getMaxStackLength() {
		int max=0;
		int size=0;
		for (NodoExpresion e : params){
			max = Math.max(max, size + e.getMaxStackLength());
			size += e.tipo().size();
		}
		return max;
	}

	public int asignarDirecciones(int nextDir, int pa) {return nextDir;}
}
