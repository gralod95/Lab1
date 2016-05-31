/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mirea.spo.lab1;

/**
 *
 * @author macbook
 */
 public class Token {
    private int nL; 
    private int id;
    public Lexer.TokenType type;
    public String data;

    public Token(Lexer.TokenType type, String data, int id, int nL) {
        //System.out.print(id);
        this.id = id;
        this.nL = nL;
        this.type = type;
        this.data = data;
    }
    @Override
    public String toString() {
      return String.format("(%s %s)", type.name(), data);
    }
 }