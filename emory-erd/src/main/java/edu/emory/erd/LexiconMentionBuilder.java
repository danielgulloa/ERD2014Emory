package edu.emory.erd;

import edu.emory.erd.types.*;
import opennlp.tools.util.Span;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Finds entity mentions using sliding window and lexicon.
 */
public class LexiconMentionBuilder implements MentionBuilder {
    private PatriciaTrie<List<String>> lexicon;
    private MultiKeyMap<String, Long> entityPhraseCount;  // the number of mentions of entity with phrase
    private HashMap<String, Long> entityCount;  // the number of mentions for each entity.
    private long totalCount; // total number of mentions.

    public LexiconMentionBuilder(InputStream lexiconStream) throws IOException {
        // Initialize fields.
        lexicon = new PatriciaTrie<List<String>>();
        entityPhraseCount = MultiKeyMap.multiKeyMap(new HashedMap<MultiKey<? extends String>, Long>());
        entityCount = new HashMap<String, Long>();
        totalCount = 0;

        BufferedReader lexiconReader = new BufferedReader(new InputStreamReader(lexiconStream));
        while (lexiconReader.ready()) {
            String[] line = lexiconReader.readLine().split("\t");
            String entityId = line[0];
            for (int i = 2; i < line.length; i+=2) {
                String phrase = line[i-1];
                Long count = Long.parseLong(line[i]);
                addCounts(entityId, phrase, count);
                if (!lexicon.containsKey(phrase))
                    lexicon.put(phrase, new ArrayList<String>());
                lexicon.get(phrase).add(entityId);
            }
        }
    }

    /**
     * Adds count to the number of mentions overall, for a phrase and for entity-phrase pair.
     * @param entityId id of mentioned entity.
     * @param phrase mention phrase.
     * @param count mentions count.
     */
    private void addCounts(String entityId, String phrase, Long count) {
        totalCount += count;
        if (!entityCount.containsKey(entityId)) {
            entityCount.put(entityId, 0L);
        }
        entityCount.put(entityId, entityCount.get(entityId) + 1L);
        if (!entityPhraseCount.containsKey(entityId, phrase)) {
            entityPhraseCount.put(entityId, phrase, 0L);
        }
        entityPhraseCount.put(entityId, phrase, entityPhraseCount.get(entityId, phrase) + 1);
    }

    /**
     * Returns confidence score for entity mention.
     * @param entityId mentioned entity.
     * @param phrase mention phrase.
     * @return A score between 0 and 1.
     */
    private double getMentionScore(String entityId, String phrase) {
        if (!entityPhraseCount.containsKey(entityId, phrase))
            // TODO: Use smoothing.
            return 0.0;
        return 1.0 * entityPhraseCount.get(entityId, phrase) / totalCount;
    }

    @Override
    public List<Annotation> buildMentions(Text document) {
        List<Annotation> res = new ArrayList<Annotation>();

        // Go over all sentences.
        for (Sentence sentence : document.getSentences()) {
            int currentSpanStartWord = 0;
            int currentSpanEndWord = 1;
            // Go over all words.
            while (currentSpanStartWord < sentence.getWordsCount()) {
                Span currentSpan = new Span(sentence.getWordSpan(currentSpanStartWord).getStart(),
                        sentence.getWordSpan(currentSpanEndWord - 1).getEnd());
                String phrase = document.getSpanText(currentSpan);
                // If we have a mention.
                if (lexicon.containsKey(phrase)) {
                    for (String entityId : lexicon.get(phrase)) {
                        res.add(new Annotation(document, currentSpan, new EntityInfo(entityId, entityId),
                                getMentionScore(phrase, entityId)));
                    }
                   ++currentSpanEndWord;
                } else if (lexicon.prefixMap(phrase).size() == 0) {
                    // If current phrase is not a prefix of any possible mention.
                    ++currentSpanStartWord;
                    currentSpanEndWord = currentSpanStartWord + 1;
                } else {
                    // If current phrase is a prefix of some mention.
                    ++currentSpanEndWord;
                }
                if (currentSpanEndWord > sentence.getWordsCount()) {
                    ++currentSpanStartWord;
                    currentSpanEndWord = currentSpanStartWord + 1;
                }
            }
        }
        return res;
    }
}
