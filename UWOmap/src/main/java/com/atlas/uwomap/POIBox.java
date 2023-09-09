/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atlas.uwomap;

import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 * Creates custom buttons for POIs that also store poi data for easy access.
 * 
 * @version 1.0
 * @author Nandhitha Krishnan
 * @author Alysha Aul
 * @author Hirbod Hosseini
 * @author Jacqueline Kahnert
 * @author Ramtin Sirjani
 */
public class POIBox extends JButton{
    Map map;
    POI poi;
    Building building;
    JScrollPane MapScroll;

    public POIBox(Map map, POI poi, JScrollPane MapScroll) {
        this.map = map;
        this.poi = poi;
        this.MapScroll = MapScroll;
    }
    
    public Map getMap(){
        return map;
    }
    
    public POI getPOI(){
        return poi;
    }
    
    public JScrollPane getMapScroll(){
        return MapScroll;
    }
    public void setBuilding(Building building){
        this.building = building;
    }
    
    public Building getBuilding(){
        return building;
    }
}
