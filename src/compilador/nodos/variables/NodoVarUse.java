package compilador.nodos.variables;

import compilador.TablaDeSimbolos;
import compilador.TablaDeSimbolos.Referencia;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import compilador.generacionCodigo.InstruccionP;
import compilador.nodos.sentencias.definiciones.NodoDv;

public class NodoVarUse extends NodoVariable{

	private final String identificador;
	public String getIdentificador() {return identificador;}
	
	private NodoDv declaracion;
	public NodoDv getDeclaracion() {
		return declaracion;
	}
	public void setDeclaracion(NodoDv declaracion) {
		this.declaracion = declaracion;
	}
	
	private int pa_rel;
	
	private int pa;
	public int getPA(){
		return pa;
	}

	private int direccion;
	public int getDireccion() {
		return direccion;
	}
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}

	public NodoVarUse(String id) {
		identificador = id;
	}
	
	public void faseIdentificadores(TablaDeSimbolos tabla) throws IdException {
		try{
			Referencia ref = tabla.buscaId(identificador);
			if (ref == null) throw new IdException(this, this.identificador, "id no encontrado");
			this.declaracion = (NodoDv)ref.getDeclaracion();
			this.pa_rel = ref.getPa();
		}
		catch (ClassCastException e){
			throw new IdException(this, this.identificador, "el identificador no corresponde a una variable");
		}
	}
	public void faseTipos() throws TypeException {
		tipo = declaracion.tipo();
		//La fase de tipos de la declaracion ya esta hecha, se supone
	}
	public boolean esConstante() {return declaracion.esConstante();}
	
	public void generarCodigo(GeneradorDeCodigo gen) {
		gen.add(InstruccionP.LDA(gen.pa - declaracion.pa(), declaracion.direccion()));
	}
	
	public int getMaxStackLength() {
		return 1;
	}
}
