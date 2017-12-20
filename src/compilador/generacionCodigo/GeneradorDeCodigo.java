package compilador.generacionCodigo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import compilador.nodos.sentencias.definiciones.NodoDf;

public class GeneradorDeCodigo {
	
	public class Etiqueta{	//Clase que encapsula la informaci�n de una etiqueta: su nombre 
							//y el n�mero de la instrucci�n que representa
		final String nombre;
		private int num;
		private Etiqueta(String etiqueta) {
			this.nombre = etiqueta;
		}
	}
	
	private class InstruccionEnlazada{	//Nodo de una lista enlazada de instrucciones
		
		public InstruccionEnlazada(Instruccion instruccion) {
			this.instruccion = instruccion;
		}

		private InstruccionEnlazada next = null;
		
		private Instruccion instruccion;
		
		private Etiqueta etiqueta;
	}
	
	private class ListaInstrucciones{	//Lista enlazada de instrucciones
		
		private InstruccionEnlazada primeraInstruccion;
		private InstruccionEnlazada ultimaInstruccion;
		private Etiqueta etiqueta = null;
		
		
		public void add(Instruccion instruccion) {
			
			InstruccionEnlazada inst = new InstruccionEnlazada(instruccion);
			if (primeraInstruccion != null){
				ultimaInstruccion.next = inst;
				ultimaInstruccion = inst;
			}
			else{
				primeraInstruccion = inst;
				ultimaInstruccion = inst;
			}
			
			if (etiqueta != null) {
				inst.etiqueta = etiqueta;
				etiqueta = null;
			}
		}
		
		public void a�adirEtiqueta(Etiqueta et) {
			etiqueta = et;
		}

		
		//Este m�todo intercala otra lista enlazada en esta lista a la altura de un nodo dado,
		//que se asume que est� en la lista
		public void insertar(ListaInstrucciones lista, InstruccionEnlazada ins) {
			
			if (ins == this.ultimaInstruccion){
				ins.next = lista.primeraInstruccion;
				this.ultimaInstruccion = lista.ultimaInstruccion;
			}
			//si la instrucci�n dada era la �ltima de la lista y hab�a una etiqueta pendiente,
			//no se asigna a la primera instrucci�n de la lista recibida, se queda pendiente para
			//la siguiente instrucci�n que se a�ada.
			else {
				InstruccionEnlazada next = ins.next;
				ins.next = lista.primeraInstruccion;
				lista.ultimaInstruccion.next = next;
			}
		}
	}
	
	private ListaInstrucciones instrucciones; //lista de instrucciones donde los nodos del
	//AST van insertando el codigo-p que generan
	
	private Stack<ListaInstrucciones> pilaListaInstrucciones; //pila de listas de instrucciones
	//necesaria para generar c�digo de funciones. Cuando se va a generar el c�digo de una funci�n
	//se apila la lista con la que se estaba trabajando y se crea una nueva
	
	private InstruccionEnlazada seccionProcedimientos; //Instrucci�n de la lista de instrucciones
	//en uso donde va el c�digo del siguiente procedimiento anidado que se encuentre
	
	private Stack<InstruccionEnlazada> pilaSeccionProcedimientos; //pila donde apilar la variable
	//anterior cuando se empieza a generar el c�digo de un nuevo procedimiento
	
	
	public void add(Instruccion instruccion){
		instrucciones.add(instruccion);
	}
	
	public Etiqueta crearEtiqueta(String nombre){
		Etiqueta etiqueta = new Etiqueta(nombre);
		return etiqueta;
	}
	
	public void a�adirEtiqueta(Etiqueta et){
		instrucciones.a�adirEtiqueta(et);
	}
	
	private Map<NodoDf, Etiqueta> funciones; //Mapa donde se guardan las etiquetas correspondientes
	//a cada declaraci�n de funci�n. Podr�a guardarse como atributos en los nodosDf y de hecho ser�a
	//m�s eficiente, pero no quiero sobrecargar los nodos de atributos a�n m�s
	
	public int pa; //Profundidad de anidamiento de la secci�n de c�digo que se est� generando
	//Es necesaria para generar las direcciones de las variables, y s�lo aumenta al entrar en
	//una funci�n o procedimiento, por lo que no podr�a haberse guardado como atributo en la
	//fase de identificadores ya que entonces tambi�n contaban los cuerpos de ifs y whiles como
	//un nivel m�s de profundidad
	
	public void inic(int size){ //Inicializaci�n de las estructuras
		funciones = new HashMap<>();
		instrucciones = new ListaInstrucciones();
		pilaListaInstrucciones = new Stack<>();
		pilaSeccionProcedimientos = new Stack<>();
		
		pa = 1;
	}
	
