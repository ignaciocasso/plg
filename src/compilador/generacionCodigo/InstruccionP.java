package compilador.generacionCodigo;

import compilador.generacionCodigo.GeneradorDeCodigo.Etiqueta;

//Enumerado de las instrucciones-p usadas y factoría de instrucciones
public enum InstruccionP{
	
	ADD,
	SUB,
	MUL,
	DIV,
	NEG,
	AND,
	OR,
	NOT,
	EQU,
	NEQ,
	GRT,
	GEQ,
	LES,
	LEQ,
	UJP,
	FJP,
	IND,
	LDC,
	LDO,
	SRO,
	STO,
	IXA,
	DEC,
	CHK,
	INC,
	LOD,
	LDA,
	STR,
	MOVS,
	MST,
	CUP,
	SSP,
	SEP,
	RETP,
	RETF,
	STP;

	public static Instruccion NEQ() {
		return new Instruccion(NEQ);
	}

	public static Instruccion LES() {
		return new Instruccion(LES);
	}

	public static Instruccion LEQ() {
		return new Instruccion(LEQ);
	}

	public static Instruccion GRT() {
		return new Instruccion(GRT);
	}

	public static Instruccion GEQ() {
		return new Instruccion(GEQ);
	}

	public static Instruccion EQU() {
		return new Instruccion(EQU);
	}

	public static Instruccion OR() {
		return new Instruccion(OR);
	}

	public static Instruccion AND() {
		return new Instruccion(AND);
	}

	public static Instruccion MUL() {
		return new Instruccion(MUL);
	}

	public static Instruccion SUB() {
		return new Instruccion(SUB);
	}

	public static Instruccion ADD() {
		return new Instruccion(ADD);
	}

	public static Instruccion DIV() {
		return new Instruccion(DIV);
	}

	public static Instruccion NOT() {
		return new Instruccion(NOT);
	}

	public static Instruccion NEG() {
		return new Instruccion(NEG);
	}

	public static Instruccion IND() {
		return new Instruccion(IND);
	}

	public static Instruccion IXA(int size) {
		return new Instruccion(IXA, size+"");
	}

	public static Instruccion LDA(int pa_rel, int direccion) {
		return new Instruccion(LDA, pa_rel+"", direccion+"");
	}

	public static Instruccion UJP(Etiqueta et) {
		return new Instruccion(UJP, et);
	}

	public static Instruccion SSP(int size) {
		return new Instruccion(SSP, size+"");
	}

	public static Instruccion SEP(int n) {
		return new Instruccion(SEP, n+"");
	}

	public static Instruccion RETP() {
		return new Instruccion(RETP);
	}

	public static Instruccion STO() {
		return new Instruccion(STO);
	}

	public static Instruccion MOVS(int sizeParam) {
		return new Instruccion(MOVS, sizeParam+"");
	}

	public static Instruccion MST(int i) {
		return new Instruccion(MST, i+"");
	}

	public static Instruccion FJP(Etiqueta fin) {
		return new Instruccion(FJP, fin);
	}

	public static Instruccion LDC(String value) {
		return new Instruccion(LDC, value);
	}

	public static Instruccion CUP(int sizeParams, Etiqueta etiqueta) {
		return new Instruccion(CUP, sizeParams+"", etiqueta);
	}

	public static Instruccion RETF() {
		return new Instruccion(RETF);
	}

	public static Instruccion STR(int i, int j) {
		return new Instruccion(STR, i+"", j+"");
	}
}
