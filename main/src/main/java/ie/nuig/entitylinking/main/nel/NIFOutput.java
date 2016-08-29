package ie.nuig.entitylinking.main.nel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author John McCrae <john@mccr.ae>
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class NIFOutput {
    private final String context = "http://mixedemotions-project.eu/ns/context.jsonld";
    private String id;
    private List<Analysis> analysis = Collections.singletonList(new Analysis());
    private List<Entry> entries;

    @JsonProperty("@id") public String getId() {
        return id;
    }

    @JsonProperty("@id") public void setId(String id) {
        this.id = id;
    }

    public List<Analysis> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @JsonProperty("@context") public String getContext() {
        return context;
    }


    public static class Analysis {
        private String id = "me:EntityLinking";
        private String type = "me:NERAnalysis";

        @JsonProperty("@id") public String getId() {
            return id;
        }

        @JsonProperty("@id") public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("@type") public String getType() {
            return type;
        }

        @JsonProperty("@type") public void setType(String type) {
            this.type = type;
        }
    }

    public static class Entity {
        private String id;
        private int beginIndex, endIndex;
        private String anchorOf;
        private String references;
        private double score;
        private final String wasGeneratedBy = "me:EntityLinking";

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        @JsonProperty("@id") public String getId() {
            return id;
        }

        @JsonProperty("@id") public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("nif:beginIndex") public int getBeginIndex() {
            return beginIndex;
        }

        @JsonProperty("nif:beginIndex") public void setBeginIndex(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        @JsonProperty("nif:endIndex") public int getEndIndex() {
            return endIndex;
        }

        @JsonProperty("nif:endIndex") public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        @JsonProperty("nif:anchorOf") public String getAnchorOf() {
            return anchorOf;
        }

        @JsonProperty("nif:anchorOf") public void setAnchorOf(String anchorOf) {
            this.anchorOf = anchorOf;
        }

        @JsonProperty("me:references") public String getReferences() {
            return references;
        }

        @JsonProperty("me:references") public void setReferences(String references) {
            this.references = references;
        }

        @JsonProperty("prov:wasGeneratedBy") public String getWasGeneratedBy() {
            return wasGeneratedBy;
        }


    }
    
    public static class Entry {
        private String id;
        private List<String> type = Arrays.asList("nif:RFC5147String", "nif:Context");
        private List<Entity> entities;
        private List<Object> suggestions = Collections.EMPTY_LIST;
        private List<Object> sentiments = Collections.EMPTY_LIST;
        private List<Object> emotionSets = Collections.EMPTY_LIST;

        @JsonProperty("@id") public String getId() {
            return id;
        }

        @JsonProperty("@id") public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("@type") public List<String> getType() {
            return type;
        }

        @JsonProperty("@type") public void setType(List<String> type) {
            this.type = type;
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public void setEntities(List<Entity> entities) {
            this.entities = entities;
        }

        public List<Object> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<Object> suggestions) {
            this.suggestions = suggestions;
        }

        public List<Object> getSentiments() {
            return sentiments;
        }

        public void setSentiments(List<Object> sentiments) {
            this.sentiments = sentiments;
        }

        public List<Object> getEmotionSets() {
            return emotionSets;
        }

        public void setEmotionSets(List<Object> emotionSets) {
            this.emotionSets = emotionSets;
        }

        

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.context != null ? this.context.hashCode() : 0);
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.analysis != null ? this.analysis.hashCode() : 0);
        hash = 89 * hash + (this.entries != null ? this.entries.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NIFOutput other = (NIFOutput) obj;
        if ((this.context == null) ? (other.context != null) : !this.context.equals(other.context)) {
            return false;
        }
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.analysis != other.analysis && (this.analysis == null || !this.analysis.equals(other.analysis))) {
            return false;
        }
        if (this.entries != other.entries && (this.entries == null || !this.entries.equals(other.entries))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NIFOutput{" + "context=" + context + ", id=" + id + ", analysis=" + analysis + ", entries=" + entries + '}';
    }


}