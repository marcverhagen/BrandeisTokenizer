package edu.brandeis.nlp.tokenizer;

import java.io.File;
import java.io.IOException;


public class Main {

	private static final Boolean DEBUG = true;

	public static void main(String[] args) throws IOException
	{
		if (DEBUG)
			test();
		else
			run(args);
	}

	private static void test() throws IOException
	{
		boolean test = false;
		boolean example1 = true;
		boolean example2 = false;
		boolean text1 = false;

		Tokenizer tokenizer = new Tokenizer();
        TokenizedText result;
		String fname;

		fname = "/Users/marc/Desktop/stanford-tokens-phrases.lif";
		//fname = "/Users/marc/Desktop/example.txt";

		if (example1) {
            result = tokenizer.tokenizeFile(new File(fname));
			result.printSentences();
			result.report(); }

		if (example2) {
            result = tokenizer.tokenizeFile(
					new File("/Users/marc/Desktop/example.txt"),
					new File("/Users/marc/Desktop/example.tok"));
			result.printSentences();
			result.report(); }

		if (text1) {
			result = tokenizer.tokenizeText("John Jr. is asleep.");
	        result.printSentences();
		    result.report(); }
	}

	private static void run(String[] args) throws IOException
	{
		Tokenizer tokenizer = new Tokenizer();
        TokenizedText result;

		String fname = args[0];
		System.out.println(fname);
        result = tokenizer.tokenizeFile(new File(fname));
		result.printSentences();
		result.report();
	}

}
