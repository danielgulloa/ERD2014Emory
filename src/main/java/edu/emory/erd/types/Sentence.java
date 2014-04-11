package edu.emory.erd.types;

import edu.emory.erd.util.NlpUtils;
import opennlp.tools.util.Span;

/**
 * Represents a sentence of text and contains array of words
 */
final public class Sentence {
    private final String rawText;
    // Current sentence span in the original text.
    private final Span sentenceSpan;
    // Words
    private final String[] words;

    /**
     * Create a sentence with the given text and offset in the original text.
     * @param text Text of the sentence
     * @param beginning Offset of the sentence in the original text
     */
    public Sentence(String text, int beginning) {
        rawText = text;
        sentenceSpan = new Span(beginning, beginning + text.length());
        words = NlpUtils.tokenize(rawText);
    }
}