package compilador.alex;

import compilador.asint.ClaseLexica;
import java_cup.runtime.Symbol;

public class ALexOperations {
	private AnalizadorLexico alex;

	public ALexOperations(AnalizadorLexico alex) {
		this.alex = alex;
	}

	public UnidadLexica unidadId() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema());
	}

	public UnidadLexica unidadEnt() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENT, alex.lexema());
	}
	
	public UnidadLexica unidadIf() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF, "if");
	}

	public UnidadLexica unidadThen() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.THEN, "then");
	}

	public UnidadLexica unidadElse() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE, "else");
	}

	public UnidadLexica unidadWhile() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE, "while");
	}

	public UnidadLexica unidadDo() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DO, "do");
	}
	
	public UnidadLexica unidadVoid() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.VOID, "void");
	}
	
	public UnidadLexica unidadReturn() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RETURN, "return");
	}
	
	public UnidadLexica unidadConst() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CONST, "const");
	}

	public UnidadLexica unidadInt() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.INT, "int");
	}

	public UnidadLexica unidadBool() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BOOL, "bool");
	}

	public UnidadLexica unidadTrue() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TRUE, "true");
	}

	public UnidadLexica unidadFalse() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FALSE, "false");
	}	
	
	public UnidadLexica unidadSuma() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAS, "+");
	}

	public UnidadLexica unidadResta() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOS, "-");
	}

	public UnidadLexica unidadMul() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.POR, "*");
	}

	public UnidadLexica unidadDiv() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV, "/");
	}

	public UnidadLexica unidadAnd() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND, "&&");
	}

	public UnidadLexica unidadOr() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR, "||");
	}

	public UnidadLexica unidadNot() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOT, "!");
	}

	public UnidadLexica unidadEQ() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EQU, "==");
	}

	public UnidadLexica unidadNEQ() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEQ, "!=");
	}

	public UnidadLexica unidadGT() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.GT, ">");
	}

	public UnidadLexica unidadGE() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.GE, ">=");
	}

	public UnidadLexica unidadLT() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LT, "<");
	}

	public UnidadLexica unidadLE() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LE, "<=");
	}	
	
	
	public UnidadLexica unidadPAp() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AP_PAR, "(");
	}

	public UnidadLexica unidadPCierre() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CIERRE_PAR, ")");
	}

	public UnidadLexica unidadBloqueAp() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AP_BLOQUE, "{");
	}

	public UnidadLexica unidadBloqueCierre() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CIERRE_BLOQUE, "}");
	}
	
	public UnidadLexica unidadCorchAp() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AP_CORCH, "[");
	}

	public UnidadLexica unidadCorchCierre() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CIERRE_CORCH, "]");
	}

	public UnidadLexica unidadIgual() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL, "=");
	}

	public UnidadLexica unidadComa() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA, ",");
	}

	public UnidadLexica unidadPuntoComa() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTOCOMA, ";");
	}

	public UnidadLexica unidadEof() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF, "<EOF>");
	}

	public Symbol unidadReferencia() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REF, alex.lexema());
	}

	public Symbol unidadPuntero() {
		return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNT, alex.lexema());
	}
}
