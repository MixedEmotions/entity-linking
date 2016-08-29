package ie.nuig.entitylinking.main.nel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * NIF Input object from JSON-LD
 * @author John McCrae <john@mccr.ae>
 */
@JsonIgnoreProperties("@context")
public class NIFInput {
    private final String context = "http://mixedemotions-project.eu/ns/context.jsonld";
    private String id;
    private List<Object> analysis;
    private List<Entry> entries;

    @JsonProperty("@id") public String getId() {
        return id;
    }

    @JsonProperty("@id") public void setId(String id) {
        this.id = id;
    }

    public List<Object> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<Object> analysis) {
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


    
    public static class Entry {
        private String id;
        private List<String> type;
        private int beginIndex, endIndex;
        private String isString;

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

        @JsonProperty("nif:isString") public String getIsString() {
            return isString;
        }

        @JsonProperty("nif:isString") public void setIsString(String isString) {
            this.isString = isString;
        }

        @Override
        public String toString() {
            return "Entry{" + "id=" + id + ", type=" + type + ", beginIndex=" + beginIndex + ", endIndex=" + endIndex + ", isString=" + isString + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
            hash = 41 * hash + (this.type != null ? this.type.hashCode() : 0);
            hash = 41 * hash + this.beginIndex;
            hash = 41 * hash + this.endIndex;
            hash = 41 * hash + (this.isString != null ? this.isString.hashCode() : 0);
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
            final Entry other = (Entry) obj;
            if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
                return false;
            }
            if (this.type != other.type && (this.type == null || !this.type.equals(other.type))) {
                return false;
            }
            if (this.beginIndex != other.beginIndex) {
                return false;
            }
            if (this.endIndex != other.endIndex) {
                return false;
            }
            if ((this.isString == null) ? (other.isString != null) : !this.isString.equals(other.isString)) {
                return false;
            }
            return true;
        }

    }

    @Override
    public String toString() {
        return "NIFInput{" + "context=" + context + ", id=" + id + ", analysis=" + analysis + ", entries=" + entries + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.context != null ? this.context.hashCode() : 0);
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.analysis != null ? this.analysis.hashCode() : 0);
        hash = 23 * hash + (this.entries != null ? this.entries.hashCode() : 0);
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
        final NIFInput other = (NIFInput) obj;
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
    
    
}
