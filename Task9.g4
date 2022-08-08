/**
 * @name Fakhreldin Hussein Soliman Tarabay
 * @id 43-11215
 * @labNumber 19
 */
grammar Task9;
@members {public static int equals(int x, int y) {return x == y ? 1 : 0;}}

s returns [int check]: (r1'U'r2('U')*)+ {$check = equals($r1.counta,$r2.countb);};
r1 returns [int counta]: 'a'r1 {$counta=$r1.counta+1;} | 'b'r1 {$counta=$r1.counta;}|{$counta=$r1.counta;};
r2 returns [int countb]: 'b'r2 {$countb=$r2.countb+1;} | 'a'r2 {$countb=$r2.countb;}|{$countb=$r2.countb;};
WS:[ \t\n\r] -> skip;
