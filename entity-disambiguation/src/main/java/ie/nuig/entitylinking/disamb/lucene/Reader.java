package ie.nuig.entitylinking.disamb.lucene;



import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;


public class Reader {
	private Directory index;
	private IndexReader reader;
	
	public Reader(Directory index){
		this.index = index;		
		openIndex();
	}

	private void openIndex() {
		try {			
//			this.reader = IndexReader.open(this.index);
			this.reader = DirectoryReader.open(index);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean closeIndex() {		
		try {
			reader.close();
			index.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public IndexReader getIndexReader() {
		return this.reader;
	}
	
	public int totalDocuments() {
		return reader.maxDoc();		
	}	

//	public Boolean isDeleted(int i) {
//		return reader.isDeleted(i);
//	}

	public Document getDocumentWithDocId(int docId) {
		try {
			return reader.document(docId);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}	

}
