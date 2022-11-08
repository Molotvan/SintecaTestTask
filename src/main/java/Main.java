import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static List<String> read(String fileName) {
        List<String> str = new ArrayList<>();
        try {
            str = (Files.lines(Paths.get(fileName)).collect(Collectors.toList()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
    }

    public static List<List<String>> splitArray(List<String> input) {
        List<String> keys = input.subList(1, Integer.parseInt(input.get(0)) + 1);
        List<String> values = input.subList(Integer.parseInt(input.get(0)) + 2, input.size());
        List<List<String>> kv = new ArrayList<>();
        kv.add(keys);
        kv.add(values);
        return kv;
    }

    public static String textBuilder(List<SimilarWord> list) {
        StringBuilder builder = new StringBuilder();
        for (SimilarWord word : list) {
            builder.append(word.toString())
                    .append("\n");
        }
        return builder.toString();
    }

    public static List<SimilarWord> listMapping(List<List<String>> kv) {
        List<String> keys = kv.get(0);
        List<String> values = kv.get(1);
        List<SimilarWord> resultList = new ArrayList<>();
        for (String key : keys) {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                indexes.add(wordCompare(key, values.get(i)));
            }
            resultList.add(new SimilarWord(key, values.get(indexes.indexOf(Collections.max(indexes)))));
        }
        for (int i = 0; i < resultList.size(); i++) {
            for (int j = i + 1; j < resultList.size(); j++) {
                if (resultList.get(i).equals(resultList.get(j))) {
                    if (resultList.get(i).getSimilarityIndex() > resultList.get(j).getSimilarityIndex()) {
                        resultList.get(j).setValue("?");
                    } else resultList.get(i).setValue("?");
                }
            }

        }
        return resultList;
    }

    public static int wordCompare(String key, String value) {
        int index = 0;
        char[] keyChars = key.toLowerCase().toCharArray();
        char[] valueChars = value.toLowerCase().toCharArray();
        for (int i = 0; i < keyChars.length; i++) {
            if (keyChars[i] == ' ') continue;
            for (int j = 0; j < valueChars.length; j++) {
                if (valueChars[j] == ' ') continue;
                if (keyChars[i] == valueChars[j]) {
                    for (int k = 0; k < Integer.min(valueChars.length, keyChars.length); k++) {
                        if (i < keyChars.length - k && j < valueChars.length - k && keyChars[i + k] == valueChars[j + k]) {
                            index++;
                        } else break;
                    }
                }

            }
        }

        return index;
    }

    public static void write(String outputFileName, String inputFilename) {
        try {
            FileWriter writer = new FileWriter(outputFileName);
            writer.write(textBuilder(listMapping(splitArray(read(inputFilename)))));
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        System.out.println(read("input.txt"));
        System.out.println(listMapping(splitArray(read("input.txt"))));
        write("output.txt", "input.txt");


    }
}
