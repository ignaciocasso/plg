package compilador;

public enum TipoPrimitivo {
	INT(1),
	BOOL(1);
	
	private int size;
	public int size(){return size;}
	
	private TipoPrimitivo(int size){
		this.size = size;
	}
}
