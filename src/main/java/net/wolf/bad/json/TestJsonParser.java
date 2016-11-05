/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;
import net.wolf.bad.json.parser.JsonParser;
import net.wolf.bad.json.parser.ParserException;

/**
 * Тестируем, что получилось :)
 * @author Volchkov Andrey
 */
public class TestJsonParser {

    
    public static void main(String [] params) throws IOException, ParserException {
        JsonParser jp = new JsonParser();
        
        String js = "{\"id\": \"123\",\"ua\":\"Mozilla/firefox lg4.5\" 1.2123\"}";
        System.out.println("Парсим json из задания => " + js);
        JsonObject jsonObject = jp.parseJson(js);
        System.out.println("Получим поле \"id\" => " + jsonObject.get("id"));
        System.out.println("Получим поле \"ua\" => " + jsonObject.get("ua"));
        
        System.out.println();
        System.out.println();
        System.out.println("Пробежимся по примерам JsonSamples.txt... ");
        try (BufferedReader br = new BufferedReader( 
                new InputStreamReader(TestJsonParser.class.getResourceAsStream("JsonSamples.txt"), Charset.forName("UTF-8")));) {
            String all = br.lines().collect(Collectors.joining("\n"));
            String[] jsons = all.split("\\|\\|");
            for (String json : jsons) {
                System.out.println("Парсим json => " + json);
                System.out.println();
                System.out.println("Отображаем распарсенный json => " + jp.parseJson(json).prettyPrint());
                System.out.println();
                System.out.println();
            }
        }
    }
}
