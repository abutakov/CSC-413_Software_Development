package lexer;

/** <pre>
 *  The Token class records the information for a token:
 *  1. The Symbol that describes the characters in the token
 *  2. The starting column in the source file of the token and
 *  3. The ending column in the source file of the token
 *  </pre>
*/
public class Token {
  private int leftPosition,rightPosition, linenum;
  private Symbol symbol;

/**
 *  Create a new Token based on the given Symbol
 *  @param leftPosition is the source file column where the Token begins
 *  @param rightPosition is the source file column where the Token ends
*/
  public Token(int leftPosition, int rightPosition, int linenum, Symbol sym) {
   this.leftPosition = leftPosition;
   this.rightPosition = rightPosition;
   this.linenum= linenum;
   this.symbol = sym;
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public void print() {
    
    System.out.printf("%-10s", symbol.toString());
    System.out.printf("%-5s", "left: "); 
    System.out.printf("%-5d", leftPosition);
    System.out.printf("%-5s", "right: ");
    System.out.printf("%-4d", rightPosition);
    System.out.printf("%-5s", "line: ");
    System.out.printf("%-5d", linenum);
    System.out.println("");
    
    return;
//    System.out.println(/*"       " +*/ symbol.toString() + 
//                       "             left: " + leftPosition +
//                       " right: " + rightPosition);
    
    
  }

  public String toString() {
    return symbol.toString();
  }

  public int getLeftPosition() {
    return leftPosition;
  }

  public int getRightPosition() {
    return rightPosition;
  }
  
  public int getLineNumber(){
      return linenum;
  }

/**
 *  @return the integer that represents the kind of symbol we have which
 *  is actually the type of token associated with the symbol
*/
  public Tokens getKind() {
    return symbol.getKind();
  }
}

