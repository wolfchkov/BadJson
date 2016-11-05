/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Volchkov Andrey
 */
public class FastStackTest {
    
    /**
     * Test of isEmpty method, of class FastStack.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        FastStack<Object> stack = new FastStack<>();
        assertTrue(stack.isEmpty());
        
        stack.push(new Object());
        stack.push(new Object());
        assertFalse(stack.isEmpty());
        
        stack.pop();
        assertFalse(stack.isEmpty());
        
        stack.pop();
        assertTrue(stack.isEmpty());
        
    }

    /**
     * Test of getTop method, of class FastStack.
     */
    @Test
    public void testGetTop() {
        System.out.println("getTop");
        FastStack<String> stack = new FastStack<>();
        
        stack.push("V1");
        stack.push("V2");
        stack.push("V3");
        stack.push("V4");
        
        assertEquals("V4", stack.getFirst());
        assertEquals("V4", stack.getFirst());
        assertEquals("V4", stack.getFirst());
        
         stack.pop();
         
         assertEquals("V3", stack.getFirst());
        assertEquals("V3", stack.getFirst());
        assertEquals("V3", stack.getFirst());
        
    }

    /**
     * Test of push method, of class FastStack.
     */
    @Test
    public void testPush() {
        System.out.println("push");
        FastStack<String> stack = new FastStack<>();
        
        stack.push("V1");
        stack.push("V2");
        stack.push("V3");
        stack.push("V4");
        
        assertEquals("V4", stack.pop());
        assertEquals("V3", stack.pop());
        assertEquals("V2", stack.pop());
        assertEquals("V1", stack.pop());
        assertEquals(null, stack.pop());
    }

    /**
     * Test of pop method, of class FastStack.
     */
    @Test
    public void testPop() {
        System.out.println("pop");
        FastStack<String> stack = new FastStack<>();
        
        stack.push("V1");
        stack.push("V2");
        stack.push("V3");
        stack.push("V4");
        
        assertEquals("V4", stack.pop());
        assertEquals("V3", stack.pop());
        assertEquals("V2", stack.pop());
        assertEquals("V1", stack.pop());
        assertEquals(null, stack.pop());
    }
    
}

