package edu.emory.erd.types;

import edu.emory.erd.LexiconMentionBuilder;
import edu.emory.erd.MentionBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class LexiconMentionBuilderTest {

    MentionBuilder mentionBuilder;

    @Before
    public void setUp() throws Exception {
        mentionBuilder = new LexiconMentionBuilder(
                LexiconMentionBuilderTest.class.getResourceAsStream("/entity_lexicon.txt"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMentionBuilder() {
        Text text = new Text("I saw Mark Byford in the Possum town");
        List<Annotation> annotations = mentionBuilder.buildMentions(text);
        // Mark, Mark Byford, Byford, Possum town
        assertEquals(4, annotations.size());
        assertEquals("/m/02qgwd", annotations.get(0).getEntityInfo().getId());
    }
}
