public class SimilarWord {
    private String key;
    private String value;
    private int similarityIndex;

    public SimilarWord(String key, String value) {
        this.key = key;
        this.value = value;
        this.similarityIndex = Main.wordCompare(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this.value.equals(((SimilarWord) obj).value)) {
            return true;
        } else
            return false;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int getSimilarityIndex() {
        return similarityIndex;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + ":" + this.value + "\n";
    }
}
