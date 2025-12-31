package ar.com.sodhium.commons.text;

public class WordsProcessor {
    private SeparatorsSet separators;
    private WordsWeightManager wordsWeightManager;

    public WordsProcessor(SeparatorsSet separators) {
        this.separators = separators;
    }

    public void setWordsWeightManager(WordsWeightManager wordsWeightManager) {
        this.wordsWeightManager = wordsWeightManager;
    }

    public ChainedWordsBlock getBlock(String input) {
        return new ChainedWordsBlock(input, separators);
    }

    public WeightedChainedWordsBlock getEmpyBlock() {
        ChainedWordsBlock content = new ChainedWordsBlock("");
        return new WeightedChainedWordsBlock(content, 0D, 0D);
    }

    private WeightedChainedWordsBlock getLongestCommonSequence(ChainedWordsBlock block1, ChainedWordsBlock block2,
            int initialWord1, int initialWord2) {
        int i = initialWord1;
        int j = initialWord2;
        Double weightBlock1 = 0D;
        Double weightBlock2 = 0D;

        while (i < block1.getWords().size() && j < block2.getWords().size()) {
            if (block1.getWords().get(i).matches(block2.getWords().get(j))) {
                weightBlock1 += wordsWeightManager.getWeight(block1, i);
                weightBlock2 += wordsWeightManager.getWeight(block2, j);
                i++;
                j++;
            } else {
                break;
            }
        }
        if (i == initialWord1) {
            return getEmpyBlock();
        } else {
            ChainedWordsBlock outputBlock = block1.getSubBlock(initialWord1, i);
            return new WeightedChainedWordsBlock(outputBlock, weightBlock1, weightBlock2);
        }
    }

    public WeightedChainedWordsBlock getLongestCommonSequence(ChainedWordsBlock block1, ChainedWordsBlock block2) {
        WeightedChainedWordsBlock output = getEmpyBlock();
        for (int i = 0; i < block1.getWords().size(); i++) {
            for (int j = 0; j < block2.getWords().size(); j++) {
                WeightedChainedWordsBlock longestCommonSequence = getLongestCommonSequence(block1, block2, i, j);
                if (output.compareTo(longestCommonSequence) < 0) {
                    output = longestCommonSequence;
                }
            }
        }
        return output;
    }

    public WeightedChainedWordsBlock getLongestCommonSequence(String text1, String text2) {
        return getLongestCommonSequence(new ChainedWordsBlock(text1), new ChainedWordsBlock(text2));
    }
}
