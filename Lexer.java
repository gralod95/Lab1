/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mirea.spo.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

public class Lexer {

	private List<Token> tokens = new ArrayList<Token>();

	String accum="";

	//patterns are here
	private Pattern pSM = Pattern.compile("^;$");
	private Pattern pASSIGN_OP = Pattern.compile("^=$");
	private Pattern pDIGIT = Pattern.compile("^0|[1-9]{1}[0-9]*$");
	private Pattern pVAR = Pattern.compile("^[a-zA-Z]+$"); 
	private Pattern pWS = Pattern.compile("^\\s*$");

	private Pattern pPLUS_OP = Pattern.compile("^[+]$");
	private Pattern pMINUS_OP = Pattern.compile("^[-]$");
	private Pattern pDEL_OP = Pattern.compile("^[/]$");
	private Pattern pMULT_OP = Pattern.compile("^[*]$");
	
	private Pattern pBRK_OP = Pattern.compile("^[(]$");
	private Pattern pBRK_CL = Pattern.compile("^[)]$");

	private Pattern pVAR_KW  = Pattern.compile("^var$");
	
	//maps are here
	private Map<String, Pattern> commonTerminals = new HashMap<String, Pattern> ();
	private Map<String, Pattern> keyWords = new HashMap<String, Pattern> ();

	private String currentLucky = null;
	private int i;

	public Lexer() {

		//add pattern to map for keywords recognition
		keyWords.put("VAR_KW", pVAR_KW); 

		//add pattern to map for regular terminals recognition
		commonTerminals.put("SM", pSM);
		commonTerminals.put("ASSIGN_OP", pASSIGN_OP);
		commonTerminals.put("DIGIT", pDIGIT);
		commonTerminals.put("VAR", pVAR);
		commonTerminals.put("WS", pWS);
		commonTerminals.put("PLUS_OP", pPLUS_OP);
		commonTerminals.put("MINUS_OP", pMINUS_OP);
		commonTerminals.put("DEL_OP", pDEL_OP);
		commonTerminals.put("MULT_OP", pMULT_OP);
		commonTerminals.put("BRK_OP", pBRK_OP);
		commonTerminals.put("BRK_CL", pBRK_CL);
	}
        public void processInput(String fileName) throws IOException {
		File file = new File(fileName);
		Reader reader = new FileReader(file);
		BufferedReader breader = new BufferedReader(reader);
		String line;
		while( (line = breader.readLine()) != null ) {
			processLine(line);
		}
		System.out.println("TOKEN("
			+ currentLucky
			+ ") recognized with value : "
			+ accum
			);

		tokens.add(new Token(currentLucky, accum));

		System.out.println("List of tokens:");
		for (Token token: tokens) {
			System.out.println(token);
		}

	}

	private void processLine(String line) {
		for ( i=0; i<line.length(); i++ ) {
			accum = accum + line.charAt(i);
			processAcumm();
		}
	}

	private void processAcumm() {
		boolean found = false;
		for ( String regExpName : commonTerminals.keySet() ) {
			Pattern currentPattern = commonTerminals.get(regExpName);
			Matcher m = currentPattern.matcher(accum);
			if ( m.matches() ) {
				currentLucky = regExpName;
				found = true;
			}
		}
                

		if ( currentLucky != null && !found ) {
			System.out.println("TOKEN("
			+ currentLucky
			+ ") recognized with value : "
			+ accum.substring(0, accum.length()-1)
			);

			tokens.add(new Token(currentLucky, accum.substring(0, accum.length()-1)));
			i--;
			accum = "";
			currentLucky = null;
		}


		for ( String regExpName : keyWords.keySet() ) {
			Pattern currentPattern = keyWords.get(regExpName);
			Matcher m = currentPattern.matcher(accum);
			if ( m.matches() ) {
				currentLucky = regExpName;
				found = true;
			}
		}
                
		if ( currentLucky != null && !found ) {
			System.out.println("TOKEN("
			+ currentLucky
			+ ") recognized with value : "
			+ accum.substring(0, accum.length()-1)
			);

			tokens.add(new Token(currentLucky, accum.substring(0, accum.length()-1)));
			i--;
			accum = "";
			currentLucky = null;
		}
                
                if ( accum!="" && currentLucky == null && found == false ) {
			System.out.println("WRONG TOKEN");
                        accum = "";
		}
	}

	public List<Token> getTokens() {
		return tokens;
	}
}
