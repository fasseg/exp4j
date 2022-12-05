grammar Exp4jGrammar;

expression:
    term
    ;

term:
    unary_prefix term |
    term multiplication term |
    term addition term |
    NUMBER |
    function |
    constant |
    variable
    ;

function:
    NAME '(' term (',' term)* ')'
    ;

unary_prefix:
    ('+' | '-')
    ;

addition:
    ('+' | '-')
    ;

multiplication:
    ('*' | '/' | '^' | '%')
    ;

constant:
    ('pi' | 'π' | 'phi' | 'φ' | 'e')
    ;

variable:
    NAME
    ;

NAME:
    [_a-zA-Z] [_a-zA-Z0-9]*
    ;

NUMBER:
    [0-9]+ (('.' [0-9]+)? (('e' | 'E') ('-')? [0-9]+ ('.' [0-9]+)?)?)?
    ;

WHITESPACE:
    (' '| '\t' | '\r' | '\n') + -> skip
    ;
