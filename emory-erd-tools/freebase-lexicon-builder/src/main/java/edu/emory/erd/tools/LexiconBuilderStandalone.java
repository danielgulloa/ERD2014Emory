package edu.emory.erd.tools;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by dsavenk on 4/15/14.
 */
public class LexiconBuilderStandalone {

    static Map<String, HashMap<String, Integer>> midPhrases = new HashMap<String, HashMap<String, Integer>>();

    public static void processLine(String line) {
        String[] fields = line.split("\t");
        String mid = fields[7];
        String phrase = fields[2];
        if (!midPhrases.containsKey(mid)) {
            midPhrases.put(mid, new HashMap<String, Integer>());
        }

        if (!midPhrases.get(mid).containsKey(phrase)) {
            midPhrases.get(mid).put(phrase, 0);
        }
        midPhrases.get(mid).put(phrase, midPhrases.get(mid).get(phrase) + 1);
    }

    public static void outputLexicon() {
        for (Map.Entry<String, HashMap<String, Integer>> e : midPhrases.entrySet()) {
            System.out.print(e.getKey() + "\t");
            for (Map.Entry<String, Integer> e1 : e.getValue().entrySet()) {
                System.out.print(e1.getKey() + "\t" + e1.getValue() + "\t");
            }
            System.out.println();
        }
    }

    public static void processTgzFile(String tgzFileName) throws IOException {
        TarArchiveInputStream tarInput =
                new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(tgzFileName)));

        TarArchiveEntry currentEntry;
        BufferedReader tgzReader = new BufferedReader(new InputStreamReader(tarInput));
        while((currentEntry = tarInput.getNextTarEntry()) != null) {
            if (currentEntry.isFile()) {
                while (tgzReader.ready()) {
                    processLine(tgzReader.readLine());
                }
            }
        }

        tarInput.close();
    }

    public static void main(String[] args) {
        try {
            for (String inputFile : args) {
                processTgzFile(inputFile);
            }
        } catch (IOException exc) {
            System.err.println(exc.getMessage());
        }
        outputLexicon();
    }
}
