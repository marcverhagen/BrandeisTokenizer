package edu.brandeis.nlp.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Tokenizer {

	private static final Boolean DEBUG = false;

	public String filename;
	public String text;
	public int length;
	public ArrayList<Token> tokens;
	public ArrayList<Sentence> sentences;
	public Token firstToken;

	private final String defaultInput = "/example.txt";
	private final String defaultOutput = "/example.tok";

	public Tokenizer(String filename) throws IOException
	{
		if (filename == null)
			filename = defaultInput;
		this.filename = filename;
		readFileContent();
		this.tokens = new ArrayList<>();
		this.sentences = new ArrayList<>();
		readTokens();
		linkTokens();
		splitPunctuations();
		splitSentences();
		printSentences();
	}

	private void readTokens() {
		int offset = 0;
		while (offset < this.length) {
			Token token = slurpToken(offset);
			// this prevents adding a token with just leading whitespace, which
			// happens when the text ends with some white space
			if (token.length > 0)
				this.tokens.add(token);
			offset = token.endToken; }
	}

	/**
	 * Consume a token starting at the given offset.
	 *
	 * @param offset offset of where to start in this.text
	 * @return a Token object that has the beginning and ending offsets of the
	 * token as well as the text of the token and an indication of where leading
	 * whitespace started
	 */
	private Token slurpToken(int offset) {
		int beginSpace = offset;
		offset = consumeSpace(offset);
		int beginToken = offset;
		StringBuilder token = new StringBuilder();
		while (offset < this.length) {
			char c = this.text.charAt(offset);
			if (Character.isWhitespace(c))
				break;
			token.append(c);
			offset++;
		}
		return new Token(token.toString(), beginSpace, beginToken, offset);
	}

	/**
	 * Consume consecutive white space characters in the text starting from the
	 * given offset.
	 *
	 * @param offset
	 * @return the offset immediately after the final whitespace found
	 */
	private int consumeSpace(int offset) {
		while (offset < this.length) {
			char c = this.text.charAt(offset);
			if (Character.isWhitespace(c))
				offset++;
			else
				return offset; }
		return offset;
	}

	private void readFileContent() throws IOException
	{
		//System.out.println("Reading content from " + filename);
		InputStream stream = this.getClass().getResourceAsStream(this.filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder string = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null)
			string.append(line).append("\n");
		this.text = string.toString();
		this.length = this.text.length();
	}

	private void linkTokens() {
		this.firstToken = this.tokens.get(0);
		for (int i=0 ; i < this.tokens.size() - 1 ; i++) {
			Token t1 = this.tokens.get(i);
			Token t2 = this.tokens.get(i+1);
			t1.next = t2;
			t2.previous = t1; }
	}

	private void splitPunctuations() {

		ArrayList<Token> newTokens = new ArrayList<>();
		ArrayList<Token> splitToken;
		for (Token tok : this.tokens) {
			splitToken = tok.splitPunctuations();
			if (splitToken == null)
				newTokens.add(tok);
			else
				newTokens.addAll(splitToken); }
		this.tokens = newTokens;
		linkTokens();
		for (Token tok : newTokens)
			debug(tok.toString());
	}

	private void debug(String text) {
		if (DEBUG)
			System.out.println(text);
	}

	private void splitSentences() {
		Sentence sentence = new Sentence();
		this.sentences.add(sentence);
		for (Token tok : this.tokens) {
			sentence.add(tok);
			if (tok.isEOS()) {
				sentence = new Sentence();
				this.sentences.add(sentence); }}
	}

	public final void printSentences() {
		for (Sentence sent : this.sentences) {
		for (Token tok : sent.tokens)
			System.out.println(tok);
		System.out.println(); }
	}

}
