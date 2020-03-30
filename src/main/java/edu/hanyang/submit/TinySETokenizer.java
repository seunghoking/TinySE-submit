package edu.hanyang.submit;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;

import edu.hanyang.indexer.Tokenizer;

public class TinySETokenizer implements Tokenizer {

	private SimpleAnalyzer analyzer;
	private PorterStemmer stemmer;
	
	public void setup() {
		analyzer = new SimpleAnalyzer();
		stemmer = new PorterStemmer();
	}

	public List<String> split(String text) {
		List<String> list = new ArrayList<String>();
		
		try {
			TokenStream stream = analyzer.tokenStream(null, new StringReader(text));
			stream.reset();
			CharTermAttribute term = stream.getAttribute(CharTermAttribute.class);
			
			while(stream.incrementToken()) {
				stemmer.setCurrent(term.toString());
				stemmer.stem();
				list.add(stemmer.getCurrent());
				System.out.println(stemmer.getCurrent());
			}
			stream.close();
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
		return list;
	}
	

	public void clean() {
		analyzer.close();
	}

}

// 다하고 mvn test 로 unit test 진행
