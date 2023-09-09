package com.atlas.uwomap;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Builds a scrollable and interactive map that has a POI list associated with it.
 * 
 * POIs are stored in a list from BuiltinPOIs.txt and the user's personal created POIs.
 * POIs are added to the map{@link addPOIs}
 * User POIs can be deleted and edited. New POIs can be created {@link mouseClicked}{@link setEditMode}
 * POIs are interactive. Highlighted and data is shown once clicked. Clicking away deselects.
 * 
 * @version 1.0
 * @author Nandhitha Krishnan
 * @author Alysha Aul
 * @author Hirbod Hosseini
 * @author Jacqueline Kahnert
 * @author Ramtin Sirjani
 */
public class Map extends JLabel implements MouseListener{
    /** The Image of the map*/
    private JLabel MapLabel = new JLabel();
    /** POIs that can be added to the map*/
    private POI[] POIs = new POI[100];
    /** Layers that POIs can belong to*/
    private final String[] Layers = {"Washroom", "Accessibility", "Classroom", "Eatery", "Navigation", "Study Space", "Lab", "Other"};
    /** Status of edit mode*/
    private Boolean editMode = false;
    /** Count of POIs currently on the map*/
    private int POIcount = 0;
    /** Highlight that can be added to POIs*/
    private JLabel highlight = new JLabel();
    /** Filename for user made POIs*/
    private String UserPOIs = "";
    /** Filename for user's favorite POIs*/
    private String UserFavs = "";
    /** Name of the map*/
    private String mapName = "";
    /** Used for calling methods from MainGUI when remaking maps*/
    private final MainGUI main;
    /** JFrame used to create/edit POIs*/
    private JFrame[] poiInputs = new JFrame[1];
    /** # of POIs being edited or created currently*/
    private int JFrameCount = 0;

