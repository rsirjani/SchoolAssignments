package com.atlas.uwomap;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Builds POI with relevant data.
 * 
 * POIs data is useful for map and POIbox classes to retrieve information for specific POIs.
 * Primarily getters and setters.
 * 
 * @version 1.0
 * @author Nandhitha Krishnan
 * @author Alysha Aul
 * @author Hirbod Hosseini
 * @author Jacqueline Kahnert
 * @author Ramtin Sirjani
 */
public class POI extends JLabel{
    /** X coordinate of POI*/
    private int x;
    /** Y coordinate of POI*/
    private int y;
    /** Index in map POI list*/
    private int index;
    /** Name of POI*/
    private String name;
    /** Layer POI belongs to*/
    private String layer;
    /** Description of POI*/
    private String description;
    /** Map POI belongs to*/
    private String map;
    /** JFrame to display POI description and name*/
    private JFrame POIDATA = new JFrame();
    /** Label to communicate where POIDATA should spawn*/
    private JLabel dataLocation = new JLabel();
    /** Variable to change when showing or hiding layer*/
    private boolean hidden = false;
    /** Variable to change when POI is considered favorite*/
    private boolean favorite;
    /** Info to add to POIDATA frame*/
    private JLabel nameLabel = new JLabel("", JLabel.CENTER);
    private JLabel descriptionLabel = new JLabel();
    private ImageIcon favImageIcon;
    private ImageIcon favOffImageIcon;
    /** File from which to read favorite info*/
    private String Favs;
    /** Boolean variable to communicate whether this is a built in POI or not*/
    private boolean isBuiltIn = false;
    /** Button to set or clear POI as favorite*/
    private JButton FavoriteButton = new JButton();
    
    
    /** 
     * Constructor
     * @param name - name of POI
     * @param layer - layer this POI belongs to
     * @param description - description of POI
     * @param map - map this POI belongs to
     * @param x  - x position on map
     * @param y - y position on Map
     * @param favorite - used to set favorite in Map class
     * @param UserPOIs - Used to read in favorite status
     * @param main - used to call makeFavoritesList if POI favorite state is changed.
     * - Sets instance variables.
     * 
     * This method creates a JLabel associated with a map for a floor of a UWO building.
     */
    public POI(String name,  String layer, String description, String map, int x, int y, boolean favorite, MainGUI main, String UserPOIs){
        if(UserPOIs.equals("BuiltinPOIs.txt")){
            Favs = "DEVFavorites.txt"; 
        }
        else{
            Favs = UserPOIs.substring(0, UserPOIs.length() - 8) + "Favorites.txt";
        }
        this.name = name;
        this.layer = layer;
        this.description = description;
        this.map = map;
        this.x = x;
        this.y = y;
        this.favorite = favorite;
        this.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
        this.setBounds(x,y, 50,50);
        BufferedImage poImage = null;
        try {
            poImage = ImageIO.read(new File(layer));
        } catch (IOException ex) {
            Logger.getLogger(POI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image poiImage = poImage.getScaledInstance(this.getWidth(), this.getHeight(),
        Image.SCALE_SMOOTH);
        ImageIcon POIimage = new ImageIcon(poiImage);
        this.setIcon(POIimage);
        POIDATA.setSize(250,200);
        POIDATA.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        POIDATA.setUndecorated(true);
        POIDATA.setAlwaysOnTop(true);
        JPanel poidatapanel = new JPanel();
        JScrollPane poidatascroll = new JScrollPane();
        poidatapanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        nameLabel.setText(this.name);
        nameLabel.setFont(new Font("calibri", Font.BOLD, 20));
        descriptionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        descriptionLabel.setText(this.description);
        descriptionLabel.setHorizontalAlignment(CENTER);
              
        //FavoriteImage
        
        BufferedImage favOffImage = null;
        try {
            favOffImage = ImageIO.read(new File("star.png"));
        } catch (IOException ex) {
            Logger.getLogger(POI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image favOffImage1 = favOffImage.getScaledInstance(25, 25,Image.SCALE_SMOOTH);
        favOffImageIcon = new ImageIcon(favOffImage1);
        BufferedImage favImage = null;
        try {
            favImage = ImageIO.read(new File("star(1).png"));
        } catch (IOException ex) {
            Logger.getLogger(POI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image favImage1 = favImage.getScaledInstance(25, 25,Image.SCALE_SMOOTH);
        favImageIcon = new ImageIcon(favImage1);     
        if(this.favorite){
            FavoriteButton.setIcon(favImageIcon);
        }
        else{
            FavoriteButton.setIcon(favOffImageIcon);
        }
        FavoriteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton thisButton = (JButton) e.getSource();
                if(POI.this.favorite == false){
                    try {
                        POI.this.favorite = true;
                        thisButton.setIcon(favImageIcon);
                        if(isBuiltIn || UserPOIs.equals("BuiltinPOIs.txt")){
                            BufferedReader reader = null;
                            File inputFile = new File(Favs);
                            File tempFile = new File("tmp");
                            tempFile.createNewFile();
                            reader = new BufferedReader(new FileReader(inputFile));
                            String line = reader.readLine();
                            boolean happened = false;
                            while (line != null) {
                                String line_array[] = line.split(",");
                                if(x + 25 == Integer.parseInt(line_array[1])){
                                    Files.write(Paths.get("tmp"), (map + "," + (x + 25) + "," + POI.this.favorite + "\n").getBytes(),StandardOpenOption.APPEND);
                                    happened = true;
                                }
                                else{
                                    Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                }
                                line = reader.readLine();
                            }   reader.close();
                            if(!happened){
                                Files.write(Paths.get("tmp"), (map + "," + (x + 25) + "," + POI.this.favorite + "\n").getBytes(),StandardOpenOption.APPEND);
                            }
                            Path source = Paths.get("tmp");
                            Files.move(source, source.resolveSibling(Favs), StandardCopyOption.REPLACE_EXISTING);
                            //tempFile.renameTo(inputFile);
                            main.makeFavoriteList();
                        }
                        else{
                            //write to file
                            BufferedReader reader = null;
                            File inputFile = new File(UserPOIs);
                            File tempFile = new File("tmp");
                            tempFile.createNewFile();
                            reader = new BufferedReader(new FileReader(inputFile));
                            String line = reader.readLine();
                            while (line != null) {
                                String line_array[] = line.split("\037");
                                if(x == Integer.parseInt(line_array[4])){
                                    Files.write(Paths.get("tmp"), (name + " \037" + layer + "\037" + description + "\037" + map + "\037" + x + "\037" + y + "\037" + POI.this.favorite + "\n").getBytes(),StandardOpenOption.APPEND);
                                }
                                else{
                                    Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                }
                                line = reader.readLine();
                            }   reader.close();
                            Path source = Paths.get("tmp");
                            Files.move(source, source.resolveSibling(UserPOIs), StandardCopyOption.REPLACE_EXISTING);
                            //tempFile.renameTo(inputFile);
                            main.makeFavoriteList();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(POI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    try {
                        POI.this.favorite = false;
                        thisButton.setIcon(favOffImageIcon);
                        if(isBuiltIn || UserPOIs.equals("BuiltinPOIs.txt")){
                            BufferedReader reader = null;
                            File inputFile = new File(Favs);
                            File tempFile = new File("tmp");
                            tempFile.createNewFile();
                            reader = new BufferedReader(new FileReader(inputFile));
                            String line = reader.readLine();
                            boolean happened = false;
                            while (line != null) {
                                String line_array[] = line.split(",");
                                if(x + 25 == Integer.parseInt(line_array[1])){
                                    Files.write(Paths.get("tmp"), (map + "," + (x + 25) + "," + POI.this.favorite + "\n").getBytes(),StandardOpenOption.APPEND);
                                    happened = true;
                                }
                                else{
                                    Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                }
                                line = reader.readLine();
                            }   reader.close();
                            if(!happened){
                                Files.write(Paths.get("tmp"), (map + "," + (x + 25) + "," + POI.this.favorite + "\n").getBytes(),StandardOpenOption.APPEND);
                            }
                            Path source = Paths.get("tmp");
                            Files.move(source, source.resolveSibling(Favs), StandardCopyOption.REPLACE_EXISTING);
                            //tempFile.renameTo(inputFile);
                            main.makeFavoriteList();
                        }
                        else{
                            //write to file
                            BufferedReader reader = null;
                            File inputFile = new File(UserPOIs);
                            File tempFile = new File("tmp");
                            tempFile.createNewFile();
                            reader = new BufferedReader(new FileReader(inputFile));
                            String line = reader.readLine();
                            while (line != null) {
                                String line_array[] = line.split("\037");
                                if(x == Integer.parseInt(line_array[4])){
                                    Files.write(Paths.get("tmp"), (name + "\037" + layer + "\037" + description + "\037" + map + "\037" + x + "\037" + y + "\037" + POI.this.favorite + "\n").getBytes(),StandardOpenOption.APPEND);
                                }
                                else{
                                    Files.write(Paths.get("tmp"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
                                }
                                line = reader.readLine();
                            }   reader.close();
                            Path source = Paths.get("tmp");
                            Files.move(source, source.resolveSibling(UserPOIs), StandardCopyOption.REPLACE_EXISTING);
                            //tempFile.renameTo(inputFile);
                            main.makeFavoriteList();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(POI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        poidatapanel.setLayout(new BoxLayout(poidatapanel, BoxLayout.Y_AXIS));
        poidatapanel.add(FavoriteButton);
        poidatapanel.add(nameLabel);
        poidatapanel.add(descriptionLabel);
        poidatascroll.setViewportView(poidatapanel);
        POIDATA.add(poidatascroll);
        dataLocation.setBounds(x-125,y-100,1,1);
    }
    
    public String getPOIData(){
        return (this.name + "\037" + this.layer + "\037" + this.description + "\037" + this.map + "\037" + this.x + "\037" + this.y + "\037" + this.favorite + "\n");
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        nameLabel.setText("Name: " + name);
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
        BufferedImage poImage = null;
        try {
            poImage = ImageIO.read(new File(layer));
        } catch (IOException ex) {
            Logger.getLogger(POI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image poiImage = poImage.getScaledInstance(this.getWidth(), this.getHeight(),
        Image.SCALE_SMOOTH);
        ImageIcon POIimage = new ImageIcon(poiImage);
        this.setIcon(POIimage);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        descriptionLabel.setText(description);
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
    
    public void setIndex(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return index;
    }  
    
    public JFrame getWindow(){
        return POIDATA;    
    }
    
    public void setWindowLocation(Component here){
        POIDATA.setLocationRelativeTo(here);    
    }
    public void showWindow(){
        POIDATA.setVisible(true);    
    }
     
    public void closeWindow(){
        POIDATA.dispose();
    }
    
    public JLabel getDataLocation(){
        return dataLocation;
    }
    
    public void setHidden(boolean value){
        hidden = value;
    }
    
    public boolean getHidden(){
        return hidden;
    }
    
    public boolean  getFavorite(){
        return favorite;
    }
    
    public ImageIcon getFavIcon(){
        return favImageIcon;
    }
    
    public void setIsBuiltIn(boolean value){
        isBuiltIn = value;
    }
    
    public void setFavorite(Boolean value){
        favorite = value;
        if(value){
            FavoriteButton.setIcon(favImageIcon);
        }
        else{
            FavoriteButton.setIcon(favOffImageIcon);
        }
    }
}
