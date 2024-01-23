package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchResultFinder {

    public ArrayList<String> getSearchResults(String searchTerm, String searchFilter) {
        ArrayList<String> searchResults = new ArrayList<>();
        Scanner inputStream = null;

        boolean useFilter = true;
        char filter = '0'; //only initialized for compile error

        if (searchFilter.equals("US Games Only")) {
            filter = 'E';
        }
        else if (searchFilter.equals("PAL Games Only")) {
            filter = 'P';
        }
        else if (searchFilter.equals("Japanese Games Only")) {
            filter = 'J';
        }
        else {
            useFilter = false;
        }

        try {
            inputStream = new Scanner(new FileInputStream("wiitdb.txt"));
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        while (inputStream.hasNextLine()) {

            //get rid of accented characters, so searches like "Pokemon" work
            String line = inputStream.nextLine();

            String editedLine = line;
            editedLine = Normalizer.normalize(editedLine, Normalizer.Form.NFD);
            editedLine = editedLine.replaceAll("[^\\p{ASCII}]", "");


            editedLine = editedLine.replaceAll(" ", "");
            editedLine = editedLine.replaceAll("&", "and");
            editedLine = editedLine.replaceAll("[^a-zA-Z0-9]", "");


            searchTerm = searchTerm.replaceAll(" ", "");
            searchTerm = searchTerm.replaceAll("&", "and");
            searchTerm = searchTerm.replaceAll("[^a-zA-Z0-9]", "");



            if (editedLine.toLowerCase().contains(searchTerm.toLowerCase())) {
                if (!useFilter) {
                    searchResults.add(line);
                }
                else {
                    String gameID = line.split(" ")[0];
                    if (gameID.charAt(3) == filter) {
                        searchResults.add(line);
                    }
                }
            }
        }

        inputStream.close();
        return searchResults;
    }
}