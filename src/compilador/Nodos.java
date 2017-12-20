package compilador;

import java.util.List;

import compilador.nodos.NodoPrograma;
import compilador.nodos.expresiones.NodoExpresion;
import compilador.nodos.expresiones.NodoFuncExp;
import compilador.nodos.expresiones.NodoLiteral;
import compilador.nodos.expresiones.NodoReferencia;
import compilador.nodos.expresiones.NodoVarExp;
import compilador.nodos.expresiones.operadores.NodoAnd;
import compilador.nodos.expresiones.operadores.NodoDiv;
import compilador.nodos.expresiones.operadores.NodoEQ;
import compilador.nodos.expresiones.operadores.NodoGE;
import compilador.nodos.expresiones.operadores.NodoGT;
import compilador.nodos.expresiones.operadores.NodoLE;
import compilador.nodos.expresiones.operadores.NodoLT;
import compilador.nodos.expresiones.operadores.NodoMas;
import compilador.nodos.expresiones.operadores.NodoMenos;
import compilador.nodos.expresiones.operadores.NodoNEQ;
import compilador.nodos.expresiones.operadores.NodoNeg;
import compilador.nodos.expresiones.operadores.NodoNot;
import compilador.nodos.expresiones.operadores.NodoOr;
import compilador.nodos.expresiones.operadores.NodoPor;
import compilador.nodos.sentencias.NodoAsignacion;
import compilador.nodos.sentencias.NodoBloque;
import compilador.nodos.sentencias.NodoCall;
import compilador.nodos.sentencias.NodoIf;
import compilador.nodos.sentencias.NodoIfElse;
import compilador.nodos.sentencias.NodoSentencia;
import compilador.nodos.sentencias.NodoWhile;
import compilador.nodos.sentencias.definiciones.NodoDecFunc;
import compilador.nodos.sentencias.definiciones.NodoDecProc;
import compilador.nodos.sentencias.definiciones.NodoDv;
import compilador.nodos.sentencias.definiciones.NodoDvCons;
import compilador.nodos.sentencias.definiciones.NodoDvNormal;
import compilador.nodos.sentencias.definiciones.NodoDvVI;
import compilador.nodos.tipos.NodoTipo;
import compilador.nodos.tipos.NodoTipoArray;
import compilador.nodos.tipos.NodoTipoPrimitivo;
import compilador.nodos.tipos.NodoTipoPuntero;
import compilador.nodos.variables.NodoAccesoArray;
import compilador.nodos.variables.NodoAccesoPuntero;
import compilador.nodos.variables.NodoVarUse;
import compilador.nodos.variables.NodoVariable;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class Nodos {
	//Factoria de nodos AST

	public static NodoPrograma nodoPrograma(Location loc, List<NodoSentencia> sentencias) {
		NodoPrograma nodo = new NodoPrograma(sentencias);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoBloque nodoBloque(Location loc, List<NodoSentencia> sentencias) {
		NodoBloque nodo = new NodoBloque(sentencias);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoDv nodoDv(Location loc, String id, NodoTipo tipo) {
		NodoDv nodo = new NodoDvNormal(id, tipo);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoDv nodoDv(Location loc, String id, NodoTipo tipo, NodoExpresion exp) {
		NodoDv nodo = new NodoDvVI(id, tipo, exp);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoDv nodoDvCons(Location loc, String id, NodoTipo tipo, NodoExpresion exp) {
		NodoDv nodo = new NodoDvCons(id, tipo, exp);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoSentencia nodoDecProc(Location loc, String id, List<NodoDv> args, List<NodoSentencia> cuerpo) {
		NodoSentencia nodo = new NodoDecProc(id, args, cuerpo);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoSentencia nodoDecFunc(Location loc, String id, List<NodoDv> args,	List<NodoSentencia> cuerpo, NodoTipo tipo, NodoExpresion ret) {
		NodoSentencia nodo = new NodoDecFunc(id, args, cuerpo, tipo, ret);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoSentencia nodoIf(Location loc, NodoExpresion cond, List<NodoSentencia> cuerpo) {
		NodoSentencia nodo = new NodoIf(cond, cuerpo);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoSentencia nodoIfElse(Location loc, NodoExpresion cond, List<NodoSentencia> cuerpo1,
			List<NodoSentencia> cuerpo2) {
		NodoSentencia nodo = new NodoIfElse(cond, cuerpo1, cuerpo2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoSentencia nodoWhile(Location loc, NodoExpresion cond, List<NodoSentencia> cuerpo) {
		NodoSentencia nodo = new NodoWhile(cond, cuerpo);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoSentencia nodoAsignacion(Location loc, NodoVariable var, NodoExpresion exp) {
		NodoSentencia nodo = new NodoAsignacion(var, exp);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoCall nodoCall(Location loc, String id, List<NodoExpresion> args) {
		NodoCall nodo = new NodoCall(id, args);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoTipo nodoTipoArray(Location loc, NodoTipo tipo, NodoExpresion exp) {
		NodoTipo nodo = new NodoTipoArray(tipo, exp);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoTipo nodoInt(Location loc) {
		NodoTipo nodo = new NodoTipoPrimitivo(TipoPrimitivo.INT);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoTipo nodoBool(Location loc) {
		NodoTipo nodo = new NodoTipoPrimitivo(TipoPrimitivo.BOOL);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoVariable nodoVarUse(Location loc, String id) {
		NodoVariable nodo = new NodoVarUse(id);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoVariable nodoAccesoArray(Location loc, NodoVariable var, NodoExpresion exp) {
		NodoVariable nodo = new NodoAccesoArray(var, exp);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoVarExp(Location loc, NodoVariable var) {
		NodoExpresion nodo = new NodoVarExp(var);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoEnt(Location loc, String n) {
		NodoExpresion nodo = new NodoLiteral(NodoTipoPrimitivo.INT, n);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoTrue(Location loc) {
		NodoExpresion nodo = new NodoLiteral(NodoTipoPrimitivo.BOOL, "true");
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoFalse(Location loc) {
		NodoExpresion nodo = new NodoLiteral(NodoTipoPrimitivo.BOOL, "false");
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoFuncExp(Location loc, NodoCall func) {
		NodoExpresion nodo = new NodoFuncExp(func);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoMas(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoMas(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoMenos(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoMenos(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoPor(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoPor(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoDiv(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoDiv(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoNeg(Location loc, NodoExpresion e) {
		NodoExpresion nodo = new NodoNeg(e);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoAnd(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoAnd(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoOr(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoOr(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoNot(Location loc, NodoExpresion e) {
		NodoExpresion nodo = new NodoNot(e);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoEQ(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoEQ(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoNEQ(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoNEQ(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoGT(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoGT(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoGE(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoGE(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoLT(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoLT(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion nodoLE(Location loc, NodoExpresion e1, NodoExpresion e2) {
		NodoExpresion nodo = new NodoLE(e1, e2);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoTipo nodoTipoPuntero(Location loc, NodoTipo tipo) {
		NodoTipo nodo = new NodoTipoPuntero(tipo);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoVariable NodoAccesoPuntero(Location loc, NodoVariable var) {
		NodoVariable nodo = new NodoAccesoPuntero(var);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoExpresion NodoReferencia(Location loc, NodoVariable var) {
		NodoExpresion nodo = new NodoReferencia(var);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoTipo nodoTipoArray(Location loc, NodoTipo tipo) {
		NodoTipo nodo = new NodoTipoArray(tipo);
		nodo.setLocation(loc);
		return nodo;
	}

	public static NodoDv nodoDecParam(Location loc, String id, NodoTipo tipo) {
		NodoDv nodo = new NodoDvNormal(id, tipo);
		nodo.setLocation(loc);
		return nodo;
	}

}
