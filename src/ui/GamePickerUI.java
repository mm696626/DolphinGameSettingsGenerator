package ui;

import io.SearchResultFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePickerUI extends JFrame implements ActionListener {

    private JTextField searchBar;
    private JButton search;
    private ArrayList<String> searchResults;

    private JComboBox searchFilters;

    private final String[] filters = {"No Filter", "US Games Only", "PAL Games Only", "Japanese Games Only"};


    GridBagConstraints gridBagConstraints = null;


    public GamePickerUI() {
        setTitle("Pick a Game");

        searchBar = new JTextField(10);

        search = new JButton("Search");
        search.addActionListener(this);

        searchFilters = new JComboBox(filters);
        searchFilters.setSelectedIndex(0);

        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        add(searchBar, gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        add(search, gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        add(searchFilters, gridBagConstraints);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == search) {
            String gameSearch = searchBar.getText().trim();

            if (gameSearch.length() == 0) {
                JOptionPane.showMessageDialog(this, "Nothing was entered");
            }
            else {
                SearchResultFinder searchResultFinder = new SearchResultFinder();

                String chosenFilter = filters[searchFilters.getSelectedIndex()];
                searchResults = searchResultFinder.getSearchResults(gameSearch, chosenFilter);
                if (searchResults.size() == 0) {
                    JOptionPane.showMessageDialog(this, "No results were found");
                }
                else {
                    setVisible(false);
                    SearchResultsUI searchResultsUI = new SearchResultsUI(searchResults);
                    searchResultsUI.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    searchResultsUI.pack();
                    searchResultsUI.setVisible(true);
                }
            }
        }
    }
}
