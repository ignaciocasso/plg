package compilador.alex;

import compilador.errores.GestionErrores;

%%
%cup
%line
%char
%class AnalizadorLexico
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErrores errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yychar+1;}
  public void fijaGestionErrores(GestionErrores errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)

cero = 0
numeroEntero = [\-]?{digitoPositivo}{digito}* //problema: i-1 lo considera dos numeros seguidos

separador = [ \t\r\b\n]
comentario = //[^\n]*

if = if
then = then
else = else
while = while
do = do
void = void
return = return
const = const

int = int
bool = bool
true = true
false = false

identificador = {letra}({letra}|{digito})*


operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = /
operadorAnd = &&
operadorOr = \|\|
operadorNot = \!
operadorIgual = ==
operadorNoIgual = !=
operadorMenor = <
operadorMayor = >
operadorMenorIgual = <=
operadorMayorIgual = >=


parentesisApertura = \(
parentesisCierre = \)
bloqueApertura = \{
bloqueCierre = \}
corcheteApertura = \[
corcheteCierre = \]


igual = \=
coma  = \,
puntoYcoma  = \;

puntero = \#
referencia = \&


%%


{separador}               {}
{comentario}              {}
{cero}                  {return ops.unidadEnt();}
{numeroEntero}            {return ops.unidadEnt();}

{if}                    {return ops.unidadIf();}
{then}                  {return ops.unidadThen();}
{else}                  {return ops.unidadElse();}
{while}                 {return ops.unidadWhile();}
{do}                    {return ops.unidadDo();}

{void}					{return ops.unidadVoid();}
{return}				{return ops.unidadReturn();}

{const}					{return ops.unidadConst();}

{int}                   {return ops.unidadInt();}
{bool}                  {return ops.unidadBool();}
{true}                  {return ops.unidadTrue();}
{false}                 {return ops.unidadFalse();}

{identificador}           {return ops.unidadId();}


{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{operadorAnd}           {return ops.unidadAnd();}
{operadorAnd}           {return ops.unidadAnd();}
{operadorOr}            {return ops.unidadOr();}
{operadorNot}           {return ops.unidadNot();}
{operadorIgual}         {return ops.unidadEQ();}
{operadorNoIgual}	{return ops.unidadNEQ();}
{operadorMenor}         {return ops.unidadLT();}
{operadorMayor}         {return ops.unidadGT();}
{operadorMenorIgual}    {return ops.unidadLE();}
{operadorMayorIgual}    {return ops.unidadGE();}


{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{bloqueApertura}        {return ops.unidadBloqueAp();}
{bloqueCierre}          {return ops.unidadBloqueCierre();}
{corcheteApertura}      {return ops.unidadCorchAp();}
{corcheteCierre}        {return ops.unidadCorchCierre();}

{igual}                   {return ops.unidadIgual();}
{coma}                    {return ops.unidadComa();}
{puntoYcoma}            {return ops.unidadPuntoComa();}

{puntero}                   {return ops.unidadPuntero();}
{referencia}                   {return ops.unidadReferencia();}

[^]                       {errores.errorLexico(fila(),columna(),lexema());}