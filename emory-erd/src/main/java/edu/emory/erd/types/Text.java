package edu.emory.erd.types;

import edu.emory.erd.util.NlpUtils;

import opennlp.tools.util.Span;

/**
 * Represents text. We store both original String information as well as token information.
 */
final public class Text {
    private final String rawText;
    private final Sentence[] sentences;

    /**
     * Create Text object for the given String text. Text object will split text into sentences and tokenize each
     * sentence.
     * @param text Text to create an object for.
     */
    public Text(String text) {
        rawText = text;
        // Get String[] for sentences
        String[] sentencesText = NlpUtils.detectSentences(rawText);
        sentences = new Sentence[sentencesText.length];
        int index = 0;
        for (String sentText : sentencesText) {
            // TODO: searching for a sentence each time is really bad, but sentence detector strips extra punctuation.
            sentences[index++] = new Sentence(sentText, text.indexOf(sentText));
        }
    }

    /**
     * Returns text of some span of the text.
     * @param span Span to return text for.
     * @return String of span text.
     */
    public String getSpanText(Span span) {
        return rawText.substring(span.getStart(), span.getEnd());
    }

    /**
     * Returns text sentences.
     * @return Array of Sentences, each string contains a sentence.
     */
    public Sentence[] getSentences() {
        return sentences;
    }

    /**
     * Returns document text.
     * @return String text of the current document.
     */
    public String getText() {
        return rawText;
    }
}
