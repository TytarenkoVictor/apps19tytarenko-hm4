package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.StreamSupport.stream;

public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        return (int) Stream.of(strings)
                .map(str -> str.split(" "))
                .flatMap(Stream::of)
                .distinct()
                .peek(word -> trie.add(new Tuple(word, word.length())))
                .count();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
         List<String> wordsSortedByLength = StreamSupport
                .stream(wordsWithPrefix(pref).spliterator(), false)
                .sorted(Comparator.comparingInt(String::length).reversed())
                 .collect(Collectors.toList());

         List<String> resultWords = new ArrayList<>();
         for (int i = wordsSortedByLength.size()-1, countLengths = k; countLengths > 0 && i > 0; i--) {
             String currentWord = wordsSortedByLength.get(i);
             resultWords.add(currentWord);

             if (currentWord.length() < wordsSortedByLength.get(i-1).length()) {
                 countLengths--;
             }
         }

         return resultWords;
    }

    public int size() {
        return trie.size();
    }
}
