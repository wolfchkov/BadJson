/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wolf.bad.json.parser.util;

import java.util.ArrayList;

/**
 * Так как данные в массиве а не в связном списке, то они локальней для 
 * кеша процессора. По этому реализация данного стека будет быстрее чем 
 * LinkedList, для x86 уж точно ;). Ну и меньше мусорим, так как нет создания Node 
 * связного списка.
 * 
 * @author Volchkov Andrey
 * @param <T> тип значений хранящихся в стеке
 */
public class FastStack <T> {
    
    private final ArrayList<T> stack;
    private int top = -1;

    
    public FastStack(int initSize) {
        stack = new ArrayList<>(initSize);
    }

    public FastStack() {
        this.stack = new ArrayList<>();
    }
    
    /**
     * Пустой ли стек 
     * @return true если пустой, иначе false
     */
    public boolean isEmpty(){
        return top < 0;
    }
    
    /**
     * Возвращает кол-во элементов в стеке
     * @return кол-во элементов в стеке
     */
    public int size() {
        return top + 1;
    }
    
    
    /**
     * Получить значение из вершины стека, не удаляя 
     * @return значение на вершине стека , null если стек пуст 
     */
    public T getFirst() {
        if (!isEmpty()) {
            return stack.get(top);
        }
        return null;
    }
    
    /**
     * Положить в стек
     */    
    public void push(T v) {
        top ++;
        stack.add(top, v);                
    }

    /**
     * Извлечь из вершшины стека, и удалить элемент
     * @return значение на вершине стека, null если стек пуст
     */    
    public T pop() {        
        if (!isEmpty()) {
            T topObj = stack.remove(top);
            top--;
            return topObj;
        }
        return null;
    }
    
    
}

