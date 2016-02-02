package ie.nuig.entitylinking.core;

import java.util.List;

public class ELDocument {
	String docText = null;
	List<AnnotatedMention> annotatedMention = null;
	
	public ELDocument(String docText, List<AnnotatedMention> annotatedMention) {
		super();
		this.docText = docText;
		this.annotatedMention = annotatedMention;
	}

	public String getDocText() {
		return docText;
	}

	public void setDocText(String docText) {
		this.docText = docText;
	}

	public List<AnnotatedMention> getAnnotatedMention() {
		return annotatedMention;
	}

	public void setAnnotatedMention(List<AnnotatedMention> annotatedMention) {
		this.annotatedMention = annotatedMention;
	}

	
}
