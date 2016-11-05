/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

/**
 * Утилитарный  класс.
 * @author Volchkov Andrey
 */
public final class ValueParser {
    
    
    /**
     * Парсит значение json поля.
     * Ожидается либо boolean (true/false), либо null
     * В противном случае парсим как число.
     * @param value
     * @return 
     */
    static Object parseValue(String value) {
        final String v = value.toLowerCase();
        switch(v) {
            case "true": return Boolean.TRUE;
            case "false": return Boolean.FALSE;
            case "null": return null;
            default:
                if (v.contains(".")) {
                    return Float.parseFloat(v);
                } else {
                    return Integer.parseInt(v);
                }
        }
        
    }
}
