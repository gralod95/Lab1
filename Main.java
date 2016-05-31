/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mirea.spo.lab1;

import java.util.ArrayList;
import java.util.Scanner;
import static ru.mirea.spo.lab1.Lexer.lex;


/**
 *
 * @author macbook
 */
public class Main {
    
    static Scanner sc = new Scanner(System.in);
    
      public static void main(String[] args) {
          System.out.print("Введите ваши данные:"+"\n");
          String input = scanner();
    
    // Create tokens and print them
    ArrayList<Token> tokens = lex(input);
    for (Token token : tokens)
      System.out.println(token);
  }
      public static String scanner(){
        String scan = sc.nextLine();
        return scan;   
    }
    
}
