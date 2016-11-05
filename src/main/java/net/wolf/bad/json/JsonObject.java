/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс, инкапсулирующий в себе JSON объект
 * @author Volchkov Andrey
 */
public class JsonObject extends HashMap<String, Object> implements Map<String, Object> {

    private static final long serialVersionUID = 1L;
    private static final String NEW_LINE = System.lineSeparator();

    public JsonObject() {
    }

    /**
     * Возвращает строку, отображающую JSON-текст текущего JsonObject объекта
     * @return 
     */
    public String prettyPrint() {

        StringBuilder sb = new StringBuilder();

        prittyPrint(sb, this, 0);
        return sb.toString();
    }

    /**
     * Рекурсивно проходися по карте и пишем красивый JSON
     * @param sb буффер, куда аппедним JSON
     * @param jo JsonObject который разбираем 
     * @param depth глубина рекурсии
     */
    private void prittyPrint(StringBuilder sb, JsonObject jo, int depth) {
        String spaces = createSpaces(depth);
                
        sb.append("{");
        Set<Entry<String, Object>> entries = jo.entrySet();
        boolean removeComma = false;
        for (Entry<String, Object> entry : entries) {
            sb.append(NEW_LINE)
                    .append(spaces)
                    .append("\"").append(entry.getKey()).append("\"")
                    .append(": ");
            Object value = entry.getValue();
            if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else if (value instanceof JsonObject) {
                prittyPrint(sb, (JsonObject)value, depth + 1);
            } else {
                sb.append(entry.getValue());
            }
            sb.append(",");
            removeComma = true;
        }
        if (removeComma) {
            sb.delete(sb.length()-1, sb.length());
        }
        sb.append(NEW_LINE);
        if (depth != 0) {            
            sb.append(spaces.substring(0,spaces.length()-2));
        }
        sb.append("}");
    }

    private String createSpaces(final int count) {
        StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < count; i++) {
            sb.append("   ");
        }
        return sb.toString();
    }

}

