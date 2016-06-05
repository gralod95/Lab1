package ru.mirea.spo.lab1;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author macbook
 */
public class Parser {
    private List<Token> tokens;
    private Token currentToken;
    private int currentTokenNumber = 0;
    private int numOfRTok = 0;
    private List<Token> variables = new ArrayList<Token>();
    private List<Integer> varVal = new ArrayList<Integer>();
    private String nameOfVar;
    
    public void setTokens(List<Token> tokens){
		this.tokens = tokens;
	}
    
    public boolean match(){
        currentToken = tokens.get(currentTokenNumber);
        currentTokenNumber++;
        return false;
    }
    
    public void lang() throws Exception {
        boolean exist = false;
        while ( currentTokenNumber < tokens.size() && expr() ) {
            exist = true;
        }
        if (!exist) {
            System.out.print("end");
            throw new Exception("expr expected");
        }
    }
    
    private boolean expr() throws Exception{
        System.out.print("Вход в expr"+"\n");
        if(declare()||assign()){
            return true;  
        }
        return false;
    }
    
    private boolean declare() throws Exception{
        System.out.print("Вход в declare"+"\n");
        if (var_kw()){
            if(var()){
                if(sm()){
                    System.out.print("строка правильная"+"\n");
                    addNewVal();
                    numOfRTok = 0;
                    return true;
                }else { //!sm()
                    throw new Exception("VAR_KW, VAR found; SM expected; currentToken: " + currentToken);
                }
            }else {
                throw new Exception("VAR_KW found; VAR expected; currentToken: " + currentToken);
            }
        }
        System.out.print(numOfRTok);
        currentTokenNumber = currentTokenNumber - numOfRTok; 
        return false;
    }
    
    private boolean assign() throws Exception{
        System.out.print("Вход в assign"+"\n");
        if (var()){
            if(var_exist()){
                if(assign_op()){
                    if(var()||digit()){
                        while(operation()){}
                            if(sm()){
                                System.out.print("строка правильная"+"\n");
                                numOfRTok = 0;
                                return true;
                            }else { //!var||digit
                                throw new Exception("VAR,ASSIGN_OP, all OPERATIONS found; SM expected; currentToken: " + currentToken);
                            }   
                    }else { //!var||digit
                        throw new Exception("VAR,ASSIGN_OP found; VAR or DIGIT expected; currentToken: " + currentToken);
                    }
                }else { //!assignOp
                    throw new Exception("VAR found; ASSIGN_OP expected; currentToken: " + currentToken);
                }
            }
        }
        System.out.print(numOfRTok);
        currentTokenNumber = currentTokenNumber - numOfRTok; 
        return false;
    }
    
    private boolean var_exist() throws Exception{
        System.out.print("Вход в var_exist"+"\n");
        for(int i = 0; i <= variables.size()-1;){
           if(variables.get(i).getName().equals(currentToken.getName())){
               return true;
           }
           i++;
        }
        //return variables.contains(currentToken.getName());
        return false;
    }
