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
public class ParserUnexpectedTokenException extends ParserException {

    private static final long serialVersionUID = 234761238746L;
    
    private final LexToken lexToken;

    public ParserUnexpectedTokenException(LexToken lexToken, int position) {
        super(position);
        this.lexToken = lexToken;
    }

    public LexToken getLexToken() {
        return lexToken;
    }

    
    @Override
    public String getMessage() {
        return new StringBuilder("Непредвиденный токен ")
                .append(lexToken)
                .append(", позиция ")
                .append(position).toString();
    }
    
}
