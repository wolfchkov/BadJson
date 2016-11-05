/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser;

import java.io.IOException;
import net.wolf.bad.json.JsonObject;
import net.wolf.bad.json.parser.util.FastStack;

/**
 * Парсер. представляет собой автомат, который проходится по лексемам входного
 * потока JSON строки и конструирует JsonObject.
 *
 * Потокобезопасный.
 *
 * @author Volchkov Andrey
 */
public class JsonParser {

    //Состояния парсера
    private static enum Sate {
        //начали работу
        START,
        //внутри объекта
        IN_OBJECT,
        //собираем поле и значения объекта
        COLLECT_KEY_VALUE,
        //закончили работу
        FINISH,
        //ошибка
        ERROR;
    }

    /**
     * Парсиn строку JSON и возвращаем JsonObject.
     *
     * @param str строка JSON которую нужно распарсить.
     * @return JsonObject представляющий распарcенный JSON
     * @throws ParserException при ошибках парсинга
     */
    public JsonObject parseJson(String str) throws ParserException {

        FastStack<Sate> stateStack = new FastStack<>();
        FastStack<Object> valueStack = new FastStack<>();

        Sate state = Sate.START;
        Lex lex = new Lex(str.toCharArray());
        LexToken token = lex.nextToken();

        while (token.getType() != LexTokenType.EOF) {

            switch (state) {

                case START:
                    switch (token.getType()) {
                        case LEFT_BRACE:
                            stateStack.push(state);
                            state = Sate.IN_OBJECT;
                            valueStack.push(new JsonObject());
                            break;
                        default:
                            state = Sate.ERROR;
                    }
                    break;

                case IN_OBJECT:
                    switch (token.getType()) {
                        case RIGHT_BRACE:

                            if (valueStack.size() > 1) {
                                valueStack.pop();
                            } else {
                                state = Sate.FINISH;
                            }
                            break;
                        case VALUE:
                            valueStack.push(token.getValue());
                            stateStack.push(state);
                            state = Sate.COLLECT_KEY_VALUE;
                            break;
                        case COMMA:
                            break;
                        default:
                            state = Sate.ERROR;
                    }
                    break;

                case COLLECT_KEY_VALUE:
                    switch (token.getType()) {
                        case COLON:
                            break;
                        case VALUE:
                            state = stateStack.pop();
                            Object key = valueStack.pop();
                            if (key instanceof String) {
                                JsonObject topObj = (JsonObject) valueStack.getFirst();
                                topObj.put((String) key, token.getValue());
                            } else {
                                state = Sate.ERROR;
                            }
                            break;
                        case LEFT_BRACE:
                            state = stateStack.pop();
                            key = valueStack.pop();
                            if (key instanceof String) {
                                JsonObject topObj = (JsonObject) valueStack.getFirst();
                                JsonObject newObj = new JsonObject();
                                topObj.put((String) key, newObj);
                                valueStack.push(newObj);
                            } else {
                                state = Sate.ERROR;
                            }
                            break;
                        default:
                            state = Sate.ERROR;
                    }
                    break;
                case FINISH:
                    return (JsonObject) valueStack.pop();
                case ERROR:
                    throw new ParserUnexpectedTokenException(token, lex.getPosition());
            }
            token = lex.nextToken();
        }

        return (JsonObject) valueStack.pop();
    }

    public static void main(String var[]) throws IOException {
        System.out.println("test");
        String json = "{"
                + "\"field1\" : {"
                + "\"f1\":true,  "
                + "\"f3\":{\"ff1\":1,\"ff2\":2},  "
                + "\"f4\":\"Это тест с не икранированными ковычками \" \"  456465\",  да да да!\""
                + "}, "
                + "\"field2\":true, "
                + "\"filed3\":\"Mozilla/firefox lg4.5\" 1.2123\","
                + "\"field4\":151464565, "
                + "\"field5\":45646.54654 "
                + "}";

        try {
            JsonObject jo = new JsonParser().parseJson(json);
            System.out.println(jo.prettyPrint());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
