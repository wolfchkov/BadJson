/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

/**
 *
 * @author Volchkov Andrey, openfisher.com
 */
public enum LexTokenType {
    VALUE,
    LEFT_BRACE,
    RIGHT_BRACE,
    LEFT_SQUARE,
    RIGHT_SQUARE,
    COMMA,
    COLON,
    NUMBER,
    EOF;
    
}
