/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mirea.spo.lab1;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
  public static enum TokenType {
    // Token types cannot have underscores
    NUMBER("-?[0-9]+"), 
    BINARYOP("[*|/|+|-]"),
    BR_OP("("),
    BR_CL(")"),
    EQV("="),
    WHITESPACE("[ \t\f\r]+"),
    ENDOFLINE("[ \n]+");

    public final String pattern;

    private TokenType(String pattern) {
      this.pattern = pattern;
    }
  }

 
  public static ArrayList<Token> lex(String input) {
      int nL = 0;
      int id = 0;
    // The tokens to return
    ArrayList<Token> tokens = new ArrayList<Token>();

    // Lexer logic begins here
    StringBuffer tokenPatternsBuffer = new StringBuffer();
    for (TokenType tokenType : TokenType.values())
      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
    Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

    Matcher matcher = tokenPatterns.matcher(input);
    while (matcher.find()) {
      if (matcher.group(TokenType.NUMBER.name()) != null) {
        tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name()), id, nL));
        id ++;
        continue;
      } else if (matcher.group(TokenType.BINARYOP.name()) != null) {
        tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name()),id, nL));
        id++;
        continue;
      } else if (matcher.group(TokenType.ENDOFLINE.name()) != null){
          nL++;
          System.out.print(nL);
      }else if (matcher.group(TokenType.WHITESPACE.name()) != null)
        continue;
    }

    return tokens;
  }
}
