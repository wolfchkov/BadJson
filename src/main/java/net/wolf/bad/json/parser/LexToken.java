/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

/**
 * Токен парсера лексем. Содержит тип лексемы и, опционально, значение.
 * @author Volchkov Andrey
 */
public final class LexToken {
    
    private final LexTokenType type;
    private final Object value;

    public LexToken(LexTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public LexToken(LexTokenType type) {
        this(type, null);
    }
    
    
    public LexTokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + "type=" + type + ", value=" + value + '}';
    }
    
    
}
