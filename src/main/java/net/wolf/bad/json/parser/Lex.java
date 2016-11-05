/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

import java.io.IOException;

import static net.wolf.bad.json.parser.LexCharClass.*;

/**
 * Парсит входной массив символов на лексемы.
 *
 * @author Volchkov Andrey
 */
public class Lex {
    //Теперь можно "дешево" бросать это исключение
    //Конец чтения
    private final static Exception EOF = new Exception();

    //массив символов, который разбиваем на лексемы
    private final char[] buffer;
    //размер массива
    private int buffSize = 0;
    //текущая позиция чтения лексем
    private int buffPos = 0;
    //предыдущий тип лексемы
    private LexCharClass prevClass = null;

    public Lex(char[] in) {
        this.buffer = in;
        this.buffSize = buffer.length;
    }

    /**
     * Читаем очередной символ либо выбрасываем EOF
     * @return очередной символ
     * @throws LexException = EOF, если прочитали весь массив
     */
    private int readChar() throws Exception {
        if (buffPos < buffSize) {
            return buffer[buffPos++];
        }
        throw EOF;
    }

    public int getPosition() {
        return buffPos;
    }

    /**
     * Парсим поток на лексемы и возвращаем токен 
     * @return очередной токен
     * @throws ParserException если возникла ошибка парсинга
     */
    public LexToken nextToken() throws ParserException {
        try {
            while (true) {
                //читаем очередной символ с массива
                int ch = readChar();
                if (ch > CLASS_TABLE_SIZE) {
                    throw new ParserUnexpectedCharException((char)ch, buffPos);
                }
                
                //Выбираем к какому классу относится символ и возвращаем 
                //соответсвующий токен
                switch (CLASS_TABLE[ch]) {
                    case RIGHT_BRACE:
                        prevClass = RIGHT_BRACE;
                        return new LexToken(LexTokenType.RIGHT_BRACE);
                    case LEFT_BRACE:
                        prevClass = LEFT_BRACE;
                        return new LexToken(LexTokenType.LEFT_BRACE);
                    case COLON:
                        checkOnDuplicate(ch, COLON);
                        prevClass = COLON;
                        return new LexToken(LexTokenType.COLON);
                    case COMMA:
                        checkOnDuplicate(ch, COMMA);
                        prevClass = COMMA;
                        return new LexToken(LexTokenType.COMMA);
                    case QUOTE:
                        //'"' и предыдущий символ ':' - читаем строку-значение, 
                        //                 игнорируя не экраннированные ковычки
                        //предыдущий символ не ':' - значит читаем строку-имя поля
                        //                 без игнорирования не экраннированныъ ковычек
                        String value = prevClass == COLON
                                ? readUglyString() : readString();
                        prevClass = QUOTE;
                        return new LexToken(LexTokenType.VALUE, value);
                    case VALUE:
                        if (prevClass == COLON) {
                            prevClass = VALUE;
                            return createValueToken(readValue());
                        } else {
                            throw new ParserUnexpectedCharException((char)ch, buffPos);
                        }
                    case IGNORE:
                    //skip;                
                }
            }
        } catch (ParserException pex) {
            throw pex;
        } catch (Exception ex) {
            if (ex == EOF) {
                return new LexToken(LexTokenType.EOF);
            }
            throw new RuntimeException(ex);
        }
    }

    /**
     * Читаем не валидную строку JSON с не экранированными QUOTE.
     * Так как нужно все же где то остановится, то останавливаемся, если после 
     * QUOTE следует ," (новая пара поле-значение) либо } (завершение объекта)
     * @return
     * @throws Exception 
     */
    private String readUglyString() throws Exception {
        int prevChar, ch, maybeStop, start;
        start = maybeStop = buffPos;
        ch = -1;
        boolean run = true;
        boolean search = false;
        boolean expectQuoteOrNum = false;
        while (run) {
            prevChar = ch;
            ch = readChar();
            if (ch < CLASS_TABLE_SIZE) {
                LexCharClass charClass = CLASS_TABLE[ch];
                if (charClass == LexCharClass.QUOTE && prevChar != ESCAPE_CHAR
                        && !search) {
                    maybeStop = buffPos;
                    search = true;
                } else if (search) {
                    switch (charClass) {
                        case RIGHT_BRACE:
                            run = false;
                            break;
                        case COMMA:
                            expectQuoteOrNum = true;
                            break;
                        case QUOTE:
                            run = !expectQuoteOrNum;
                            break;
                        case SPACE:
                            break;
                        default:
                            search = expectQuoteOrNum = false;
                    }
                }
            } else {
                search = expectQuoteOrNum = false;
            }
        }
        buffPos = maybeStop;
        return new String(buffer, start, maybeStop - start - 1);
    }

    /**
     * Читаем значение поля. Булевые, цифры, либо null.
     * @param val 
     * @return
     * @throws LexException 
     */    
    private LexToken createValueToken(String val) throws ParserException {
        try {
            Object parsed = ValueParser.parseValue(val);
            return new LexToken(LexTokenType.VALUE, parsed);
        } catch (NumberFormatException nfex) {
            throw new ParserUnexpectedTokenException(new LexToken(LexTokenType.VALUE, val), buffPos);
        }
    }

    /**
     * Читаем обычноую строку JSON. Все внутринние '"' должны быть экранированными.
     * Возвращаемая строка - все символы между не экранированными '"';
     * @return прочитанная строка 
     * @throws Exception 
     */       
    private String readString() throws Exception {
        int ch, prevChar = -1, start = buffPos;
        ch = readChar();
        while (CLASS_TABLE[ch] != LexCharClass.QUOTE || prevChar == ESCAPE_CHAR) {
            prevChar = ch;
            ch = readChar();
        }

        return new String(buffer, start, buffPos - start - 1);
    }

    /**
     * Читаем значение поля. Булевые, цифры, либо null.
     * Читаем все символы, входящие в класс LexCharClass.VALUE, до первого не вхождения.
     * @return прочитанная строка 
     * @throws Exception 
     */   
    private String readValue() throws Exception {
        int ch, stop, start = stop = buffPos - 1;
        boolean run = true;
        while (true) {
            ch = readChar();
            if (ch > CLASS_TABLE_SIZE || CLASS_TABLE[ch] != LexCharClass.VALUE) {
                stop = buffPos;
                buffPos--;
                break;
            }
        }
        return new String(buffer, start, stop - start - 1);
    }
    

    private void checkOnDuplicate(int ch, LexCharClass сharClass) throws ParserException {
        if (prevClass == сharClass) {
            throw new ParserUnexpectedCharException((char)ch, buffSize);
        }
    }    

    public static void main(String var[]) throws Exception {
        System.out.println("test");
        String json = "{\"t55\":true, \"t2\":\"Это тест \" с  \" ковычками \\\", \",\"t3\":\"sdfsdf\"    }";

        System.out.println(json);
        Lex lex = new Lex(json.toCharArray());
        LexToken nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);
        nextToken = lex.nextToken();
        System.out.println(nextToken);

    }

}
