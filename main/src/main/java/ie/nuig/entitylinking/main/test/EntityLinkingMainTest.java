package ie.nuig.entitylinking.main.test;

import ie.nuig.entitylinking.core.AnnotatedMention;
import ie.nuig.entitylinking.core.ELDocument;
import ie.nuig.entitylinking.main.nel.EntityLinkingMain;

import java.util.List;

public class EntityLinkingMainTest {
	
	public static void main(String[] args) throws Exception {

		String configPath = "/Users/Nitish/Work/EntityLinking/ie.nuig.entitylinking.properties";
		EntityLinkingMain entityLinkingDemo = new EntityLinkingMain(configPath);

		String docText = "IBM Research and APS internships for undergrad women & minorities. Applications due Feb 15";
		ELDocument elDocument = new ELDocument(docText, null);		

		entityLinkingDemo.processDocument(elDocument);

		List<AnnotatedMention> annotatedMentions = elDocument.getAnnotatedMention();
		for(AnnotatedMention annotatedMention: annotatedMentions){
				System.out.println("Mention: "+annotatedMention.getMention() + "\tclass: "+annotatedMention.getClassType() +"\tURI: "+
						annotatedMention.getUriScorePair().getKey() +"\tscore: "+annotatedMention.getUriScorePair().getValue());
		}

	}
}
