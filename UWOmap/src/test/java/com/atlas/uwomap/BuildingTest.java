/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.atlas.uwomap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author caram
 */
public class BuildingTest {
    
    public BuildingTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of addMap method, of class Building.
     */
    @org.junit.jupiter.api.Test
    public void testAddMap() {
        System.out.println("addMap");
        try
        {
            String tabName = "Level 1";
            Map map = new Map("HealthSciencesCenter1", "Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300", new MainGUI("Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300"));
            Building instance = new Building("Health Sciences Center");
            instance.addMap(tabName, map);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }    
}
