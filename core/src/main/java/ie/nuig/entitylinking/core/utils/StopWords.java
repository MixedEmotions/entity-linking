package ie.nuig.entitylinking.core.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class StopWords {	
	private HashSet<String> stopWords = new HashSet<String>();
	private Properties config;


	
	public StopWords(String configFilePath){
		loadConfig(configFilePath);
		getStopWords();
	}
	
	
	public String removeStopWords(String text) {		
		StringTokenizer tokenizer = new StringTokenizer(text);
		StringBuffer textWithOutStopWords = new StringBuffer();
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if(!this.stopWords.contains(token)) 
				textWithOutStopWords.append(token + " ");			
		}
		return textWithOutStopWords.toString().trim();
	}
	
	public List<String> removeStopWords(List<String> text) {
		List<String> textWithOutStopWords = new ArrayList<String>();
		for(String token : text) {
			if((!this.stopWords.contains(token)) && (token.length()>2)) 
				textWithOutStopWords.add(token);			
		}
		return textWithOutStopWords;
	}

	public void loadConfig(String configFilePath) {
		if(this.config == null) {
			try {
				this.config =  new Properties();
				this.config.load(new FileInputStream(configFilePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean getStopWords() {
		String filePath = this.config.getProperty("stopWords") + "/" + "en" + ".txt";
		File stopWordsFile = new File(filePath);
		String stopWordsFileString = null;
		try {
			stopWordsFileString = BasicFileTools.extractText(stopWordsFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] splits = stopWordsFileString.split("\n");
		//	HashSet<String> stopWords = new HashSet<String>();
		for(String line : splits) {
			line = line.trim();
			if(!line.contentEquals("")) 
				if(!(line.charAt(0) == '|')) {
					if(line.contains("|")) {
						line = line.trim();
						char first = line.charAt(0);
						String myWord = line.substring(line.indexOf(first),line.indexOf(" "));
						stopWords.add(myWord);
					} else {
						stopWords.add(line);						
					}
				}
		}
		return true;
	}
	
	public static String cleanText(String text){
		return text.replaceAll("[\\d]", "").replaceAll("[^\\w\\s]", "");
		
	}
	
	public static void main(String[] args) {
		StopWords stopWords = new StopWords("/home/naggarw/WorkspaceEmpty/com.ibm.bluej.wikibasedMCR/resources/load/com.ibm.bluej.watson.wikiAnchorTitleIndex.properties");
		String words = stopWords.removeStopWords("198 Sensation of discomfort, distress, or agony in the abdominal region; generally associated with functional disorders, tissue injuries, or diseases");
	
		words = cleanText(words);
		System.out.println(words);
	}
	
}
