/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atlas.uwomap;


import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates a tabbed pain representing a building with multiple floors.
 * - addMap method adds a new tab for the map of the floor.
 * 
 * @version 1.0
 * @author Nandhitha Krishnan
 * @author Alysha Aul
 * @author Hirbod Hosseini
 * @author Jacqueline Kahnert
 * @author Ramtin Sirjani
 */
public class Building extends JTabbedPane{
    private String name = new String();
    private Map[] maps = new Map[10];
    private int numMaps = 0;
    private JScrollPane[] mapScroller = new JScrollPane[10];

    public Building(String name){
        this.name = name;
        this.setBounds(225,10,870,590);
        this.setOpaque(true);
    }
    
    public void addMap(String tabName, Map map){
        JScrollPane thisMap = new JScrollPane(map.getMapLabel());
        this.add(tabName, thisMap);
        Dimension size = thisMap.getViewport().getViewSize();
        int x = (size.width - 890) / 2;
        int y = (size.height - 600) / 2;
        thisMap.getViewport().setViewPosition(new Point(x, y));
        maps[numMaps] = map;
        mapScroller[numMaps] = thisMap;
        numMaps++;
        thisMap.getVerticalScrollBar().setUnitIncrement(10);
        thisMap.getHorizontalScrollBar().setUnitIncrement(10);
        thisMap.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int i = 0;
                Building.this.getMaps()[(Building.this.getSelectedIndex())].removeHighlights();
            }
        });
    }

    public Map[] getMaps() {
        return maps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
    
    public JScrollPane getMapScroller(int i){
        return mapScroller[i];
    }
}
