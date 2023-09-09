/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.atlas.uwomap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author caram
 */
public class MapTest {
    
    public MapTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addPOIs method, of class Map.
     */
    @Test
    public void testAddPOIs() {
        System.out.println("addPOIs");
        try
        {
            Map instance = new Map("HealthSciencesCenter1", "Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300", new MainGUI("Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300"));
            instance.addPOIs();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Test of POIcount method, of class Map.
     */
    @Test
    public void testPOIcount() {
        System.out.println("POIcount");
        try
        {
            Map instance = new Map("HealthSciencesCenter1", "Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300", new MainGUI("Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300"));
            int expResult = 0;
            int result = instance.POIcount();
            assertEquals(expResult, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Test of removeHighlights method, of class Map.
     */
    @Test
    public void testRemoveHighlights() {
        System.out.println("removeHighlights");
        try
        {
            Map instance = new Map("HealthSciencesCenter1", "Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300", new MainGUI("Women's Washroom,Washroom,You Pee Here,Middlesex2,300,300"));
            instance.removeHighlights();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }    
}
