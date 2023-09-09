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
 * @author nandh
 */
public class POITest {
    
    public POITest() {
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
     * Test of getPOIData method, of class POI.
     */
    @Test
    public void testGetPOIData() {
        System.out.println("getPOIData");
        try 
        {
        POI instance = new POI("Women's Washroom", "Washroom", "Female toilet", "Middlesex0", 2, 1, false, new MainGUI("Women's Washroom"), "Women's Washroom");
        String expResult = "";
        String result = instance.getPOIData();
        assertEquals(expResult, result);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
