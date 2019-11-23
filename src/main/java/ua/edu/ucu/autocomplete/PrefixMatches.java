package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
        return StreamSupport
                .stream(wordsWithPrefix(pref).spliterator(), false)
                .filter(word -> word.length() < pref.length() + k)
                .collect(Collectors.toList());
    }

    public int size() {
        return trie.size();
    }
}
