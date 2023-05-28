package Commandline;

import Management.Dictionary;
import Management.DictionaryManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement new_management = new DictionaryManagement();
    public void showAllWords() {
        Dictionary list = new_management.myList;
        System.out.printf("%-5s |%-20s |%s%n", "No", "English", "Vietnamese");
        for (int i = 0; i < list.word_list.size(); i++) {
            System.out.printf("%-5d |%-20s |%s%n", i + 1, list.word_list.get(i).getWord_target(), list.word_list.get(i).getWord_explain());
        }
    }

    public void dictionaryAdvanced() throws FileNotFoundException, IOException {

        new_management.insertFromFile();

        System.out.println("Dictionary ");
        System.out.println("");
        System.out.println("Read the following instruction");
        System.out.println();
        System.out.println("*Welcome to our Dictionary application*");

        boolean Quit = false;

        while (!Quit) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("**** Make your choice ****");
            System.out.println("1. Q or q to quit the Dictionary");
            System.out.println("2. A or a to add some words you want");
            System.out.println("3. S or s to show all of words");
            System.out.println("4. T or t to search a word");
            System.out.println("5. C or c to search words by the starting characters");
            System.out.println("6. E or e to edit a word");
            System.out.println("7. D or d to delete a word");
            System.out.println();
            String choice = scanner.next();
            choice = choice.toUpperCase();

            switch (choice) {
                case "Q":
                    Quit = true;
                    break;
                case "A":
                    System.out.println("You chose to add word!");
                    new_management.insertFromCommandline();
                    break;
                case "S":
                    showAllWords();
                    break;
                case "T":
                    new_management.dictionaryLookup();
                    break;
                case "C":
                    new_management.dictionarySearch();
                    break;
                case "E":
                    new_management.editWord();
                    break;
                case "D":
                    new_management.removeWord();
                    break;
                default:
                    System.out.println("Wrong choice, you must to chose again! \n");
            }
        }
        if(new_management.change){
            new_management.dictionaryExportToFile();
        }
    }
    

}
