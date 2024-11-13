grammar Simulation;

// Point d'entrée de la grammaire
script: (statement ';')*;

// Définition d'une déclaration
statement
    : assignment
    | methodCall
    ;

// Assignation de variable (ex: `sat1 := new Satellite(hauteur = 2000);`)
assignment
    : IDENTIFIER ':=' 'new' className '(' params? ')'
    ;

// Appel de méthode (ex: `sat1.start();`)
methodCall
    : IDENTIFIER '.' IDENTIFIER '(' params? ')'
    ;

// Paramètres pour les constructeurs et méthodes
params
    : param (',' param)*
    ;

param
    : IDENTIFIER '=' expr
    ;

// Expressions simples (entiers ou identifiants)
expr
    : INT
    | IDENTIFIER
    | '#horizontal'
    | '#vertical'
    ;

// Classes possibles pour les objets créés
className
    : 'Satellite'
    | 'Balise'
    ;

// Lexèmes
IDENTIFIER: [a-zA-Z_][a-zA-Z_0-9]*;
INT: [0-9]+;

// Ignorer les espaces
WS: [ \t\r\n]+ -> skip;
