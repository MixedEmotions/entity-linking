package ie.nuig.entitylinking.disamb.prior;

import ie.nuig.entitylinking.core.Pair;
import ie.nuig.entitylinking.core.utils.BasicUtils;
import ie.nuig.entitylinking.disamb.lucene.Searcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.search.TopScoreDocCollector;


/**
 * @author naggarw
 *
 */

public class WikiAnchorPriorSearch {

	private static final String CANDIDATE_URI_FIELD = "URI";
	private static final String CANDIDATE_ANCHOR_FIELD =  "Anchor";
	private static final String CANDIDATE_PRIORSCORE_FIELD =  "PriorScore";
	
	private static final String DBpediaNameSpace =  "http://dbpedia.org/page/";

	private static int luceneHits = 50000; 

	private Properties config = null;

	private Searcher searcher ;


	public WikiAnchorPriorSearch(String indexPath){
		initialize(indexPath);
	}

	private void initialize(String indexPath){
		this.searcher = new Searcher(indexPath, false);
	}

//	private void initialize(){
//		String indexPath = this.config.getProperty("anchorsPriorIndexPath");
//		boolean RAM = Boolean.getBoolean(config.getProperty("RAM"));
//		this.searcher = new Searcher(indexPath, RAM);
//	}

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


	public List<Pair<String, Double>> getURIScorePair(String anchor){
		TopScoreDocCollector docCollector = this.searcher.termQuerySearch(anchor, CANDIDATE_ANCHOR_FIELD, luceneHits);
		org.apache.lucene.search.ScoreDoc[] scoreDocs = docCollector.topDocs().scoreDocs;

		Map<String, Double> uriScoreMap = new HashMap<String, Double>();
		for(int docNo = 0; docNo < scoreDocs.length; ++docNo) {
			int docID = scoreDocs[docNo].doc;
			org.apache.lucene.document.Document document = searcher.getDocumentWithDocID(docID);			
//			uriScoreMap.put(document.get(CANDIDATE_URI_FIELD), Double.parseDouble(document.get(CANDIDATE_PRIORSCORE_FIELD)));
			uriScoreMap.put(DBpediaNameSpace + document.get(CANDIDATE_URI_FIELD), Double.parseDouble(document.get(CANDIDATE_PRIORSCORE_FIELD)));	
		}
		List<String> sortedkeyValueList = BasicUtils.map2SortedkeyValueList(uriScoreMap, "\t");
		List<Pair<String, Double>> results = new ArrayList<Pair<String,Double>>();
		
		for(String s : sortedkeyValueList){
			String[] split = s.split("\t");
			results.add(new Pair<String, Double>(split[0], Double.parseDouble(split[1])));
			}
		
		return results;
//		return BasicUtils.map2SortedkeyValuePair(uriScoreMap);
	}
	
	
	public List<String> getURIs(String anchor){
		TopScoreDocCollector docCollector = this.searcher.termQuerySearch(anchor, CANDIDATE_ANCHOR_FIELD, luceneHits);
		org.apache.lucene.search.ScoreDoc[] scoreDocs = docCollector.topDocs().scoreDocs;

		Map<String, Double> uriScoreMap = new HashMap<String, Double>();
		for(int docNo = 0; docNo < scoreDocs.length; ++docNo) {
			int docID = scoreDocs[docNo].doc;
			org.apache.lucene.document.Document document = searcher.getDocumentWithDocID(docID);			
			uriScoreMap.put(document.get(CANDIDATE_URI_FIELD), Double.parseDouble(document.get(CANDIDATE_PRIORSCORE_FIELD)));
			}
				
		return BasicUtils.map2SortedKeyList(uriScoreMap);
	}
	
	public static void main(String[] args) {
		String indexPath = "/Users/Nitish/Work/EntityLinking/WikiLex_AnchorsPrior";
		WikiAnchorPriorSearch anchorSearch = new WikiAnchorPriorSearch(indexPath); 
		List<Pair<String, Double>> uriScorePairs = anchorSearch.getURIScorePair("apple inc.");
		
		int count = 1;
		System.out.println(uriScorePairs.size());
		for(Pair<String, Double> uriScorePair: uriScorePairs)
			System.out.println(count++ +": "+ uriScorePair.getKey() +"\t"+uriScorePair.getValue());
	}
}