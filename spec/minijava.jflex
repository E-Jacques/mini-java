package syntax;
%% 
%include Jflex.include
%include JflexCup.include

/// Macros
WS         = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore     = {WS} | {EOLComment} | {C89Comment}
Integer    = 0 | [1-9] [0-9]*
Bool       = "true" | "false"
Ident      = [:jletter:] [:jletterdigit:]*

%%
//// Mots Clés
"class"   { return TOKEN(KLASS);   }
"main"    { return TOKEN(MAIN);    }
"out"     { return TOKEN(OUT);     }
"println" { return TOKEN(PRINTLN); }
"public"  { return TOKEN(PUBLIC);  }
"static"  { return TOKEN(STATIC);  }
"String"  { return TOKEN(STRING);  }
"System"  { return TOKEN(SYSTEM);  }
"void"    { return TOKEN(VOID);    }
"extends" { return TOKEN(EXTENDS); }
"return"  { return TOKEN(RETURN);  }
"int"     { return TOKEN(INT);     }
"boolean" { return TOKEN(BOOLEAN); }
"new"     { return TOKEN(NEW);     }
"this"    { return TOKEN(THIS_TOKEN);  }
"if"      { return TOKEN(IF); }
"if"      { return TOKEN(IF); }
"else"    { return TOKEN(ELSE); }
"while"   { return TOKEN(WHILE); }
"length"  { return TOKEN(LENGTH); }
//// Operateurs
"&&"      { return TOKEN(AND);     }
"<"       { return TOKEN(LESS);    }
"+"       { return TOKEN(PLUS);    }
"-"       { return TOKEN(MINUS);   }
"*"       { return TOKEN(TIMES);   }
"="       { return TOKEN(EQUAL);   }
"!"       { return TOKEN(NOT);     }
//// Ponctuations 
"."       { return TOKEN(DOT);     }
";"       { return TOKEN(SEMI);    }
","       { return TOKEN(COMMA);   }
"["       { return TOKEN(LB);      }
"]"       { return TOKEN(RB);      }
"{"       { return TOKEN(LC);      }
"}"       { return TOKEN(RC);      }
"("       { return TOKEN(LP);      }
")"       { return TOKEN(RP);      }
//// literals, identificateur
{Bool}    { return TOKEN(LIT_BOOL,        yytext());}
{Integer} { return TOKEN(LIT_INT,   Integer.parseInt(yytext()));      }  
{Ident}   { return TOKEN(IDENT,     new String(yytext())) ;           }
//// Ignore 
{Ignore}  {}
//// Ramasse Miette
[^]       { WARN("Invalid char '"+yytext()+"'"); return TOKEN(error); }
