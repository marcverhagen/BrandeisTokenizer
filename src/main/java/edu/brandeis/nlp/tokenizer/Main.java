/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brandeis.nlp.tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marc
 */
public class Main {

	public static void main(String[] args) {

		Tokenizer tokenizer = new Tokenizer();
        TokenizedText result;

        try {
            //tokenizer.test();

            result = tokenizer.tokenizeFile(new File("/Users/marc/Desktop/example.txt"));
            result.printSentences();
            result.report();

            result = tokenizer.tokenizeText("John Jr. is asleep.");
            result.printSentences();
            result.report();

        } catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
