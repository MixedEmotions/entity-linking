package ie.nuig.entitylinking.disamb.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class Searcher {
	private final static int maxClauseCount = 6144;
	private IndexReader reader;
	private IndexSearcher searcher;

	public Searcher(String indexPath, boolean cach){
		BooleanQuery.setMaxClauseCount(maxClauseCount);		
		setIndexReader(getIndex(indexPath, cach));
		setIndexSearcher();		
	}

	public Searcher(IndexReader reader){
		//		BooleanQuery.setMaxClauseCount(maxClauseCount);		
		setIndexReader(reader);
		setIndexSearcher();		
	}

	private void setIndexReader(IndexReader reader) {
		this.reader = reader;
	}

	private void setIndexReader(Directory indexDir) {
		try {
			this.reader = DirectoryReader.open(indexDir);
			indexDir.close();			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void setIndexSearcher() {
		this.searcher = new IndexSearcher(this.reader);
		//		this.searcher.setSimilarity(new NTFIDF());
	}

	public boolean closeIndex() {		
		try {
			//			searcher.close();
			reader.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	private Directory getIndex(String indexPath, boolean RAM) {		
		Directory index = null;
		try {			
			if(RAM) {
				index = new MMapDirectory(new File(indexPath + System.getProperty("file.separator")));
				System.out.println("index uploaded with efficient caching");
				return index;
			}
			else{
				index = new SimpleFSDirectory(new File(indexPath + 
						System.getProperty("file.separator")));
				return index;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return index;
	}


	public int getTotalDocs(){
		return reader.maxDoc();
		//searcher.maxDoc();
	}

	//	public long getTotalUniqueTerms(){
	//		long termCount = 0;
	//		IndexReader[] subReaders = reader.getSequentialSubReaders();
	//		for(IndexReader subReader: subReaders)
	//			try {
	//				termCount = termCount + subReader.getUniqueTermCount();
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		return termCount; 
	//	}

	private TopScoreDocCollector search(Query query, int lucHits) {
		//		new Sort
		TopScoreDocCollector collector = TopScoreDocCollector.create(lucHits, true);
		try {
			searcher.search(query, collector);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return collector;				
	}

	public TopScoreDocCollector termQuerySearch(String queryString, String fieldName, int lucHits) {		
		TermQuery query = new TermQuery(new Term(fieldName, queryString));
		return search(query, lucHits);
	}

	public Document getDocumentWithDocID(int docID) {
		try {
			return searcher.doc(docID);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TopScoreDocCollector multiFieldTermSearch(String[] queryStrings, int lucHits, String[] fieldsName) {
		BooleanQuery query = new BooleanQuery();
		for(int count =0; count< queryStrings.length; count++){
			TermQuery termQuery = new TermQuery(new Term(fieldsName[count], queryStrings[count]));
			query.add(termQuery, Occur.MUST);
		}
		return this.search(query, lucHits);
	}

	public TopScoreDocCollector search(String queryString, int lucHits, String fieldName, Analyzer analyzer) {
		queryString = QueryParser.escape(queryString.trim());		
		if(queryString.equalsIgnoreCase("")) 
			return null;
		Query query = null;
		try {
			QueryParser queryParser = new QueryParser(Version.LUCENE_46, fieldName, analyzer);			
			query = queryParser.parse(queryString);
		} catch (ParseException e) {
			e.printStackTrace();
		}						
		return search(query, lucHits);	
	}

	public TopFieldDocs searchTermSortedByField(String termVal, String fieldName, String sortbyFieldName, Type sortedFieldType, int hits){
		SortField sortField = new SortField(sortbyFieldName, sortedFieldType, true);
		Sort sort = new Sort(sortField);
		TopFieldDocs topFieldDocs = null;
		TermQuery query = new TermQuery(new Term(fieldName, termVal));		
		try {
			topFieldDocs = this.searcher.search(query, hits, sort);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return topFieldDocs;
	}

	public double getIDF(Term term){
		int docFreq = 1;
		try {
			docFreq = this.reader.docFreq(term);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double idf = Math.log(this.reader.maxDoc() / (docFreq + 1.0)) + 1;

		return idf;
	}


}
