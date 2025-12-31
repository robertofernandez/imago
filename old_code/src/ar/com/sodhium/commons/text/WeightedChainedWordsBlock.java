package ar.com.sodhium.commons.text;

public class WeightedChainedWordsBlock implements Comparable<WeightedChainedWordsBlock> {
    private ChainedWordsBlock content;
    private Double weight;
    private Double complementaryWeight;

    public WeightedChainedWordsBlock(ChainedWordsBlock content, Double weight, Double complementaryWeight) {
        super();
        this.content = content;
        this.weight = weight;
        this.complementaryWeight = complementaryWeight;
    }

    public Double getTotalWeight() {
        return weight + complementaryWeight;
    }

    @Override
    public int compareTo(WeightedChainedWordsBlock anotherBlock) {
        return getTotalWeight().compareTo(anotherBlock.getTotalWeight());
    }

    public ChainedWordsBlock getContent() {
        return content;
    }

    public Double getComplementaryWeight() {
        return complementaryWeight;
    }
    
    @Override
    public String toString() {
        return "[" + getTotalWeight() + "] " + content.toString();
    }
}
