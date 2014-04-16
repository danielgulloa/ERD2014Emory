package edu.emory.erd.types;

import com.hp.hpl.jena.util.iterator.ArrayIterator;
import edu.emory.erd.util.NlpUtils;
import opennlp.tools.util.Span;

import java.util.Iterator;

/**
 * Represents a sentence of text and contains array of words
 */
final public class Sentence implements Iterable<String> {
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
        words = NlpUtils.tokenize(getText());
    }

    /**
     * Returns the span for this sentence in the original text.
     * @return Span object, which can be used to detect position of the sentence in the original text.
     */
    public Span getSpan() {
        return sentenceSpan;
    }

    /**
     * Gets text of the sentence.
     * @return String text of the sentence.
     */
    public String getText() {
        return rawText;
    }

    /**
     * Returns sentence words.
     * @return array of string, containing sentence words.
     */
    public String[] getWords() {
        return words;
    }

    /**
     * Returns iterator over sentence words.
     * @return Iterator<String> for sentence words.
     */
    @Override
    public Iterator<String> iterator() {
        return new ArrayIterator(words);
    }

    @Override
    public String toString() {
        return getText();
    }
}