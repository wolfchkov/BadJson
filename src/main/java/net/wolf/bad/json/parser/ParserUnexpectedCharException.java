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
public class ParserUnexpectedCharException extends ParserException {

    private static final long serialVersionUID = 21282345983523L;

    private final char ch;
    
    public ParserUnexpectedCharException(char ch, int position) {
        super(position);
        this.ch = ch;
    }

    public char getCh() {
        return ch;
    }
    
    @Override
    public String getMessage() {
        return new StringBuilder("Непредвиденный символ '")
                .append(ch)
                .append('\'')
                .append(", позиция ")
                .append(position).toString();
    }
}
