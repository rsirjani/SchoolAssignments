/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atlas.uwomap;

/**
 * Builds the GUI that provides all navigation/information for application.
 * 
 * Buildings are built based on an input "buildingData.txt" {@link makeBuildings} method.
 * List of Layers builds a display of radio buttons associated with each layer {@link makeLAYERList}
 *      - All toggles start on
 *      - toggle on to show POIs belonging to that layer
 *      - toggle off to hide POIs belonging to that layer
 * List of POIs is built depending on the active Building {@link makePOIList}
 *      - Each button will take the view to the location of the POI and display POI data
 *      - Buttons can be filtered and searched through using the text box above the list
 * List of Favorites for the User across all buildings {@link makeFavoriteList}
 *      - Favorite buttons will take the view to wherever the POI is, even if it is on another building.
 * Get weather retrieves the current weather in London Ontario using an API {@link getWeather}
 * 
 * -POI edit mode can be entered through the tools menu. This allows users to create, modify, and remove POIs (not built-in POIs) {@link actionPerformed}
 * 
 * @version 1.0
 * @author Nandhitha Krishnan
 * @author Alysha Aul
 * @author Hirbod Hosseini
 * @author Jacqueline Kahnert
 * @author Ramtin Sirjani
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainGUI extends JFrame implements ActionListener{
    /** Menu button to open help menu*/
    private JMenu helpMenu = new JMenu("Help");
    /** Menu button to open tools menu*/
    private JMenu toolsMenu = new JMenu("Tools");
    /** Button in help menu opens help dialogue*/
    private JMenuItem use = new JMenuItem("How to Use");
    /** Button in tools menu to turn on edit mode*/
    private JMenuItem POI = new JMenuItem("Edit POIs");
    /** Building Objects that represent buildings viewable in application*/
    private Building[] buildings = new Building[10];
    /** String representing the file name in which the User's POIs are stored*/
    private String UserPOIs;
    /** Array used to store buttons that are associated with POIs */
    private POIBox[] POIbuttons = new POIBox[100];
    /** Scroll pane to store POI button list */
    private JScrollPane POIscroll = new JScrollPane();
    /** Panels that contain POIs each building has its own panel*/
    private JPanel[] POIviews = new JPanel[10];
    /** Buttons associated with layers */
    private JRadioButton[] Layerbuttons = new JRadioButton[100];
    /** Scroll pane to view layer buttons */
    private JScrollPane Layerscroll = new JScrollPane();
    /** Scroll pane to view Favorite buttons */
    private JScrollPane Favoritescroll = new JScrollPane();
    /** Buttons for POIs that have been selected as favorites */
    private POIBox[] FavButtons = new POIBox[100];
    /** Index of building being viewed */
    int currentBuilding;
    /** Background Image for GUI */
    private JLabel background = new JLabel();
    /** Image to show EditMode off */
    private JLabel bulbOffLabel = new JLabel();
    /** Image to show EditMode on */
    private JLabel bulbOnLabel = new JLabel();
    /** ComboBox to select viewed building */
    private JComboBox chooseMapBox;
    /** Strings for current temperature and URL to get current temp from */
    private String apiURL, temp;
    /** JFrame for help page popup */
    private JFrame help = new JFrame();
    /** Create a new JTextField to allow user input for search queries.*/
    private JTextField searchField = new JTextField();
 
    
    /** 
     * Constructor
     * @param UserPOIs is the file in which user's persistent data is stored.
     * 
     * This method creates a jFrame that contains all the necessary components for the application to function.
     * @throws java.io.IOException
     */
    public MainGUI(String UserPOIs) throws HeadlessException, IOException {
        this.UserPOIs = UserPOIs;
        POIscroll.setBounds(7,40,210,310);   
        this.getContentPane().add(POIscroll);
        Layerscroll.setBounds(7,355,210,110);
        this.getContentPane().add(Layerscroll);
        Favoritescroll.setBounds(7,470,210,175);
        this.getContentPane().add(Favoritescroll);
        apiURL = "http://api.openweathermap.org/data/2.5/weather?lat=42.9846&lon=-81.2453&appid=a3c1bc888af793570c3b85895664fe31&units=metric";
        temp = getWeather();
        makeBuildings();
        makeLAYERList();
        makePOIList();
        makeFavoriteList();
        
        // Set the position and size of the searchField JTextField using absolute positioning.
        searchField.setBounds(7,10,200,20);
        Border b = BorderFactory.createLineBorder(Color.BLACK, 2);
        searchField.setBorder(b);
        
        //Images
        ImageIcon image = new ImageIcon("W.png"); //Create an ImageIcon
        ImageIcon img = new ImageIcon("Western_01.png"); //Create an ImageIcon
        BufferedImage image1 = ImageIO.read(new File("weather.png"));
        
        //Weather indicator
        // Create a new JLabel and set it to display an ImageIcon with the given image1 as its source.
        JLabel weatherIcon = new JLabel(new ImageIcon(image1));
        // Add the weatherIcon JLabel to the current container.
        this.add(weatherIcon);
        // Create a new JLabel with the text "Weather".
        JLabel weather = new JLabel("Weather");
        // Set the text of the weather JLabel to include the temperature value in degrees Celsius.
        weather.setText(temp +" ºC  ");
        // Set the background color of the weather JLabel to black.
        weather.setBackground(Color.black);
        // Set the position and size of the weather JLabel using absolute positioning.
        weather.setBounds(285,610,300,30);
        // Set the font of the weather JLabel to "Calibri" with a size of 28.
        weather.setFont(new Font("Calibri", Font.PLAIN, 28));
        // Set the position and size of the weatherIcon JLabel using absolute positioning.
        weatherIcon.setBounds(200,575,100,100);
        // Add the weather JLabel to the current container.
        this.getContentPane().add(weather);
        
        //Set Icon
        background.setIcon(img);
        background.add(buildings[currentBuilding]);
 
        
        //Make Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.lightGray);
        menuBar.add(helpMenu);
        menuBar.add(toolsMenu);
        helpMenu.add(use);
        toolsMenu.add(POI);
        use.addActionListener(this);
        POI.addActionListener(this);
        toolsMenu.setMnemonic(KeyEvent.VK_T);
        POI.setMnemonic(KeyEvent.VK_E);
        this.setJMenuBar(menuBar);
        
        /** 
         * Subclass combo-box action listener.
         * Selects building based on current combo box selection
         */
        //ComboBox Choose Map algorithm.
        String[] chooseMap = {"Middlesex", "Health Science", "Alumni Hall"};
        chooseMapBox = new JComboBox(chooseMap);
        chooseMapBox.setBounds(785, 613, 300, 25);
        chooseMapBox.setFont(new Font("Calibri", Font.BOLD, 18));
        chooseMapBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(buildings[currentBuilding].getMaps()[0].getEditMode() == true){
                    background.remove(bulbOnLabel);
                    background.add(bulbOffLabel);
                    background.revalidate();
                    background.repaint();
                    for(int i = 0; i<buildings[currentBuilding].getMaps().length ; i++){
                        if (buildings[currentBuilding].getMaps()[i] != null){
                            buildings[currentBuilding].getMaps()[i].setEditMode(false);
                        }
                    }
                }
                String buildingName = (String)chooseMapBox.getSelectedItem();
                int i =0;
                while(buildings[i] != null){
                    if(buildings[i].getName().equals(buildingName)){
                        int j = 0;
                        while(buildings[currentBuilding].getMaps()[j] != null){
                            buildings[currentBuilding].getMaps()[j].removeHighlights();
                            j++;
                        }
                        background.remove(buildings[currentBuilding]);
                        currentBuilding = i;
                        background.add(buildings[currentBuilding]);
                        makeLAYERList();
                        makePOIList();
                        background.revalidate();
                        background.repaint();  
                    }
                    i++;
                }
            }
        });
        this.getContentPane().add(chooseMapBox);
        
        
        //LOGO in center
        JLabel UWOlogoLabel = new JLabel();
        UWOlogoLabel.setBounds(630, 603, 38,45);
        BufferedImage UWOlogo = ImageIO.read(new File("Stacked_Rev_Full.gif"));
        Image scaledUWOlabel = UWOlogo.getScaledInstance(UWOlogoLabel.getWidth(), UWOlogoLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon UWOlogoIcon = new ImageIcon(scaledUWOlabel);
        UWOlogoLabel.setIcon(UWOlogoIcon);
        this.add(UWOlogoLabel);
        
        //Edit Mode Bulb in center
        bulbOffLabel.setBounds(680, 603, 38,40);
        bulbOnLabel.setBounds(680, 603, 38,40);
        BufferedImage bulbOfflogo = ImageIO.read(new File("bulb.png"));
        BufferedImage bulbOnlogo = ImageIO.read(new File("idea.png"));
        Image scaledBulbOff = bulbOfflogo.getScaledInstance(bulbOffLabel.getWidth(), bulbOffLabel.getHeight(), Image.SCALE_SMOOTH);
        Image scaledBulbOn = bulbOnlogo.getScaledInstance(bulbOnLabel.getWidth(), bulbOnLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon BulbOffIcon = new ImageIcon(scaledBulbOff);
        ImageIcon BulbOnIcon = new ImageIcon(scaledBulbOn);
        bulbOffLabel.setIcon(BulbOffIcon);
        bulbOnLabel.setIcon(BulbOnIcon);
        background.add(bulbOffLabel);
        
        //Set JFrame properties 
        background.add(searchField);
        this.add(background);
        this.setVisible(true); //make frame visible
        this.setTitle("ATLAS Map Viewer: UWO Edition"); //Sets title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(image.getImage());//Change Icon of frame
        this.setResizable(false);
        this.setSize(1120,715);
        this.setLocationRelativeTo(null);
    }
    
     /** 
     * uses "buildingData.txt" to create building with their associated maps.
     * Stores the created buildings in the instance variable Building[] buildings.
     * @throws java.io.IOException
     */
    public void makeBuildings() throws IOException {
        File input = new File("buildingData.txt");
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i=0;
        while((line=br.readLine())!=null){
            String[] lineArray = line.split(",");
            for(int j=0; j<lineArray.length;j++){
                if(j==0){
                    buildings[i] = new Building(lineArray[j]);
                    buildings[i].addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            int i = 0;
                            Building thisBuilding = (Building) e.getSource();
                            while(thisBuilding.getMaps()[i] != null){
                                thisBuilding.getMaps()[i].removeHighlights();
                                i++;
                            }
                        }
                    });
                }
                else{
                    String floorname = lineArray[j].substring(3, lineArray[j].length() - 4);
                    buildings[i].addMap(floorname, new Map(lineArray[j], UserPOIs, this));
                }
            }
            i++;
        }
    }
    
    /** 
     * Creates a list of POIs based on the currently active building.
     * Also makes a search box that can filter through the list of POIs.
     */
    public void makePOIList(){
        POIviews[currentBuilding] = new JPanel();
        POIviews[currentBuilding].setBounds(0, 0, 210, 310);
        POIviews[currentBuilding].setLayout(new GridLayout(0,1));
        Border b = BorderFactory.createLineBorder(Color.BLACK, 5);
        POIviews[currentBuilding].setBorder(b);
        Map[] maps = buildings[currentBuilding].getMaps();
        int j = 0;
        while(maps[j] != null){
            POI[] POIs = buildings[currentBuilding].getMaps()[j].getPOIs();
            for(int i = 0; i < POIs.length; i++){
                if(buildings[currentBuilding].getMaps()[j].getPOIs()[i] != null){
                    POIbuttons[i] = new POIBox(buildings[currentBuilding].getMaps()[j], buildings[currentBuilding].getMaps()[j].getPOIs()[i], buildings[currentBuilding].getMapScroller(j));
                    POIbuttons[i].setText("<html>Name:<br>" + POIs[i].getName() + "<br>Floor:<br>" + POIs[i].getMap().substring(3, POIs[i].getMap().length() - 4) + "</html>");
                    POIbuttons[i].setFont(new Font("calibri", Font.BOLD, 11));
                    POIbuttons[i].setIcon(POIs[i].getIcon());
                    /**
                     * Each POI button will switch view to where the POI is and display its data.
                     */
                    POIbuttons[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            POIBox poiBox = (POIBox)e.getSource();
                            if(!poiBox.getPOI().getHidden()){
                                if(!poiBox.getPOI().getWindow().isShowing()){
                                    int i = 0;
                                    poiBox.getMap().removeHighlights();
                                    int TabofPOI = MainGUI.this.buildings[currentBuilding].indexOfTab(poiBox.getPOI().getMap().substring(3, POIs[i].getMap().length() - 4));
                                    String TabNameofPOI = poiBox.getPOI().getMap().substring(3, POIs[i].getMap().length() - 4);
                                    String thisMapTab = MainGUI.this.buildings[currentBuilding].getTitleAt(MainGUI.this.buildings[currentBuilding].getSelectedIndex());
                                    if(!(TabNameofPOI.equals(thisMapTab))){
                                        MainGUI.this.buildings[currentBuilding].setSelectedIndex(TabofPOI);
                                    }
                                    poiBox.getMapScroll().getViewport().setViewPosition(new Point(poiBox.getPOI().getX() - 600, poiBox.getPOI().getY() - 450));
                                    poiBox.getPOI().add(poiBox.getMap().getHighlight());
                                    poiBox.getMap().getMapLabel().revalidate();
                                    poiBox.getMap().getMapLabel().repaint();
                                    poiBox.getPOI().setWindowLocation(poiBox.getPOI().getDataLocation());
                                    poiBox.getPOI().showWindow();
                                }
                                else{
                                    poiBox.getMap().removeHighlights();
                                }
                            }
                            else{
                                JFrame frame = new JFrame();
                                JOptionPane.showMessageDialog(frame, "Layer is Hidden. Unhide to see POI.");
                            }
                        }
                    });
                    POIviews[currentBuilding].add(POIbuttons[i]);
                    POIviews[currentBuilding].revalidate();
                    POIviews[currentBuilding].repaint();
                }
            }
            j++;
        }
        
        // Add a DocumentListener to the searchField JTextField to listen for changes to its text.
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            // Call the filterPOIs method whenever text is inserted into the searchField.
            public void insertUpdate(DocumentEvent e) {
                filterPOIs();
            }
            // Call the filterPOIs method whenever text is removed from the searchField.
            public void removeUpdate(DocumentEvent e) {
                filterPOIs();
            }
            // Call the filterPOIs method whenever the text of the searchField is changed.
            public void changedUpdate(DocumentEvent e) {
                filterPOIs();
            }
            // Filter the POIs displayed based on the search term entered by the user.
            // Filter the POIs displayed based on the search term entered by the user.
            private void filterPOIs() {
                // Get the text entered by the user as the search term.
                String searchTerm = searchField.getText();
                // Create new ArrayLists to store the POIBoxes that match and do not match the search term.
                ArrayList<POIBox> matchingPOIBoxes = new ArrayList<>();
                ArrayList<POIBox> nonMatchingPOIBoxes = new ArrayList<>();
                // Iterate through the components of the current POIviews panel.
                for (Component component : POIviews[currentBuilding].getComponents()) {
                    // Check if the component is an instance of the POIBox class.
                    if (component instanceof POIBox poiBox) {
                        // Set the visibility of the POIBox based on whether its POI name or description contains the search term (case-insensitive).
                        poiBox.setVisible(poiBox.getPOI().getDescription().toLowerCase().contains(searchTerm.toLowerCase()) || poiBox.getPOI().getName().toLowerCase().contains(searchTerm.toLowerCase()));
                        // Add the POIBox to the appropriate ArrayList based on its visibility.
                        if (poiBox.isVisible()) {
                            matchingPOIBoxes.add(poiBox);
                        } else {
                            nonMatchingPOIBoxes.add(poiBox);
                        }
                    }
                }
                // Remove all components from the POIviews panel.
                POIviews[currentBuilding].removeAll();
                // Add the matching POIBoxes to the POIviews panel.
                for (POIBox poiBox : matchingPOIBoxes) {
                    POIviews[currentBuilding].add(poiBox);
                }
                // Add the non-matching POIBoxes to the POIviews panel.
                for (POIBox poiBox : nonMatchingPOIBoxes) {
                    POIviews[currentBuilding].add(poiBox);
                }
                // Refresh the layout of the POIviews panel.
                POIviews[currentBuilding].revalidate();
                // Repaint the POIviews panel to display the updated visibility of the POIBoxes.
                POIviews[currentBuilding].repaint();
            }
        });
        POIscroll.setViewportView(POIviews[currentBuilding]);
    }


     /** 
     * Creates a list of layers based on the layers that exist in the software.
     */
    public void makeLAYERList(){
    //Create Panels for windows
        JPanel Layerview = new JPanel();
        Layerview.setLayout(new GridLayout(buildings[currentBuilding].getMaps()[0].getLayers().length,1));
        Border blackline = BorderFactory.createLineBorder(Color.BLACK, 5);
        Layerview.setBorder(blackline);
        Layerview.setBounds(0,0,210, 310);
        for(int i = 0; i < buildings[currentBuilding].getMaps()[0].getLayers().length ; i++){
            //layers
            JRadioButton layer = new JRadioButton(buildings[currentBuilding].getMaps()[0].getLayers()[i],true);
            layer.setFocusable(false);
            layer.setFont(new Font("calibri", Font.BOLD, 14));
            layer.setHorizontalTextPosition(SwingConstants.LEFT);
            layer.setBackground(Color.WHITE);
            /**
             * Each radio button will show or hide POIs associated with the layer based on toggle.
             */
            layer.addActionListener(new ActionListener() { 
                        public void actionPerformed(ActionEvent e) { 
                            JRadioButton layerButton = (JRadioButton)e.getSource();
                            if(layerButton.isSelected()){
                                for(int i=0; i < buildings[currentBuilding].getMaps().length; i++){
                                    if(buildings[currentBuilding].getMaps()[i] != null){
                                        buildings[currentBuilding].getMaps()[i].removeHighlights();
                                        for(int j = 0; j<buildings[currentBuilding].getMaps()[i].getPOIs().length; j++){
                                            if(buildings[currentBuilding].getMaps()[i].getPOIs()[j] != null){
                                                if(buildings[currentBuilding].getMaps()[i].getPOIs()[j].getLayer().equals(layerButton.getText() + ".png")){
                                                    buildings[currentBuilding].getMaps()[i].getMapLabel().add(buildings[currentBuilding].getMaps()[i].getPOIs()[j]);
                                                    buildings[currentBuilding].getMaps()[i].getMapLabel().add(buildings[currentBuilding].getMaps()[i].getPOIs()[j].getDataLocation());
                                                    buildings[currentBuilding].getMaps()[i].getPOIs()[j].setHidden(false);
                                                }
                                            }
                                        }
                                        buildings[currentBuilding].getMaps()[i].getMapLabel().revalidate();
                                        buildings[currentBuilding].getMaps()[i].getMapLabel().repaint();
                                    }
                                }
                            }
                            else{
                                for(int i=0; i < buildings[currentBuilding].getMaps().length; i++){
                                    if(buildings[currentBuilding].getMaps()[i] != null){
                                        buildings[currentBuilding].getMaps()[i].removeHighlights();
                                        for(int j = 0; j<buildings[currentBuilding].getMaps()[i].getPOIs().length; j++){
                                            if(buildings[currentBuilding].getMaps()[i].getPOIs()[j] != null){
                                                if(buildings[currentBuilding].getMaps()[i].getPOIs()[j].getLayer().equals(layerButton.getText() + ".png")){
                                                    buildings[currentBuilding].getMaps()[i].getMapLabel().remove(buildings[currentBuilding].getMaps()[i].getPOIs()[j]);
                                                    buildings[currentBuilding].getMaps()[i].getMapLabel().remove(buildings[currentBuilding].getMaps()[i].getPOIs()[j].getDataLocation());
                                                    buildings[currentBuilding].getMaps()[i].getPOIs()[j].setHidden(true);
                                                }
                                            }
                                        }
                                        buildings[currentBuilding].getMaps()[i].getMapLabel().revalidate();
                                        buildings[currentBuilding].getMaps()[i].getMapLabel().repaint();
                                    }
                                } 
                            }
                        } 
                     });
            Layerbuttons[i] = layer;
            Layerview.add(layer);
        }
        Layerscroll.setViewportView(Layerview);
    }
    
    /** 
     * Creates a list of favorites based on the POIs that the current software user's favorites.
     */
    public void makeFavoriteList(){
    //Create Panels for windows
        JPanel Favoriteview = new JPanel();
        int numFavs = 0;
        for(int i = 0; i < buildings.length ; i++){
            if(buildings[i] != null){
                for(int j = 0; j < buildings[i].getMaps().length; j++){
                    if(buildings[i].getMaps()[j] != null){
                        for(int k = 0; k < buildings[i].getMaps()[j].getPOIs().length; k++){
                            if(buildings[i].getMaps()[j].getPOIs()[k] != null){
                                if(buildings[i].getMaps()[j].getPOIs()[k].getFavorite()){
                                    FavButtons[numFavs] = new POIBox(buildings[i].getMaps()[j], buildings[i].getMaps()[j].getPOIs()[k], buildings[i].getMapScroller(j));
                                    FavButtons[numFavs].setText("<html>Name:<br>" + buildings[i].getMaps()[j].getPOIs()[k].getName() + "<br>Building:<br>" + buildings[i].getName() +"<br>Floor:<br>" + buildings[i].getMaps()[j].getPOIs()[k].getMap().substring(3, buildings[i].getMaps()[j].getPOIs()[k].getMap().length() - 4) + "</html>");
                                    FavButtons[numFavs].setFont(new Font("calibri", Font.BOLD, 11));
                                    FavButtons[numFavs].setIcon(buildings[i].getMaps()[j].getPOIs()[k].getFavIcon());
                                    FavButtons[numFavs].setBuilding(buildings[i]);
                                    /** 
                                    * Favorite buttons change view to POI location and display POI info.
                                    */
                                    FavButtons[numFavs].addActionListener(new ActionListener() { 
                                        public void actionPerformed(ActionEvent e) { 
                                            POIBox poiBox = (POIBox)e.getSource();
                                            for(int i = 0; i < buildings[currentBuilding].getMaps().length; i++){
                                                if(buildings[currentBuilding].getMaps()[i] != null){
                                                    buildings[currentBuilding].getMaps()[i].removeHighlights();
                                                }
                                            }
                                            if(poiBox.getPOI().getHidden() == false){    
                                                //Switch To Building
                                                if(buildings[currentBuilding].getMaps()[0].getEditMode() == true){
                                                    background.remove(bulbOnLabel);
                                                    background.add(bulbOffLabel);
                                                    background.revalidate();
                                                    background.repaint();
                                                    for(int l = 0; l<buildings[currentBuilding].getMaps().length ; l++){
                                                        if (buildings[currentBuilding].getMaps()[l] != null){
                                                            buildings[currentBuilding].getMaps()[l].setEditMode(false);
                                                            buildings[currentBuilding].getMaps()[l].removeHighlights();
                                                        }
                                                    }
                                                }
                                                String buildingName = poiBox.getBuilding().getName();
                                                int m =0;
                                                while(buildings[m] != null){
                                                    if(buildings[m].getName().equals(buildingName)){
                                                        background.remove(buildings[currentBuilding]);
                                                        currentBuilding = m;
                                                        background.add(buildings[currentBuilding]);
                                                        for(int p =0; p< chooseMapBox.getItemCount();p++){
                                                            if(chooseMapBox.getItemAt(p).equals(buildingName)){
                                                                chooseMapBox.setSelectedIndex(p);
                                                            }
                                                        }                                                           
                                                        makeLAYERList();
                                                        makePOIList();
                                                        background.revalidate();
                                                        background.repaint();  
                                                    }
                                                    m++;
                                                }
                                                    
                                                //switch to tab and show info
                                                int TabofPOI = buildings[currentBuilding].indexOfTab(poiBox.getPOI().getMap().substring(3, poiBox.getPOI().getMap().length() - 4));
                                                String TabNameofPOI = poiBox.getPOI().getMap().substring(3, poiBox.getPOI().getMap().length() - 4);
                                                String thisMapTab = buildings[currentBuilding].getTitleAt(buildings[currentBuilding].getSelectedIndex());
                                                if(!(TabNameofPOI.equals(thisMapTab))){
                                                    buildings[currentBuilding].setSelectedIndex(TabofPOI);
                                                }                  
                                                poiBox.getMapScroll().getViewport().setViewPosition(new Point(poiBox.getPOI().getX() - 600, poiBox.getPOI().getY() - 450));
                                                poiBox.getPOI().add(poiBox.getMap().getHighlight());
                                                poiBox.getPOI().setWindowLocation(poiBox.getPOI().getDataLocation());
                                                poiBox.getPOI().showWindow();
                                                poiBox.getMap().getMapLabel().revalidate();
                                                poiBox.getMap().getMapLabel().repaint();
                                            }
                                            else{
                                                JFrame frame = new JFrame();
                                                JOptionPane.showMessageDialog(frame, "Layer is Hidden. Unhide to see POI.");
                                            }
                                        } 
                                     });
                                    Favoriteview.add(FavButtons[numFavs]);
                                    numFavs ++;
                                }
                            }
                        }
                    }
                }
            }
        }
        Favoriteview.setLayout(new GridLayout(numFavs,1));
        Border blackline = BorderFactory.createLineBorder(Color.BLACK, 5);
        Favoriteview.setBorder(blackline);
        Favoriteview.setBounds(0,0,210, 310);
        Favoritescroll.setViewportView(Favoriteview);
        this.revalidate();
        this.repaint();
    }
    
    /** 
    * Switch edit mode to True or False.
    */
     @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == POI){
            if(buildings[currentBuilding].getMaps()[currentBuilding].getEditMode() == false){
                background.remove(bulbOffLabel);
                background.add(bulbOnLabel);
                background.revalidate();
                background.repaint();
                for(int i = 0; i<buildings[currentBuilding].getMaps().length ; i++){
                    if (buildings[currentBuilding].getMaps()[i] != null){
                        buildings[currentBuilding].getMaps()[i].setEditMode(true);
                    }
                }
            }
            else{
                background.remove(bulbOnLabel);
                background.add(bulbOffLabel);
                background.revalidate();
                background.repaint();
                for(int i = 0; i<buildings[currentBuilding].getMaps().length ; i++){
                    if (buildings[currentBuilding].getMaps()[i] != null){
                        buildings[currentBuilding].getMaps()[i].setEditMode(false);
                    }
                }
            }
        }
        if(e.getSource() == use)
        { 
            helpPage();
            help.setVisible(true);
        }
    }
    
    /** 
    * Acquire the current weather from the weather API.
    */
    private String getWeather() throws IOException {
    String result = null;
    //WeatherAPI calls
    try{
        // create StringBuilder obj to store API response
        StringBuilder res = new StringBuilder();
        // create URL obj
        URL url = new URL(apiURL);
        // opening a connection to API endpoint and get an input stream to read the response
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        // reading each line of the API response and appending to result
        String line;
        while((line = br.readLine()) != null)
            res.append(line);
        br.close();
        // finding temperature from API response and storing value
        int i = res.indexOf("temp") + 6;
        int j = i + res.substring(i).indexOf(",");
        // parsing temperature from JSON API output
        result = res.substring(i, j);
    }
    catch(IOException e){
        // catching exception
        System.out.println(e.getMessage());
    }
    // returning weather
    return result;
    }
    
    /** 
    * Setup help page JFrame and launch it.
    */
    public void helpPage()
    {
        Color westernPurple = new Color(79, 44, 130);
        Font helpText = new Font("calibri", Font.BOLD, 12);
        JTextArea content = new JTextArea(40, 60);
        JLabel helpWriteUp = new JLabel("");
        JPanel pane = new JPanel();
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setFont(helpText);
        content.setForeground(new Color(255,255,255));
        content.setBackground(westernPurple);
        pane.setBackground(westernPurple);
        
        try
        {
            File f = new File("helpPage.txt");
            BufferedReader scan = new BufferedReader(new FileReader(f));
            String line;
            
            while((line = scan.readLine()) != null)
            {
                content.append(line + "\n");
            }
            
            pane.add(helpWriteUp);
            pane.add(content);
            help.add(pane);
            help.pack();
            //help.setBackground(new Color(79, 44, 130));
            help.setTitle("ATLAS Map Viewer Help: UWO Edition");
            help.setVisible(true);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
