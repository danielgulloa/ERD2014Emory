package edu.emory.erd.util;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;

/**
 * This class uses OpenNLP to split text into sentences.
 */
public class NlpUtils {

    private static SentenceDetector sentenceDetector;
    private static Tokenizer tokenizer;

    static {
        // Create sentence detector from OpenNLP
        try {
            sentenceDetector = new SentenceDetectorME(
                    new SentenceModel(NlpUtils.class.getClassLoader().getResourceAsStream("opennlp-models/en-sent.bin")));
        } catch (IOException e) {
            sentenceDetector = null;
        }
        assert sentenceDetector != null;

        try {
            // Create tokenizer
            tokenizer = new TokenizerME(
                    new TokenizerModel(NlpUtils.class.getClassLoader().getResourceAsStream("opennlp-models/en-token.bin")));
        } catch (IOException e) {
            tokenizer = null;
        }
        assert tokenizer != null;
    }

    /**
     * Splits text into sentences
     * @param text text to split into sentences
     * */
    public static String[] detectSentences(String text) {
        return sentenceDetector.sentDetect(text);
    }

    /**
     * Tokenizes text into tokens/words.
     * @param text text to tokenize
     * */
    public static String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }
}