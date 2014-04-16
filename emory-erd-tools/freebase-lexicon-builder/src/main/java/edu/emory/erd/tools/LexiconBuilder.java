package edu.emory.erd.tools;

import java.io.IOException;
import java.lang.Integer;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.JobConf;

/**
 * Builds freebase entities lexicon from CLueWeb documents annotations.
 */
public class LexiconBuilder {

    public static class LexiconBuilderMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
            String[] fields = value.toString().split("\t");
            // Output Freebase mid and phrase
            output.collect(new Text(fields[7]), new Text(fields[2]));
        }
    }

    public static class LexiconBuilderReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
            int sum = 0;
            java.util.Map<String, Integer> count = new HashMap<String, Integer>();
            while (values.hasNext()) {
                String phrase = values.next().toString();
                if (count.containsKey(phrase))
                    count.put(phrase, count.get(phrase) + 1);
                else
                    count.put(phrase, 1);
            }
            StringBuilder phrases = new StringBuilder();
            for (java.util.Map.Entry<String, Integer> entry : count.entrySet()) {
                phrases.append(entry.getKey() + "\t" + entry.getValue() + "\t");
            }
            output.collect(new Text(key), new Text(phrases.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(LexiconBuilder.class);
        conf.setJobName("freebase-lexicon-builder");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(LexiconBuilderMapper.class);
        conf.setCombinerClass(LexiconBuilderReducer.class);
        conf.setReducerClass(LexiconBuilderReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0])); // args[0] = "datasets/test/"
        FileOutputFormat.setOutputPath(conf, new Path(args[1])); // args[1] = "datasets/output/"

        JobClient.runJob(conf);
    }
}
