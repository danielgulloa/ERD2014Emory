package edu.emory.erd.types;

import edu.emory.erd.types.Text;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TextTest {
    private Text text;
    private Sentence[] sentences;

    @Before
    public void setUp() throws Exception {
        text = new Text("This is my sample document.   It has 2   sentences!!! And this is the third sentence.");
        sentences = text.getSentences();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testSentenceSplit() {
        assertEquals(3, sentences.length);
        assertEquals("And this is the third sentence.", sentences[2].getText());
        // !!! is considered "words", so we have 7 in total.
        assertEquals(7, sentences[1].getWordsCount());
        assertEquals("third", sentences[2].getWord(4));
    }

    @Test
    public void testSentenceTextVsText() {
        assertEquals(sentences[1].getText(), text.getSpanText(sentences[1].getSpan()));
    }

    /**
     * Checks that word span location in the original document is correct.
     */
    @Test
    public void testWordSpanPositions() {
        assertEquals(sentences[1].getWord(3), text.getSpanText(sentences[1].getWordSpan(3)));
    }

}
