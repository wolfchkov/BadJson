/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

import java.util.Arrays;

/**
 * Классы симdолов лексера и таблица соответсвия кода символа строки  - кдассу;
 * @author Volchkov Andrey
 */
public enum LexCharClass {
    //Игнорируемые символы
    IGNORE,
    //{
    LEFT_BRACE,
    //}
    RIGHT_BRACE,
    //,
    COMMA,
    //:
    COLON,
    //"
    QUOTE,
    //пробел
    SPACE,
    //класс относится к значениям (boolean, number, null)
    VALUE;
    
    
    static final int CLASS_TABLE_SIZE = 255;
    //эскейп символ для ковычек
    static final int ESCAPE_CHAR = (int) '\\';
    static final LexCharClass[] CLASS_TABLE = new LexCharClass[255];
    //инициализируем массив классами
    static {
        Arrays.fill(CLASS_TABLE, LexCharClass.IGNORE);
        Arrays.fill(CLASS_TABLE, (int) 'A', (int) 'Z', LexCharClass.VALUE);
        Arrays.fill(CLASS_TABLE, (int) 'a', (int) 'z', LexCharClass.VALUE);
        Arrays.fill(CLASS_TABLE, (int) '0', (int) '9', LexCharClass.VALUE);
        CLASS_TABLE[(int) '-'] = LexCharClass.VALUE;
        CLASS_TABLE[(int) '.'] = LexCharClass.VALUE;
        
        CLASS_TABLE[(int) '}'] = LexCharClass.RIGHT_BRACE;
        CLASS_TABLE[(int) '{'] = LexCharClass.LEFT_BRACE;
        CLASS_TABLE[(int) ':'] = LexCharClass.COLON;
        CLASS_TABLE[(int) ','] = LexCharClass.COMMA;
        CLASS_TABLE[(int) '"'] = LexCharClass.QUOTE;
        CLASS_TABLE[(int) ' '] = LexCharClass.SPACE;
        CLASS_TABLE[0x0d] = LexCharClass.SPACE;
        CLASS_TABLE[0x0a] = LexCharClass.SPACE;
    }
}



