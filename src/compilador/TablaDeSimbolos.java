package compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import compilador.errores.IdException;
import compilador.nodos.NodoAST;


public class TablaDeSimbolos {
	/*
	 * Tabla de símbolos basada en una mapa de identificadores a pilas de definiciones.
	 * 
	 */
	
	private class Definicion{ //Clase que encapsula la informacion de una declaracion
		
		String identificador; //identificador
		NodoAST nodo;	//nodo del AST correspondiente a la declaracion
		int nivel; //profundidad de anidamiento
		
		public Definicion(String id, NodoAST nodo, int nivel) {
			this.identificador = id;
			this.nodo = nodo;
			this.nivel = nivel;
		}
	}
	
	@SuppressWarnings("serial") //Alias para Stack<Definition>
	class PilaDefs extends Stack<Definicion> {}
	
	public class Referencia{ 
		private final NodoAST nodo;
		private int pa;
		private final int pa_rel;
		public Referencia(NodoAST nodo, int pa_rel){
			this.nodo = nodo;
			this.pa_rel = pa_rel;
		}
		public NodoAST getDeclaracion() {return nodo;}
		public int getPa() {return pa_rel;}
	}
	
	
	
	// interfaz para tratar los casos en los que el ambito de declaracion es todo el bloque y no
	// solo a partir del punto de declaracion. El nodo que busca el identificador implementa la
	// interfaz y se registra en la lista de pendientes, y se le avisa cuando se encuentra la
	// declaracion o cuando se sabe que no la hay
	public interface Pendiente{
		public void idFound(Referencia nodo) throws IdException;
		public void idNotFound() throws IdException;
	}
	
	class DefPendiente{
		public DefPendiente(Pendiente pendiente, int nivel) {
			this.pendiente = pendiente;
			this.nivel = nivel;
		}
		final Pendiente pendiente;
		final int nivel;
	}

	@SuppressWarnings("serial") //Mapa de identificadores a pilas de definiciones
	class Tabla extends HashMap<String, PilaDefs>{}
	
	private Tabla tabla;
	
	private List<PilaDefs> definiciones; //Lista para guardar las definiciones hechas en un
	//bloque, y asi poder eliminarlas de la tabla al salir de el
	private Stack<List<PilaDefs>> pilaDefiniciones; //Pila para poder guardar la lista de
	//definiciones del bloque al entrar en uno nuevo
	
	private Map<String, List<DefPendiente>> pendientes; //Mapa para registrar las busquedas
	//pendientes en un bloque
	private Stack<Map<String, List<DefPendiente>>> pilaPendientes; //Pila para guardar el mapa
	//de pendientes de un bloque al entrar en uno nuevo
	
	
	private int nivel = 0; //profundidad del bloque
	
	public void inic(){ //Inicializacion de las estructuras de datos
		nivel = 0;
		tabla = new Tabla();
		definiciones = new ArrayList<>();
		pilaDefiniciones = new Stack<>();
		pendientes = new HashMap<>();
		pilaPendientes = new Stack<>();
	}
	
	public void abreBloque(){ //nuevo bloque: se aumenta la profundidad en uno, se apilan
		//las estructuras de datos correspondientes y se crean nuevas
		nivel++;
		pilaDefiniciones.push(definiciones);
		definiciones = new ArrayList<>();
		pilaPendientes.push(pendientes);
		pendientes = new HashMap<>();
	}
	
	public void cierraBloque() throws IdException{ //fin de un bloque: se eliminan las
		//declaraciones del bloque de la tabla, se comprueba si se han definido ya los
		//identificadores pendientes, y se restaura el estado del bloque anterior
		
		
		//Al cerrar un bloque, comprobamos si se han declarado ya los identificadores pendientes.
		//Si lo han hecho, se notifica a los pendientes, y si no, se añaden a los identificadores
		//pendientes del nivel superior, a no ser que se esté ya en el último nivel, en cuyo caso
		//es que no se han declarado
		
		for (PilaDefs p : definiciones){
			Definicion def = p.peek();
			String id = def.identificador;
			if (pendientes.containsKey(id)){ //por cada declaracion, se notifica a los nodos
				//pendientes de ese identificador
				List<DefPendiente> l = pendientes.get(id);
				for (DefPendiente ref : l){
					ref.pendiente.idFound(new Referencia(def.nodo, ref.nivel-def.nivel));
				}
				pendientes.remove(id);
			}
			
			p.pop(); //Se eliminan las declaraciones de la tabla, y la entrada del mapa si la pila queda vacia
			if (p.isEmpty())	{tabla.values().remove(p);} //funciona, los camobios en values se reflejan en keys
		}
		
		//Se añaden los pendientes que quedan en este nivel a los del anterior, a no ser que
		//ya se esté en el nivel máximo, en cuyo caso es que no se han encontrado
		Map<String, List<DefPendiente>> pendientesOld = pilaPendientes.peek();
		for (Entry<String, List<DefPendiente>> entry : pendientes.entrySet()){
			String id = entry.getKey();
			List<DefPendiente> l = entry.getValue();
			if (nivel==1){
				for (DefPendiente defPendiente : l){
					defPendiente.pendiente.idNotFound();
				}
			}
			else{
				if (pendientesOld.containsKey(id)){
					pendientesOld.get(id).addAll(l);
				}
				else{
					pendientesOld.put(id, l);
				}
			}
		}
		
		//Se restaura el estado del bloque anterior
		nivel--;
		definiciones = pilaDefiniciones.pop();
		pendientes = pilaPendientes.pop(); //= pendientesOld
	}
	
	public void insertaId(String id, NodoAST nodo) throws IdException{ //Insertar una declaracion
		//en la tabla
		
		PilaDefs pila;
		
		if (tabla.containsKey(id)){
			pila = tabla.get(id);
			if (pila.peek().nivel == nivel) throw new IdException(nodo, id, "identificador duplicado en un mismo bloque");
		}
		else{
			pila = new PilaDefs();
			tabla.put(id, pila);
		}
		
		pila.push(new Definicion(id, nodo, nivel));
		definiciones.add(pila);
	}

	public Referencia buscaId(String id){
		
		if (tabla.containsKey(id)){
			Definicion def = tabla.get(id).peek();
			return new Referencia(def.nodo, this.nivel - def.nivel);
		}
		else return null;
	}
	
	public void insertaPendiente(String id, Pendiente pendiente){
			
		List<DefPendiente> l;
		
		if (pendientes.containsKey(id)){
			l = pendientes.get(id);
		}
		else{
			l = new ArrayList<>();
			pendientes.put(id, l);
		}
		
		l.add(new DefPendiente(pendiente, nivel));	
	}
}