    /** 
     * Constructor
     * @param mapName
     * @param UserPOIs
     * @param main
     * - Sets instance variables.
     * 
     * This method creates a JLabel associated with a map for a floor of a UWO building.
     * @throws java.io.IOException
     */
    public Map(String mapName, String UserPOIs, MainGUI main) throws FileNotFoundException, IOException {
        this.main = main;
        this.mapName = mapName;
        this.UserPOIs = UserPOIs;
        if(UserPOIs.equals("BuiltinPOIs.txt")){
            UserFavs = "DEVFavorites.txt"; 
        }
        else{
            UserFavs = UserPOIs.substring(0, UserPOIs.length() - 8) + "Favorites.txt";
        }
        ImageIcon map = new ImageIcon(mapName);
        highlight.setSize(50,50);
        highlight.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        MapLabel.setIcon(map);
        
        //Adds built in POI data.
        if(!UserPOIs.equals("BuiltinPOIs.txt")){
            File input = new File("BuiltinPOIs.txt");
            FileReader fr = new FileReader(input);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int i = 0;
            while((line=br.readLine())!=null){
                if(line.contains(mapName)){
                    String[] lineArray = line.split("\037");
                    while(POIs[i] != null){
                        i++;
                    }
                    POIs[i] = new POI(lineArray[0], lineArray[1], lineArray[2], lineArray[3], Integer.parseInt(lineArray[4]), Integer.parseInt(lineArray[5]),Boolean.parseBoolean(lineArray[6]), main, UserPOIs);
                    POIs[i].setIndex(i);
                    POIs[i].setIsBuiltIn(true);
                    POIcount++;
                    POIs[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent evt) {
                            POI thisPOI = (POI)evt.getSource();
                            if(!thisPOI.getWindow().isShowing()){
                                removeHighlights();
                                thisPOI.add(highlight);
                                thisPOI.setWindowLocation(thisPOI.getDataLocation());
                                thisPOI.showWindow();
                                thisPOI.revalidate();
                                thisPOI.repaint();
                            }
                            else{
                                removeHighlights();
                            }
                        }
                    });
                }
            }    
        }
        //Adds User POI data.
        File input = new File(UserPOIs);
        FileReader fr = new FileReader(input);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i = 0;
        while((line=br.readLine())!=null){
            if(line.contains(mapName)){
                String[] lineArray = line.split("\037");
                while(POIs[i] != null){
                    i++;
                }
                POIs[i] = new POI(lineArray[0], lineArray[1], lineArray[2], lineArray[3], Integer.parseInt(lineArray[4]), Integer.parseInt(lineArray[5]),Boolean.parseBoolean(lineArray[6]), main, UserPOIs);
                POIs[i].setIndex(i);
                POIcount++;
                POIs[i].addMouseListener(new MouseAdapter() {
                @Override
                /**
                 * POI actions when clicked.
                 */
                public void mouseClicked(MouseEvent evt) {
                    POI thisPOI = (POI)evt.getSource();
                    if(SwingUtilities.isRightMouseButton(evt)){
                        if (editMode == true){                        
                            try {
                                removeHighlights();
                                MapLabel.remove(thisPOI);
                                MapLabel.remove(thisPOI.getDataLocation());
                                POIcount --;
                                
                                File inputFile = new File(UserPOIs);
                                File tempFile = new File("tmp");
                                tempFile.createNewFile();
                                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                                String line = reader.readLine();
                                while (line != null) {
                                    String line_array[] = line.split("\037");
                                    if(thisPOI.getX() == Integer.parseInt(line_array[4])){
                                    }
                                    else{
                                        Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                    }
                                    line = reader.readLine();
                                }
                                reader.close(); 
                                Path source = Paths.get("tmp");
                                Files.move(source, source.resolveSibling(UserPOIs), StandardCopyOption.REPLACE_EXISTING);
                                //tempFile.renameTo(inputFile);
                                

                                File favFile = new File(UserFavs);
                                File favtempFile = new File("tmp");
                                tempFile.createNewFile();
                                reader = new BufferedReader(new FileReader(favFile));
                                String line1 = reader.readLine();
                                while (line1 != null) {
                                    String line_array[] = line1.split(",");
                                    if(mapName.equals(line_array[0]) && thisPOI.getX() + 25 == Integer.parseInt(line_array[1])){
                                    }
                                    else{
                                        Files.write(Paths.get("tmp"), (line1 + "\n").getBytes(), StandardOpenOption.APPEND);
                                    }
                                    line1 = reader.readLine();
                                } reader.close();
                                Path source1 = Paths.get("tmp");
                                Files.move(source1, source.resolveSibling(UserFavs), StandardCopyOption.REPLACE_EXISTING);
                                
                                MapLabel.revalidate();
                                MapLabel.repaint();
                                POIs[thisPOI.getIndex()] = null;
                                main.makePOIList();
                                main.makeFavoriteList();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (UnsupportedEncodingException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    else{
                        if(editMode == false){
                            if(!thisPOI.getWindow().isShowing()){
                                removeHighlights();
                                thisPOI.add(highlight);
                                thisPOI.setWindowLocation(thisPOI.getDataLocation());
                                thisPOI.showWindow();
                                thisPOI.revalidate();
                                thisPOI.repaint();
                            }
                            else{
                                removeHighlights();
                            }
                        }
                        else{
                            removeHighlights();
                            for(int i = 0; i < JFrameCount; i++){
                                if(poiInputs[i] != null){
                                    poiInputs[i].dispose();
                                    JFrameCount --;
                                }
                            }
                            poiInputs[JFrameCount] = new JFrame();
                            JFrameCount++;
                            poiInputs[JFrameCount - 1].setLocationRelativeTo(null);
                            poiInputs[JFrameCount - 1].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            JPanel POIMakePanel = new JPanel();
                            POIMakePanel.setLayout(null);
                            POIMakePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                            JComboBox chooseLayer = new JComboBox(Layers);
                            JTextField nameinput = new JTextField();
                            JTextArea descriptionInput = new JTextArea();
                            JScrollPane descriptionview = new JScrollPane(descriptionInput);
                            descriptionview.setBounds(90,100,180,120);
                            descriptionInput.setLineWrap(true);
                            descriptionInput.setWrapStyleWord(true);
                            JLabel nameLabel = new JLabel("Name: ");
                            JLabel layerLabel = new JLabel("Layer: ");
                            JLabel descriptionLabel = new JLabel("Description: ");
                            nameLabel.setBounds(50,40,130,20);
                            nameinput.setBounds(90,40,130,20);
                            layerLabel.setBounds(50,70,130,20);
                            chooseLayer.setBounds(90,70,130,20);
                            descriptionLabel.setBounds(10,100,130,20);
                            descriptionInput.setBounds(90,500,180,120);
                            JButton make = new JButton("Make");
                            make.setBounds(110,230,80,25);
                            POIMakePanel.add(nameinput);
                            POIMakePanel.add(chooseLayer);
                            POIMakePanel.add(descriptionview);
                            POIMakePanel.add(make);
                            POIMakePanel.add(nameLabel);
                            POIMakePanel.add(layerLabel);
                            POIMakePanel.add(descriptionLabel);
                            poiInputs[JFrameCount - 1].add(POIMakePanel);
                            poiInputs[JFrameCount - 1].setVisible(true);
                            poiInputs[JFrameCount - 1].setSize(300,300);
                            /**
                            * Editing saved POI.
                            */
                            make.addActionListener(new ActionListener() { 
                                public void actionPerformed(ActionEvent e) {
                                    BufferedReader reader = null;
                                    try {
                                        String description = descriptionInput.getText();
                                        description = description.replaceAll("\n", "<br/>");
                                        String[] descriptionWords = description.split(" ");
                                        int lines = (descriptionWords.length/5) + 1;
                                        String[] descriptionWordsReformatted = new String[lines];
                                        for(int k=0;k<lines;k++){
                                            if(k+1 == lines){
                                                String last_line = "";
                                                for(int l = k*5;l<descriptionWords.length;l++){
                                                    last_line = last_line + " " + descriptionWords[l];
                                                }
                                                descriptionWordsReformatted[k] = last_line;
                                            }
                                            else{
                                                String this_line = "";
                                                for(int l = k*5; l<(k+1)*5; l++){
                                                    this_line = this_line + " " + descriptionWords[l];
                                                }
                                                descriptionWordsReformatted[k] = this_line;
                                            }
                                        }   description = String.join("<br/>", descriptionWordsReformatted);
                                        description = "<html>" + description + "</html>";

                                        thisPOI.setName(nameinput.getText());
                                        thisPOI.setLayer((String)chooseLayer.getSelectedItem() + ".png");
                                        thisPOI.setDescription(description);
                                        File inputFile = new File(UserPOIs);
                                        File tempFile = new File("tmp");
                                        tempFile.createNewFile();
                                        reader = new BufferedReader(new FileReader(inputFile));
                                        String line = reader.readLine();
                                        while (line != null) {
                                            String line_array[] = line.split("\037");
                                            if(thisPOI.getX() + 25 == Integer.parseInt(line_array[4])){
                                                System.out.println(thisPOI.getX() + 25 + "\n" + Integer.parseInt(line_array[4]) + "\n");
                                                Files.write(Paths.get("tmp"), (nameinput.getText() + "\037" + (String)chooseLayer.getSelectedItem()+ ".png" + "\037" + description + "\037" + thisPOI.getMap() + "\037" + thisPOI.getX() + "\037" + thisPOI.getY() + "\037" + thisPOI.getFavorite() + "\n").getBytes(),StandardOpenOption.APPEND);
                                            }
                                            else{
                                                Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                            }
                                            line = reader.readLine();
                                        }   reader.close();
                                        Path source = Paths.get("tmp");
                                        Files.move(source, source.resolveSibling(UserPOIs), StandardCopyOption.REPLACE_EXISTING);
                                        //tempFile.renameTo(inputFile);
                                        main.makePOIList();
                                        poiInputs[JFrameCount - 1].dispose();
                                        JFrameCount --;
                                        MapLabel.revalidate();
                                        MapLabel.repaint();
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    } finally {
                                        try {
                                            reader.close();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
             });
            }
        }
         
        BufferedReader reader = null;
        /**
        * label Favorites.
        */
        File inputFile = new File(UserFavs);
        reader = new BufferedReader(new FileReader(inputFile));
        String line1 = reader.readLine();
        while (line1 != null) {
            String line_array[] = line1.split(",");
            for(int k =0; k < POIs.length; k++){
                if(POIs[k] != null){   
                    if(mapName.equals(line_array[0]) && POIs[k].getX() + 25 == Integer.parseInt(line_array[1])){
                        POIs[k].setFavorite(Boolean.parseBoolean(line_array[2]));
                    }
                }
            }
            line1 = reader.readLine();
        } reader.close();
        main.makeFavoriteList();
        addPOIs();
        MapLabel.addMouseListener(this);
    }
    
    /**
    * Adds POIs in poi list to map.
    */
    public void addPOIs(){
        int i=0;
        int added = 0;
        while(added < POIcount){
            if(POIs[i] != null){
                for(int j = 0;j<Layers.length;j++){
                    if((Layers[j] + ".png").equals(POIs[i].getLayer())){
                        MapLabel.add(POIs[i]);
                        MapLabel.add(POIs[i].getDataLocation());
                        POIs[i].setIndex(i);
                        added++;
                    }
                }
            }
            i++;
        }
        MapLabel.revalidate();
        MapLabel.repaint();
    }
    
    
    public POI[] getPOIs(){
        return POIs;
    }
    
    public int POIcount(){
        return POIcount;
    }
    
    /**
    * Adds new POIs to map when edit mode on, otherwise clears highlights.
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (editMode == true){
            int x =e.getX();
            int y =e.getY();
            int i = 0;
            while(POIs[i] != null){
                i++;
            }
            for(int j = 0; j < JFrameCount; j++){
                if(poiInputs[j] != null){
                    poiInputs[j].dispose();
                    JFrameCount --;
                }
            }
            poiInputs[JFrameCount] = new JFrame();
            JFrameCount ++;
            poiInputs[JFrameCount - 1].setLocationRelativeTo(null);
            poiInputs[JFrameCount - 1].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel POIMakePanel = new JPanel();
            POIMakePanel.setLayout(null);
            POIMakePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            JComboBox chooseLayer = new JComboBox(Layers);
            JTextField nameinput = new JTextField();
            JTextArea descriptionInput = new JTextArea();
            JScrollPane descriptionView = new JScrollPane(descriptionInput);
            descriptionView.setBounds(90,100,180,120);
            descriptionInput.setLineWrap(true);
            descriptionInput.setWrapStyleWord(true);
            JLabel nameLabel = new JLabel("Name: ");
            JLabel layerLabel = new JLabel("Layer: ");
            JLabel descriptionLabel = new JLabel("Description: ");
            nameLabel.setBounds(50,40,130,20);
            nameinput.setBounds(90,40,130,20);
            layerLabel.setBounds(50,70,130,20);
            chooseLayer.setBounds(90,70,130,20);
            descriptionLabel.setBounds(10,100,130,20);
            descriptionInput.setBounds(90,500,180,120);
            JButton make = new JButton("Make");
            make.setBounds(110,230,80,25);
            POIMakePanel.add(nameinput);
            POIMakePanel.add(chooseLayer);
            POIMakePanel.add(descriptionView);
            POIMakePanel.add(make);
            POIMakePanel.add(nameLabel);
            POIMakePanel.add(layerLabel);
            POIMakePanel.add(descriptionLabel);
            poiInputs[JFrameCount - 1].add(POIMakePanel);
            poiInputs[JFrameCount - 1].setVisible(true);
            poiInputs[JFrameCount - 1].setSize(300,300);
            final int j = i;
            make.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) {
                    String description = descriptionInput.getText();
                    description = description.replaceAll("\n", "<br/>");
                    String[] descriptionWords = description.split(" ");
                    int lines = (descriptionWords.length/5) + 1;
                    String[] descriptionWordsReformatted = new String[lines];
                    for(int k=0;k<lines;k++){
                        if(k+1 == lines){
                            String last_line = "";
                            for(int l = k*5;l<descriptionWords.length;l++){
                                last_line = last_line + " " + descriptionWords[l];
                            }
                            descriptionWordsReformatted[k] = last_line;
                        }
                        else{
                            String this_line = "";
                            for(int l = k*5; l<(k+1)*5; l++){
                                this_line = this_line + " " + descriptionWords[l];
                            }
                            descriptionWordsReformatted[k] = this_line;
                        }
                    }
                    description = String.join("<br/>", descriptionWordsReformatted);
                    description = "<html>" + description + "</html>";
                    POIs[j] = new POI(nameinput.getText(),(String)chooseLayer.getSelectedItem() + ".png",description,Map.this.mapName, x-25, y-25, false, main, UserPOIs);
                    POIs[j].setIndex(j);
                    try {
                        Files.write(Paths.get(UserPOIs), POIs[j].getPOIData().getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    POIcount++;
                    poiInputs[JFrameCount - 1].dispose();
                    JFrameCount --;
                    MapLabel.add(POIs[POIcount-1]);
                    MapLabel.add(POIs[POIcount-1].getDataLocation());
                    main.makePOIList();
                    MapLabel.revalidate();
                    MapLabel.repaint();
                    //POI actions on click
                    POIs[j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent evt) {
                            POI thisPOI = (POI)evt.getSource();
                            if(SwingUtilities.isRightMouseButton(evt)){                        
                                if (editMode == true){
                                    try {
                                        removeHighlights();
                                        MapLabel.remove(thisPOI);
                                        MapLabel.remove(thisPOI.getDataLocation());
                                        POIcount --;
                                        
                                        File inputFile = new File(UserPOIs);
                                        File tempFile = new File("tmp");
                                        tempFile.createNewFile();
                                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                                        String line = reader.readLine();
                                        while (line != null) {
                                            String line_array[] = line.split("\037");
                                            if(thisPOI.getX() == Integer.parseInt(line_array[4])){
                                            }
                                            else{
                                                Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                            }
                                            line = reader.readLine();
                                        }   reader.close();
                                        Path source = Paths.get("tmp");
                                        Files.move(source, source.resolveSibling(UserPOIs), StandardCopyOption.REPLACE_EXISTING);
                                        //tempFile.renameTo(inputFile);
                                        
                                        File favFile = new File(UserFavs);
                                        File favtempFile = new File("tmp");
                                        tempFile.createNewFile();
                                        reader = new BufferedReader(new FileReader(favFile));
                                        String line1 = reader.readLine();
                                        while (line1 != null) {
                                            String line_array[] = line1.split(",");
                                            if(mapName.equals(line_array[0]) && thisPOI.getX() + 25 == Integer.parseInt(line_array[1])){
                                            }
                                            else{
                                                Files.write(Paths.get("tmp"), (line1 + "\n").getBytes(), StandardOpenOption.APPEND);
                                            }
                                            line1 = reader.readLine();
                                        } reader.close();
                                        Path source1 = Paths.get("tmp");
                                        Files.move(source1, source.resolveSibling(UserFavs), StandardCopyOption.REPLACE_EXISTING);
                                        
                                        POIs[thisPOI.getIndex()] = null;
                                        main.makePOIList();
                                        main.makeFavoriteList();
                                        MapLabel.revalidate();
                                        MapLabel.repaint();
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            else{
                                if (editMode == false){
                                    if(!thisPOI.getWindow().isShowing()){
                                        removeHighlights();
                                        thisPOI.add(highlight);
                                        thisPOI.setWindowLocation(thisPOI.getDataLocation());
                                        thisPOI.showWindow();
                                        thisPOI.revalidate();
                                        thisPOI.repaint();
                                    }
                                    else{
                                        removeHighlights();
                                    }
                                }
                                else{
                                    removeHighlights();
                                    for(int h = 0; h < JFrameCount; h++){
                                        if(poiInputs[h] != null){
                                            poiInputs[h].dispose();
                                            JFrameCount --;
                                        }
                                    }
                                    poiInputs[JFrameCount] = new JFrame();
                                    JFrameCount ++;
                                    poiInputs[JFrameCount - 1].setLocationRelativeTo(null);
                                    poiInputs[JFrameCount - 1].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    JPanel POIMakePanel = new JPanel();
                                    POIMakePanel.setLayout(null);
                                    POIMakePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                                    JComboBox chooseLayer = new JComboBox(Layers);
                                    JTextField nameinput = new JTextField();
                                    JTextArea descriptionInput = new JTextArea();
                                    JScrollPane descriptionView = new JScrollPane(descriptionInput);
                                    descriptionView.setBounds(90,100,180,120);
                                    descriptionInput.setLineWrap(true);
                                    descriptionInput.setWrapStyleWord(true);
                                    JLabel nameLabel = new JLabel("Name: ");
                                    JLabel layerLabel = new JLabel("Layer: ");
                                    JLabel descriptionLabel = new JLabel("Description: ");
                                    nameLabel.setBounds(50,40,130,20);
                                    nameinput.setBounds(90,40,130,20);
                                    layerLabel.setBounds(50,70,130,20);
                                    chooseLayer.setBounds(90,70,130,20);
                                    descriptionLabel.setBounds(10,100,130,20);
                                    descriptionInput.setBounds(90,500,180,120);
                                    JButton make = new JButton("Make");
                                    make.setBounds(110,230,80,25);
                                    POIMakePanel.add(nameinput);
                                    POIMakePanel.add(chooseLayer);
                                    POIMakePanel.add(descriptionView);
                                    POIMakePanel.add(make);
                                    POIMakePanel.add(nameLabel);
                                    POIMakePanel.add(layerLabel);
                                    POIMakePanel.add(descriptionLabel);
                                    poiInputs[JFrameCount - 1].add(POIMakePanel);
                                    poiInputs[JFrameCount - 1].setVisible(true);
                                    poiInputs[JFrameCount - 1].setSize(300,300);
                                    //Edit POI on click
                                    make.addActionListener(new ActionListener() { 
                                        public void actionPerformed(ActionEvent e) {
                                            BufferedReader reader = null;
                                            try {
                                                String description = descriptionInput.getText();
                                                description = description.replaceAll("\n", "<br/>");
                                                String[] descriptionWords = description.split(" ");
                                                int lines = (descriptionWords.length/5) + 1;
                                                String[] descriptionWordsReformatted = new String[lines];
                                                for(int k=0;k<lines;k++){
                                                    if(k+1 == lines){
                                                        String last_line = "";
                                                        for(int l = k*5;l<descriptionWords.length;l++){
                                                            last_line = last_line + " " + descriptionWords[l];
                                                        }
                                                        descriptionWordsReformatted[k] = last_line;
                                                    }
                                                    else{
                                                        String this_line = "";
                                                        for(int l = k*5; l<(k+1)*5; l++){
                                                            this_line = this_line + " " + descriptionWords[l];
                                                        }
                                                        descriptionWordsReformatted[k] = this_line;
                                                    }
                                                }   description = String.join("<br/>", descriptionWordsReformatted);
                                                description = "<html>" + description + "</html>";
                                                
                                                thisPOI.setName(nameinput.getText());
                                                thisPOI.setLayer((String)chooseLayer.getSelectedItem() + ".png");
                                                thisPOI.setDescription(description);
                                                File inputFile = new File(UserPOIs);
                                                File tempFile = new File("tmp");
                                                tempFile.createNewFile();
                                                reader = new BufferedReader(new FileReader(inputFile));
                                                String line = reader.readLine();
                                                while (line != null) {
                                                    String line_array[] = line.split("\037");
                                                    if(thisPOI.getX() == Integer.parseInt(line_array[4])){
                                                        Files.write(Paths.get("tmp"), (thisPOI.getName() + "\037" + thisPOI.getLayer() + "\037" + thisPOI.getDescription() + "\037" + thisPOI.getMap() + "\037" + thisPOI.getX() + "\037" + thisPOI.getY() + "\037" + thisPOI.getFavorite() + "\n").getBytes(),StandardOpenOption.APPEND);
                                                    }
                                                    else{
                                                        Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                                    }
                                                    line = reader.readLine();
                                                }   reader.close();
                                                Path source = Paths.get("tmp");
                                                Files.move(source, source.resolveSibling(UserPOIs), StandardCopyOption.REPLACE_EXISTING);
                                                //tempFile.renameTo(inputFile);
                                                main.makePOIList();
                                                poiInputs[JFrameCount - 1].dispose();
                                                JFrameCount --;
                                                MapLabel.revalidate();
                                                MapLabel.repaint();
                                            } catch (FileNotFoundException ex) {
                                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IOException ex) {
                                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                            } finally {
                                                try {
                                                    reader.close();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                     });
                } 
            } );
        }
        removeHighlights();
    }
    
    /**
    * Clears highlights and shown data from all POIs on map.
    */
    public void removeHighlights(){
        for(int i=0; i <POIcount; i++){
            if(POIs[i] != null){
                POIs[i].remove(highlight);
                POIs[i].closeWindow();
            }
        }
        MapLabel.revalidate();
        MapLabel.repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    public Boolean getEditMode() {
        return editMode;
    }
    /**
    * @param editMode signal from MainGUI
    * receives signal from MainGUI to turn edit mode on
    */
    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
        for(int i = 0; i < JFrameCount; i++){
            if(poiInputs[i] != null){
                poiInputs[i].dispose();
                JFrameCount --;
            }
        }
        removeHighlights();
    }

    public JLabel getMapLabel() {
        return MapLabel;
    }
    
    public JLabel getHighlight() {
        return highlight;
    }
    
    public String getMapName(){
        return mapName;
    }
    public String[] getLayers(){
        return Layers;
    }
}
