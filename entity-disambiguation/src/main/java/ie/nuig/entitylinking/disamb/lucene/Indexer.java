package ie.nuig.entitylinking.disamb.lucene;


import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;



public class Indexer {

	private Directory index = null;
	private IndexWriterConfig config; 
	private IndexWriter writer;	

	public Indexer(IndexWriterConfig config, Directory index) {
		this.config = config;
		this.index = index;
		openIndexWriter();		
	}

	private void openIndexWriter() {
		if(writer!=null)
			closeIndexer();
		try {
			writer = new IndexWriter(index, config);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		writer.setSimilarity(new NTFIDF());
	}

	public void closeIndexer(){
		try {
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDoc(Document doc) {
		try {
			writer.addDocument(doc);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IndexWriter getWriter() {
		return writer;
	}
	
	
}