	public void iniFuncion(NodoDf declaracion){ //instrucciones a ejecutar cuando se empieza a generar
		//el c�digo de una nueva funci�n.
		
		//Se crea la etiqueta de la funci�n y se a�ada al mapa. Es posible que ya estuviese creada,
		//ya que si hubiese alguna llamada a la funci�n anterior a su declaraci�n, lo cual permite
		//nuestro lenguaje, ya se habr�a generado el c�digo de la llamada, que necesitar�a la etiqueta
		Etiqueta et;
		if (funciones.containsKey(declaracion)){
			et = funciones.get(declaracion);
		}
		else {
			et = this.crearEtiqueta(declaracion.id());
			funciones.put(declaracion, et);
		}
		
		//Se crea una nueva lista de instrucciones y se a�ade la anterior
		this.pilaListaInstrucciones.push(this.instrucciones);
		instrucciones = new ListaInstrucciones();
		
		//Se a�ade la etiqueta a la nueva lista. Las instrucciones de salto a esta etiqueta
		//saltar�n a la siguiente instrucci�n que se a�ada a la lista
		this.a�adirEtiqueta(et);
		
		//Se apila la anterior secci�n de procedimientos.
		this.pilaSeccionProcedimientos.push(this.seccionProcedimientos);
		this.seccionProcedimientos = null;
		
		pa++;
	}
	
	//instrucciones a ejecutar cuando se acaba de generar c�digo para una funci�n
	//Se inserta la lista de instrucciones generadas en la secci�n de procedimientos
	// del nivel anterior, y se restaura el estado (pa, instrucciones, seccionProcedimientos)
	//del nivel anterior
	public void finFuncion(){
		
		pa--;
		
		this.seccionProcedimientos = this.pilaSeccionProcedimientos.pop();	
		ListaInstrucciones instruccionesOld = pilaListaInstrucciones.pop();
		instruccionesOld.insertar(instrucciones, seccionProcedimientos);
		this.seccionProcedimientos = instrucciones.ultimaInstruccion;
		
		instrucciones = instruccionesOld;
	}
	
	//Indica que la �ltima instrucci�n a�adida es la que se�ala la secci�n de procedimientos
	//de este nivel
	public void setSeccionProcedimientos(){
		this.seccionProcedimientos = this.instrucciones.ultimaInstruccion;
	}
	
	//Devuelve la etiqueta correspondiente al c�digo de una funci�n. Si no est�, la crea
	public Etiqueta getEtiqueta(NodoDf declaracion){
		if (funciones.containsKey(declaracion)){
			return funciones.get(declaracion);
		}
		else{
			Etiqueta et = this.crearEtiqueta(declaracion.id());
			funciones.put(declaracion, et);
			return et;
		}
	}
	
	//Sustituye las etiquetas usadas por las instrucciones de salto por el n�mero de la
	//instrucci�n a la que apuntan
	public void resolverEtiquetas(){
		
		//primero recorre todas las instrucciones actualizando el numero de instrucci�n de las
		//etiquetas que aparecen
		int i = 0;
		InstruccionEnlazada ins = instrucciones.primeraInstruccion;
		while (ins!=null){
			if (ins.etiqueta != null){
				ins.etiqueta.num = i;
			}
			ins = ins.next;
			i++;
		}
		
		//Despu�s vuelve a recorres la lista sustituyendo las etiquetas de las instrucciones
		//de salto por su valor
		ins = instrucciones.primeraInstruccion;
		while (ins!=null){
			Instruccion inst = ins.instruccion;
			if (inst.getEtiqueta() != null){
				if (inst.getIns().equals(InstruccionP.CUP)){
					inst.setParam2(inst.getEtiqueta().num + "");
				}
				else{
					inst.setParam1(inst.getEtiqueta().num + "");
				}
				
			};
			ins = ins.next;
		}
	}
	
	//Escribe el programa indicado por la lista de instrucciones en el fichero especificado
	public void toFile(String path) throws IOException{
		
		this.resolverEtiquetas();
		
		Writer output = new OutputStreamWriter(new FileOutputStream(path));
		int cont = 0;
		InstruccionEnlazada ins = instrucciones.primeraInstruccion;
		while (ins!=null){
//			if (ins.etiqueta!=null){
//				output.write("{" + ins.etiqueta.nombre + "}\n");
//			}
			output.write("{" + cont + "} " + ins.instruccion.toString() + '\n');
			cont++;
			ins = ins.next;
		}
		output.close();
	}
}
