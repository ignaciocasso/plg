package compilador.nodos;

import compilador.TablaDeSimbolos;
import compilador.errores.IdException;
import compilador.errores.TypeException;
import compilador.generacionCodigo.GeneradorDeCodigo;
import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class NodoAST{
	
	private Location location = null;
	public Location getLocation() {return location;}
	public void setLocation(Location loc) {this.location = loc;}
	
	public abstract void faseIdentificadores(TablaDeSimbolos tabla) throws IdException;
	
	public abstract void faseTipos() throws TypeException;
	
	public abstract void generarCodigo(GeneradorDeCodigo gen);
	
	public abstract int getMaxStackLength();
}