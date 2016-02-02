package ie.nuig.entitylinking.ner.stanford;


import ie.nuig.entitylinking.core.AnnotatedMention;
import ie.nuig.entitylinking.core.ELDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;


public class StanfordNER {

	private AbstractSequenceClassifier<CoreLabel> classifier = null;
	private final Pattern patternPerson = Pattern.compile("<PERSON>(.+?)</PERSON>");
	private final Pattern patternOrganization = Pattern.compile("<ORGANIZATION>(.+?)</ORGANIZATION>");
	private final Pattern patternLocation = Pattern.compile("<LOCATION>(.+?)</LOCATION>");

	public StanfordNER(String classifierPath) throws Exception{
		classifier = CRFClassifier.getClassifier(classifierPath);
	}

	public boolean processDocument(ELDocument elDocument){
		String output = this.classifier.classifyWithInlineXML(elDocument.getDocText());
		//		System.out.println(output);
		ArrayList<AnnotatedMention> mentionList = new ArrayList<AnnotatedMention>();

		//Person
		Matcher matcher = this.patternPerson.matcher(output);
		while(matcher.find()){			
			String group = matcher.group(1);
			mentionList.add(new AnnotatedMention(group, "PERSON", null));
			//			System.out.println(matcher.group(1));
		}

		//Location
		matcher = this.patternLocation.matcher(output);
		while(matcher.find()){
			String group = matcher.group(1);
			mentionList.add(new AnnotatedMention(group, "LOCATION", null));
		}

		//			System.out.println(matcher.group(1));

		//Person
		matcher = this.patternOrganization.matcher(output);
		while(matcher.find()){
			String group = matcher.group(1);
			mentionList.add(new AnnotatedMention(group, "ORGANIZATION", null));
		}

		elDocument.setAnnotatedMention(mentionList);
		return true;
	}


	public static void main(String[] args) throws Exception {
		String serializedClassifierPath = "/Users/Nitish/Work/Projects/MixedEmotion/EntityLinking/ner/english.all.3class.distsim.crf.ser.gz";
		StanfordNER stanfordNER = new StanfordNER(serializedClassifierPath);

		String text = "Good afternoon Rajat Raina and Mikesh Singh, you are going school at Stanford University, which is located in California.";
		ELDocument document = new ELDocument(text, null);
		
		stanfordNER.processDocument(document);
		List<AnnotatedMention> annotatedMention = document.getAnnotatedMention();
		
		for(AnnotatedMention mention: annotatedMention){
			System.out.println(mention.getMention() + " => " + mention.getClassType());
		}
	}
}

