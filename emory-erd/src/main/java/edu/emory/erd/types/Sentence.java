package edu.emory.erd.types;

import com.hp.hpl.jena.util.iterator.ArrayIterator;
import edu.emory.erd.util.NlpUtils;
import opennlp.tools.util.Span;

import java.util.Iterator;

/**
 * Represents a sentence of text and contains array of words
 */
final public class Sentence implements Iterable<String> {
    private final String rawSentenceText;
    // Current sentence span in the original text.
    private final Span sentenceSpan;
    // Words
    private final String[] words;
    private final Span[] wordSpans;

    /**
     * Create a sentence with the given text and offset in the original text.
     * @param text Text of the sentence
     * @param beginning Offset of the sentence in the original text
     */
    public Sentence(String text, int beginning) {
        rawSentenceText = text;
        sentenceSpan = new Span(beginning, beginning + text.length());
        words = NlpUtils.tokenize(getText());
        wordSpans = new Span[words.length];
        // Now we want to get spans (offsets in chars) for all words.
        int currentPos = 0;
        for (int i = 0; i < words.length; ++i) {
            // TODO: this is too slow, but I don't know how to get offsets from OpenNLP.
            currentPos = rawSentenceText.indexOf(words[i], currentPos);
            // This should never happen!
            assert currentPos != -1;
            wordSpans[i] = new Span(sentenceSpan.getStart() + currentPos,
                                    sentenceSpan.getStart() + currentPos + words[i].length());
            currentPos += words[i].length();
        }
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
        return rawSentenceText;
    }

    /**
     * Returns sentence words.
     * @return array of string, containing sentence words.
     */
    public String[] getWords() {
        return words;
    }

    /**
     * Returns span for index-th word.
     * @param index Index of the word to return Span for.
     * @return Span that gives position of word in the original document.
     */
    public Span getWordSpan(int index) {
        return wordSpans[index];
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