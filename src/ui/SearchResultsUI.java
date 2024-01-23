package ui;

import io.GameIDSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchResultsUI extends JFrame implements ActionListener {

    private JButton[] searchResultsButtons;
    private ArrayList<String> searchResults;

    private Container container;

    GridBagConstraints gridBagConstraints = null;


    public SearchResultsUI(ArrayList<String> searchResults) {

        this.searchResults = searchResults;
        searchResultsButtons = new JButton[searchResults.size()];
        setTitle("Search Results");
        this.container = getContentPane();
        container.setLayout(new BorderLayout());
        generateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i=0; i<searchResultsButtons.length; i++) {
            if (e.getSource() == searchResultsButtons[i]) {

                String chosenButtonText = searchResultsButtons[i].getText().trim();
                chosenButtonText = chosenButtonText.split(" ")[0];
                GameIDSaver gameIDSaver = new GameIDSaver();
                gameIDSaver.saveGameIDChoiceToGameIDChoiceFile(chosenButtonText);
                setVisible(false);
            }
        }
    }

    private void generateUI() {

        JPanel jPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(searchResults.size(), 1);
        jPanel.setLayout(gridLayout);


        for (int i=0; i<searchResults.size(); i++) {

            searchResultsButtons[i] = new JButton(searchResults.get(i));
            searchResultsButtons[i].addActionListener(this);

            jPanel.add(searchResultsButtons[i]);
        }

        JScrollPane jScrollPane = new JScrollPane(jPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(jScrollPane);
    }

    private String getSearchResultsText(ArrayList<String> searchResults) {
        String searchResultsText = "";

        for (int i=0; i<searchResults.size(); i++) {
            searchResultsText += searchResults.get(i) + "\n";
        }

        return searchResultsText;
    }
}
