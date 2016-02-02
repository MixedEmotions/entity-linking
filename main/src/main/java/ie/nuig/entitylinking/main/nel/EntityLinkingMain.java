package ie.nuig.entitylinking.main.nel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import ie.nuig.entitylinking.core.AnnotatedMention;
import ie.nuig.entitylinking.core.ELDocument;
import ie.nuig.entitylinking.core.Pair;
import ie.nuig.entitylinking.core.utils.BasicUtils;
import ie.nuig.entitylinking.disamb.prior.EntityDisambPriorWikiAnchor;
import ie.nuig.entitylinking.ner.stanford.StanfordNER;

public class EntityLinkingMain {
	private StanfordNER stanfordNER = null;
	private EntityDisambPriorWikiAnchor entityDisambigution = null; 

	public EntityLinkingMain(String configPath){
		Properties config = null;
		try {
			config = BasicUtils.loadConfig(configPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String nerClassifierPath = config.getProperty("nerClassifierPath");
		try {
			this.stanfordNER =  new StanfordNER(nerClassifierPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String wikiAnchorIndexPath = config.getProperty("wikiAnchorIndexPath");
		entityDisambigution = new EntityDisambPriorWikiAnchor(wikiAnchorIndexPath);
	}

	public boolean processDocument(ELDocument elDocument){		
		this.stanfordNER.processDocument(elDocument);
		this.entityDisambigution.processDocument(elDocument);		
		return true;
	}

	public static void main(String[] args) throws Exception {
		String configPath = "/Users/Nitish/Work/EntityLinking/ie.nuig.entitylinking.properties";
		EntityLinkingMain entityLinkingMain = new EntityLinkingMain(configPath);
		
		String docText = "IBM Research and APS internships for undergrad women & minorities. Applications due Feb 15";
		ELDocument elDocument = new ELDocument(docText, null);		

		entityLinkingMain.processDocument(elDocument);

		List<AnnotatedMention> annotatedMentions = elDocument.getAnnotatedMention();
		for(AnnotatedMention annotatedMention: annotatedMentions){
			System.out.println("Mention: "+annotatedMention.getMention() + "\tclass: "+annotatedMention.getClassType());
			List<Pair<String, Double>> linkScorePairs = annotatedMention.getRankedListCandidates();
			for(Pair<String, Double> pair: linkScorePairs){
				System.out.println(pair.getKey() +"\t"+pair.getValue());
			}
		}
	}
}
