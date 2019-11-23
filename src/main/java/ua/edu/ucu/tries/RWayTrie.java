package ua.edu.ucu.tries;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RWayTrie implements Trie {

    private int e, z;

    private class RWayTrieIterator implements Iterator<String>{
        Queue<String> prefixes = new LinkedList<>();
        Queue<Node> nodes = new LinkedList<>();

        Node currentNode;
        String currentPrefix;
        boolean flag = false;

        public RWayTrieIterator(String prefix){
            this.currentNode = get(root, prefix, 0);
            this.currentPrefix = prefix;
            nodes.add(currentNode);
            prefixes.add(currentPrefix);
        }

        @Override
        public boolean hasNext() {
            if (!nodes.isEmpty()) return true;
            else{
                for (int i = 0; i < ALPHABET; i++) {
                    if (currentNode.next[i] != null) {
                        return true;
                    }
                }
                return false;
            }
        }

        @Override
        public String next() {
            while (true) {
                if (!flag) {
                    currentNode = nodes.poll();
                    currentPrefix = prefixes.poll();
                    if (currentNode.length != 0) {
                        flag = true;
                        return currentPrefix;
                    }
                }
                else{
                    flag = false;
                }
                for (int i = 0; i < ALPHABET; i++) {
                    if (currentNode.next[i] != null) {
                        nodes.add(currentNode.next[i]);
                        char c = (char) (i + 'a');
                        prefixes.add(currentPrefix + c);
                    }
                }
            }
        }
    }

    private static class Node{

        private int length;
        private Node[] next = new Node[ALPHABET];
    }

    private static final int ALPHABET = 26;
    private Node root = new Node();
    private int size;

    public void add(Tuple tuple) {
        root = add(root, tuple.term, tuple.weight, 0);
        size++;
    }

    private Node add(Node node, String word, int length, int d){
        if (node == null) node = new Node();
        if (d == word.length()){
            node.length = length;
            return node;
        }

        char c = word.charAt(d);
        node.next[c - 'a'] = add(node.next[c - 'a'], word, length, d + 1);
        return node;
    }

    public boolean delete(String word) {
        root = delete(root, word, 0);
        if (root == null) {
            return false;
        }
        return true;
    }

    private Node delete(Node node, String word, int d) {
        if (node == null) return null;
        if (d == word.length()) {
            if (node.length != 0) size--;
            node.length = 0;
        }
        else {
            char c = word.charAt(d);
            node.next[c - 'a'] = delete(node.next[c - 'a'], word, d+1);
        }

        if (node.length != 0) return node;
        for (int i = 0; i < ALPHABET; i++)
            if (node.next[i] != null)
                return node;
        return null;
    }

    public boolean contains(String word) {
        return get(word) != 0;
    }

    private int get(String word){
        Node node = get(root, word, 0);
        if (node == null) return 0;
        return node.length;
    }

    private Node get(Node node, String word, int d){
        if (node == null) return null;
        if (d == word.length()) return node;
        char c = word.charAt(d);
        return get(node.next[c - 'a'], word, d + 1);
    }

    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    public Iterable<String> wordsWithPrefix(String prefix){
        return () -> new RWayTrieIterator(prefix);
    }

    public int size() {
        return size;
    }
}

