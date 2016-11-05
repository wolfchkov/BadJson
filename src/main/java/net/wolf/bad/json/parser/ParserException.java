/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

/**
 * Общее исключение для парсеров
 * @author Volchkov Andrey
 */
public class ParserException extends Exception {

    private static final long serialVersionUID = 4579345479L;
    
    protected int position;

    public ParserException(int position) {
        this.position = position;
    }

}
