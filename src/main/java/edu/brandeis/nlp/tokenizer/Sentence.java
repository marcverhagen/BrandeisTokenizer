package edu.brandeis.nlp.tokenizer;

import java.util.ArrayList;

class Sentence {

	private static Boolean DEBUG = false;

	public String text;
	public int length, beginSpace, beginToken, endToken;
	public ArrayList<Token> tokens;

	public Sentence() {

		this.tokens = new ArrayList<>();
	}

	public void add(Token tok) {
		this.tokens.add(tok);
	}

}
