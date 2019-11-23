package ua.edu.ucu;

import ua.edu.ucu.autocomplete.PrefixMatches;
import ua.edu.ucu.tries.RWayTrie;

public class Main {

    public static void main(String[] args) {

        PrefixMatches prefixMatches = new PrefixMatches(new RWayTrie());
        prefixMatches.load("abcd", "abc", "abce");
        System.out.println(prefixMatches.size());
        System.out.println(prefixMatches.contains("abc"));
        prefixMatches.wordsWithPrefix("ab").forEach(System.out::println);

    }

}
