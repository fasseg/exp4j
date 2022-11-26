grammar Exp4jGrammar;

expression:
    term
    ;

term:
    term unary_suffix |
    unary_prefix term |
    term multiplication term |
    term addition term |
    decimal |
    function |
    constant
    ;

function:
    FUNCTION_NAME '(' term (',' term)* ')'
    ;

decimal:
    NUMBER ('.' NUMBER)?
    ;

unary_prefix:
    ('+' | '-')
    ;

unary_suffix:
    ('!')
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

FUNCTION_NAME:
    [_a-zA-Z] [_a-zA-Z0-9]*
    ;

NUMBER:
    [0-9]+
    ;

WHITESPACE:
    (' '| '\t' | '\r' | '\n') + -> skip
    ;
