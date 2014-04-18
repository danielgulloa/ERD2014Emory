package edu.emory.erd.types;

import com.hp.hpl.jena.util.iterator.ArrayIterator;
import edu.emory.erd.util.NlpUtils;
import edu.stanford.nlp.ling.Word;
import opennlp.tools.util.Span;

import java.util.Iterator;

/**
 * Represents a sentence of text and contains array of words
 */
final public class Sentence implements Iterable<Word> {
    private final String rawSentenceText;
    // Current sentence span in the original text.
    private final Span sentenceSpan;
    // Words
    private final Word[] words;

    /**
     * Create a sentence with the given text and offset in the original text.
     * @param text Text of the sentence
     * @param beginning Offset of the sentence in the original text
     */
    public Sentence(String text, int beginning) {
        rawSentenceText = text;
        sentenceSpan = new Span(beginning, beginning + text.length());
        String[] wordsStr = NlpUtils.tokenize(getText());
        words = new Word[wordsStr.length];
        // Now we want to get spans (offsets in chars) for all words.
        int currentPos = 0;
        for (int i = 0; i < words.length; ++i) {
            // TODO: this is too slow, but I don't know how to get offsets from OpenNLP.
            currentPos = rawSentenceText.indexOf(wordsStr[i], currentPos);
            // This should never happen!
            assert currentPos != -1;
            words[i] = new Word(wordsStr[i], sentenceSpan.getStart() + currentPos,
                    sentenceSpan.getStart() + currentPos + wordsStr[i].length());
            currentPos += wordsStr[i].length();
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
     * Returns text of index-th word of a sentence.
     * @param index of word to return
     * @return String value of index-th word of the sentence.
     */
    public String getWord(int index) {
        return words[index].word();
    }

    /**
     * Returns sentence as array of words.
     * @return array of Words.
     */
    public Word[] getWords() {
        return words;
    }

    /**
     * @return Returns the number of words in the sentence.
     */
    public int getWordsCount() {
        return words.length;
    }


    /**
     * Returns span for index-th word.
     * @param index Index of the word to return Span for.
     * @return Span that gives position of word in the original document.
     */
    public Span getWordSpan(int index) {
        return new Span(words[index].beginPosition(), words[index].endPosition());
    }

    /**
     * Returns iterator over sentence words.
     * @return Iterator<Word> for sentence words.
     */
    @Override
    public Iterator<Word> iterator() {
        return new ArrayIterator(words);
    }

    @Override
    public String toString() {
        return getText();
    }
}