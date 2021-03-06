import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
  class Huffman {
    String archive;
    String tree;
    HashMap<Character, String> binaryValues = new HashMap<Character, String>();

    public Huffman(String inputString) {
      this.archive = inputString;
      
      // Generate the lists of alphabet and frequency counter.
      ArrayList<Character> alphabet = new ArrayList<Character>();
      ArrayList<Integer> frequency = new ArrayList<Integer>();
      for (char c : inputString.toCharArray()) {
        if (alphabet.indexOf(c) == -1) {
          alphabet.add(c);
          frequency.add(1);
        } else {
          int index = alphabet.indexOf(c); 
          frequency.set(index, frequency.get(index) + 1);
        }
      }

      // Sort the lists of alphabet and frequency counter.
      ArrayList<Character> sortedAlphabet = new ArrayList<Character>();
      ArrayList<Integer> sortedFrequency = new ArrayList<Integer>();
      for (int i = 1; i <= Collections.max(frequency); i++) {
        for (int j = 0; j < alphabet.size(); j++) {
          if (frequency.get(j) == i) {
            sortedAlphabet.add(alphabet.get(j));
            sortedFrequency.add(frequency.get(j));
          }
        }
      }

      // Generate the prefix expression of the Huffman coding tree.
      this.genBinaryTree(sortedAlphabet, sortedFrequency);

      // Generate the binary values of characters by using the pushdown automaton.
      this.genBinaryValues(sortedAlphabet, sortedFrequency);
    }

    // Generate a binary tree which in the postfix expression.
    private void genBinaryTree(ArrayList<Character> sortedAlphabet, ArrayList<Integer> sortedFrequency) {
      ArrayList<String> stack = new ArrayList<String>();
      ArrayList<Integer> weights = new ArrayList<Integer>();
      for (int i = 0; i < sortedAlphabet.size(); i++) {
        stack.add(Character.toString(sortedAlphabet.get(i)));
        weights.add(sortedFrequency.get(i));
      }

      while (weights.size() > 1) {
        String branchStack;
        if (stack.get(0).length() == 1) {
          branchStack = "1" + stack.get(0);
        } else {
          branchStack = stack.get(0);
        }
        if (stack.get(1).length() == 1) {
          branchStack += "1" + stack.get(1) + "0";
        } else {
          branchStack += stack.get(1) + "0";
        }

        Integer branchWeight = weights.get(0) + weights.get(1);
        stack.remove(0);
        stack.remove(0);
        weights.remove(0);
        weights.remove(0);

        Integer size = weights.size();
        for (int index = 1; index < size; index++) {
          if ((weights.get(index - 1) <= branchWeight) & (branchWeight < weights.get(index))) {
            stack.add(index, branchStack);
            weights.add(index, branchWeight);
            break;
          }
        }
        if (weights.size() == size) {
          stack.add(size, branchStack);
          weights.add(size, branchWeight);
        }
      }
      this.tree = stack.get(0);
    }

    // Generate the binary values of characters by using the pushdown automaton.
    private void genBinaryValues(ArrayList<Character> sortedAlphabet, ArrayList<Integer> sortedFrequency) {
      for (char c : sortedAlphabet) {
        this.binaryValues.put(c, "");
      }
      ArrayList<String> stack = new ArrayList<String>();
      ArrayList<String> inputTape = new ArrayList<String>();
      for (char c : this.tree.toCharArray()) {
        inputTape.add(Character.toString(c));
      }
      ArrayList<String> outputTape = new ArrayList<String>();

      while (inputTape.size() != 2) {
        outputTape.clear();
        for (int i = 0; i < inputTape.size(); i++) {
          if (inputTape.get(i).equals("0")) {
            
            if (stack.size() >= 4) {
              if (stack.get(stack.size() - 4).equals("1") & stack.get(stack.size() - 2).equals("1")) {
                for (char c : stack.get(stack.size() - 3).toCharArray()) {
                  this.binaryValues.put(c, "0" + this.binaryValues.get(c));
                }
                for (char c : stack.get(stack.size() - 1).toCharArray()) {
                  this.binaryValues.put(c, "1" + this.binaryValues.get(c));
                }
                stack.set(stack.size() - 3, stack.get(stack.size() - 3) + stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                outputTape.addAll(stack);
              } else {
                outputTape.addAll(stack);
                outputTape.add("0");
              }
            } else {
              outputTape.addAll(stack);
              outputTape.add("0");
            }
            stack.clear();
          } else {
            stack.add(inputTape.get(i));
          }
        }
        inputTape = new ArrayList<>(outputTape);
      }
    }

    public String encoder(String text) {
      String code = "";
      for (char c : text.toCharArray()) {
        code += this.binaryValues.get(c);
      }
      return code;
    }

    public String decoder(String code) {
      Map<String, Character> inverseMap = new HashMap<>();
      ArrayList<String> keys = new ArrayList<String>();
      for(Map.Entry<Character, String> entry : this.binaryValues.entrySet()){
        inverseMap.put(entry.getValue(), entry.getKey());
        keys.add(entry.getValue());
      }
      
      String txt = "";
      String key = "";

      for (char c : code.toCharArray()) {
        key += Character.toString(c);
        if (keys.contains(key)) {
          txt += Character.toString(inverseMap.get(key));
          key = "";
        }
      }
      return txt;
    }
  }

  public static void main(String[] args) {
    /* ----------------- This is a demo -------------- */
    
    // String text = "go go gophers";

    // // Initial the encoder and decoder.
    // Main demo = new Main();
    // Main.Huffman obj = demo.new Huffman(text);
    // System.out.println("-------- Initial the encoder and decoder --------");
    // System.out.println("Original: \n" + obj.archive);
    // System.out.println("The Huffman coding tree in a postfix expression:");
    // System.out.println(obj.tree);
    // System.out.println("The binary values:");
    // System.out.println(obj.binaryValues);

    // // convert a new string to binary code.
    // System.out.println("-------- Encoding/Decoding a new example --------");
    // String newInput = "pho reo res gr";
    // String output = obj.encoder(newInput);
    // System.out.println("Input: " + newInput);
    // System.out.println("Encoding: " + output);
    // System.out.println("Decoding: " + obj.decoder(output));

      System.out.println("-------- Initial the Huffman coding tree --------");
      System.out.println("Please input a string to generate tree(ex: go go gophers)");
      Main demo = new Main();
      Scanner scanner = new Scanner(System.in);
      Main.Huffman obj = demo.new Huffman(scanner.nextLine());
      System.out.println("The Huffman coding tree in a postfix expression is");
      System.out.println(obj.tree);
      System.out.println("The hash map of binary values is");
      System.out.println(obj.binaryValues);

      System.out.println("\n-------- Convert a string to binary code --------");
      System.out.println("Please input a new string for encoding(ex: pho reo res gr)");
      System.out.println("Encoding: " + obj.encoder(scanner.nextLine()));

      System.out.println("\n-------- Convert a binary code to string --------");
      System.out.println("Plsease input a binary code(ex: 1100110101101111111100110111111110100101001111)");
      System.out.println("Decoding: " + obj.decoder(scanner.nextLine()));

    scanner.close();
  }
}
