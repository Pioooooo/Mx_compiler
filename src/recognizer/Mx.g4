grammar Mx;

translationUnit: declarationSeq? EOF;

// Expressions

expression: assignmentExpression (COMMA assignmentExpression)*;

assignmentExpression:
	logicalOrExpression (ASSIGN logicalOrExpression)*;

logicalOrExpression:
	logicalAndExpression (OR_OR logicalAndExpression)*;

logicalAndExpression:
	inclusiveOrExpression (AND_AND inclusiveOrExpression)*;

inclusiveOrExpression:
	exclusiveOrExpression (OR exclusiveOrExpression)*;

exclusiveOrExpression: andExpression (CARET andExpression)*;

andExpression: equalityExpression (AND equalityExpression)*;

equalityExpression:
	relationalExpression (
		op+=(EQUAL | NOT_EQUAL) relationalExpression
	)*;

relationalExpression:
	shiftExpression (
		op+=(LESS | LESS_EQUAL | GREATER | GREATER_EQUAL) shiftExpression
	)*;

shiftExpression:
	additiveExpression (
		op+=(LESS_LESS | GREATER_GREATER) additiveExpression
	)*;

additiveExpression:
	multiplicativeExpression (
		op+=(PLUS | MINUS) multiplicativeExpression
	)*;

multiplicativeExpression:
	unaryExpression (op+=(STAR | DIV | MOD) unaryExpression)*;

unaryExpression:
	postfixExpression
	| op=(PLUS_PLUS | MINUS_MINUS | PLUS | MINUS | NOT | TILDE) unaryExpression
	| newExpression;

newExpression:
	NEW typeSpecifier (
		(LEFT_BRACKET expression RIGHT_BRACKET)+ (
			LEFT_BRACKET RIGHT_BRACKET
		)*
		| (LEFT_PAREN RIGHT_PAREN)
	)?;

postfixExpression:
	primaryExpression
	| postfixExpression op=(PLUS_PLUS | MINUS_MINUS)
	| postfixExpression LEFT_PAREN expressionList? RIGHT_PAREN
	| postfixExpression LEFT_BRACKET expression RIGHT_BRACKET
	| postfixExpression DOT idExpression;

expressionList:
	assignmentExpression (COMMA assignmentExpression)*;

primaryExpression:
	literal
	| THIS
	| LEFT_PAREN expression RIGHT_PAREN
	| idExpression;

idExpression: IDENTIFIER;

literal: boolean_literal | INTEGER_LITERAL | STRING_LITERAL | NULL;

boolean_literal: TRUE | FALSE;

// Statements

statement:
	expressionStatement
	| compoundStatement
	| selectionStatement
	| iterationStatement
	| jumpStatement
	| declarationStatement;

expressionStatement: expression? SEMI;

compoundStatement: LEFT_BRACE statementSeq? RIGHT_BRACE;

statementSeq: statement+;

selectionStatement:
	IF LEFT_PAREN expression RIGHT_PAREN ifBody=statement (
		ELSE elseBody=statement
	)?;

iterationStatement:
	WHILE LEFT_PAREN whileCondition=expression RIGHT_PAREN statement
	| FOR LEFT_PAREN forInitStatement forCondition=expression? SEMI forIteration=expression? RIGHT_PAREN statement;

forInitStatement: expressionStatement | simpleDeclaration;

jumpStatement: (BREAK | CONTINUE | RETURN expression?) SEMI;

declarationStatement: simpleDeclaration;

// Declarations

declarationSeq: declaration+;

declaration: simpleDeclaration | functionDefinition;

simpleDeclaration: (typeSpecifier initDeclaratorList?)? SEMI;

initDeclaratorList: initDeclarator (COMMA initDeclarator)*;

initDeclarator: declarator initializer?;

declarator: (LEFT_BRACKET RIGHT_BRACKET)* idExpression parametersAndQualifiers?;

parametersAndQualifiers:
	LEFT_PAREN parameterDeclarationList? RIGHT_PAREN;

parameterDeclarationList:
	parameterDeclaration (COMMA parameterDeclaration)*;

parameterDeclaration: typeSpecifier declarator;

initializer: ASSIGN assignmentExpression;

typeSpecifier: simpleTypeSpecifier | classSpecifier;

simpleTypeSpecifier: IDENTIFIER | INT | BOOL | STRING | VOID;

classSpecifier:
	CLASS className=IDENTIFIER LEFT_BRACE declarationSeq? RIGHT_BRACE;

functionDefinition:
	simpleTypeSpecifier? declarator compoundStatement;

// reserved names
BOOL: 'bool';
INT: 'int';
STRING: 'string';
NULL: 'null';
VOID: 'void';
TRUE: 'true';
FALSE: 'false';
IF: 'if';
ELSE: 'else';
FOR: 'for';
WHILE: 'while';
BREAK: 'break';
CONTINUE: 'continue';
RETURN: 'return';
NEW: 'new';
CLASS: 'class';
THIS: 'this';

// symbols
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
PLUS: '+';
MINUS: '-';
STAR: '*';
DIV: '/';
MOD: '%';
CARET: '^';
AND: '&';
OR: '|';
TILDE: '~';
NOT: '!';
LESS_LESS: '<<';
GREATER_GREATER: '>>';
ASSIGN: '=';
LESS: '<';
GREATER: '>';
EQUAL: '==';
NOT_EQUAL: '!=';
LESS_EQUAL: '<=';
GREATER_EQUAL: '>=';
AND_AND: '&&';
OR_OR: '||';
PLUS_PLUS: '++';
MINUS_MINUS: '--';
COMMA: ',';
DOT: '.';
SEMI: ';';

// TOKENS
IDENTIFIER: LETTER (NONDIGIT | DIGIT)*;
INTEGER_LITERAL: DECIMAL_LITERAL;
STRING_LITERAL: '"' (~["\\\n\r] | '\\' ["\\nr])* '"';

// encoding
fragment DIGIT: [0-9];
fragment NONZERO_DIGIT: [1-9];
DECIMAL_LITERAL: NONZERO_DIGIT DIGIT* | DIGIT;

fragment LETTER: [a-zA-Z];
fragment NONDIGIT: [a-zA-Z_];

WHITESPACE: [ \t]+ -> skip;
NEWLINE: ('\r' '\n'? | '\n') -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;