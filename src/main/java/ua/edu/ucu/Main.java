package ua.edu.ucu;

import ua.edu.ucu.autocomplete.PrefixMatches;
import ua.edu.ucu.tries.RWayTrie;

public class Main {

    public static void main(String[] args) {
        PrefixMatches p = new PrefixMatches(new RWayTrie());
        p.load("abc abcde abcdef abcdefg abf abfe abff");
        for (String i : p.wordsWithPrefix("ab", 2)) {
            System.out.println(i);
        }
    }

}
