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
    
    private boolean operation() throws Exception{
        System.out.print("Вход в operation"+"\n");
        if(op()){
            return true;
        }
        return false;
   }
    
    private boolean op() throws Exception{
        System.out.print("Вход в op"+"\n");
        numOfRTok++;
        ws();
        match();
        if(currentToken.getName().equals("PLUS_OP")||
            currentToken.getName().equals("MINUS_OP")||
            currentToken.getName().equals("DEL_OP")||
            currentToken.getName().equals("MULT_OP")||
            currentToken.getName().equals("INVOL_OP")){
            if(var()||digit()){
                return true;
            } 
            currentTokenNumber--;
            return false;
        }
        currentTokenNumber--;
        return false;
    }
    
    private boolean digit() throws Exception{
        System.out.print("Вход в digit"+"\n");
        ws();
        match();
        if(!currentToken.getName().equals("DIGIT")){
            currentTokenNumber--;
        }else{
            numOfRTok++;
        }
        return currentToken.getName().equals("DIGIT");
    }
    
    private boolean assign_op() throws Exception{
        System.out.print("Вход в assign_op"+"\n");
        numOfRTok++;
        ws();
        match();
        return currentToken.getName().equals("ASSIGN_OP");
    }
    
    private boolean sm() throws Exception{
        System.out.print("Вход в sm"+"\n");
        numOfRTok++;
        ws();
        match();
        return currentToken.getName().equals("SM");
    }
    
    private boolean var() throws Exception{
        System.out.print("Вход в var"+"\n");
        ws();
        match();
        if(!currentToken.getName().equals("VAR")){
            currentTokenNumber--;
        }else{
            nameOfVar = currentToken.getValue();
            numOfRTok++;
        }
        return currentToken.getName().equals("VAR");
    }
    
    private boolean var_kw() throws Exception{
        System.out.print("Вход в var_kw"+"\n");
        numOfRTok++;
        ws();
        match();
        return currentToken.getName().equals("VAR_KW");
    }
    
    private void ws(){
        System.out.print("Вход в ws"+"\n");
        numOfRTok++;
        match();
        if(!currentToken.getName().equals("WS")){
            numOfRTok--;
            currentTokenNumber--;
        }
        
    }
    
    private void addNewVal(){
        System.out.print(nameOfVar+"\n");
        variables.add(new Token("VAR",nameOfVar ));
        varVal.add(0);
    }
}
