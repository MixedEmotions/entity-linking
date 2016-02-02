package ie.nuig.entitylinking.disamb.prior;


import ie.nuig.entitylinking.core.AnnotatedMention;
import ie.nuig.entitylinking.core.ELDocument;
import ie.nuig.entitylinking.core.Pair;

import java.util.ArrayList;
import java.util.List;

public class EntityDisambPriorWikiAnchor{
	private WikiAnchorPriorSearch wikiAnchorPriorSearch = null;
	private int maxCandidates = 20;
	
	public EntityDisambPriorWikiAnchor(String wikiAnchorIndexPath){
		wikiAnchorPriorSearch = new WikiAnchorPriorSearch(wikiAnchorIndexPath);
	}

	public boolean processDocument(ELDocument elDocument){
		List<AnnotatedMention> annotatedMentions = elDocument.getAnnotatedMention();
		for(AnnotatedMention annotatedMention: annotatedMentions){
			String mentionText = annotatedMention.getMention();
			List<Pair<String, Double>> linkScorePairs = wikiAnchorPriorSearch.getURIScorePair(mentionText.toLowerCase());

			//set best uri to uriScorePair
			if(!linkScorePairs.isEmpty())
				annotatedMention.setUriScorePair(linkScorePairs.get(0));
			else
				annotatedMention.setUriScorePair(new Pair<String, Double>("--",0.0));
			
			if(linkScorePairs.size()>maxCandidates)
				linkScorePairs = new ArrayList<Pair<String, Double>>(linkScorePairs.subList(0, maxCandidates)); 
			annotatedMention.setRankedListCandidates(linkScorePairs);
		}
		return true;
	}
}
