package ie.nuig.entitylinking.core;


import java.util.List;

public class AnnotatedMention {

	private String mention = null;
	private String classType = null;
	private List<Pair<String, Double>> rankedListCandidates = null;
	
	private Pair<String, Double> uriScorePair = null;
	
	public AnnotatedMention(String mention, String classType, List<Pair<String, Double>> RankedListCandidates) {
		super();
		this.mention = mention;
		this.classType = classType;
		this.rankedListCandidates = RankedListCandidates;
	}

	public AnnotatedMention(String mention, String classType, List<Pair<String, Double>> RankedListCandidates,Pair<String, Double> uriScorePair) {
		super();
		this.mention = mention;
		this.classType = classType;
		this.rankedListCandidates = RankedListCandidates;
		this.setUriScorePair(uriScorePair);
	}

	
	public String getMention() {
		return mention;
	}


	public void setMention(String mention) {
		this.mention = mention;
	}


	public List<Pair<String, Double>> getRankedListCandidates() {
		return rankedListCandidates;
	}


	public void setRankedListCandidates(List<Pair<String, Double>> RankedListCandidates) {
		this.rankedListCandidates = RankedListCandidates;
	}


	public String getClassType() {
		return classType;
	}


	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Pair<String, Double> getUriScorePair() {
		return uriScorePair;
	}

	public void setUriScorePair(Pair<String, Double> uriScorePair) {
		this.uriScorePair = uriScorePair;
	}
	
	
	
}
