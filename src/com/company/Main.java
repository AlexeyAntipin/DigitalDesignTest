package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        if (validate(str))
            System.out.println(parse(str));
        else
            System.out.println("Incorrect data");
    }

    // Проверка на валидность строки
    private static boolean validate(String str) {
        int brackets = 0;
        for(int i = 0; i < str.length(); i++) {
            if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z') || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') ||
                    isNumber(str.charAt(i)) || str.charAt(i) == '[' || str.charAt(i) == ']'))
                return false;
            if (i == 0 && str.charAt(i) == ']')
                return false;
            if (i == str.length() - 1 && (isNumber(str.charAt(i)) || str.charAt(i) == '['))
                return false;
            if (str.charAt(i) == '[')
                brackets++;
            else if (i != str.length() - 1 && isNumber(str.charAt(i)) &&
                    !isNumber(str.charAt(i + 1)) && str.charAt(i + 1) != '[')
                return false;
            else if (str.charAt(i) == ']')
                brackets--;
        }
        return brackets == 0;
    }

    // Проверка на то, является ли символ числом
    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    // Основная функция, которая парсит входящую строку
    private static String parse(String str) {
        // Проверка на содержание в строке символа "["
        if (str.contains("[")) {
            StringBuilder number = new StringBuilder();
            StringBuilder result = new StringBuilder();
            // Разбор строки
            for (int i = 0; i < str.length(); i++) {
                // Если символ - число
                if (isNumber(str.charAt(i)))
                    number.append(str.charAt(i));
                // Если символ - "["
                else if (str.charAt(i) == '[') {
                    // Поиск символа "]", относящегося к текущему "["
                    int index = i, brackets = 1;
                    do {
                        index++;
                        switch (str.charAt(index)) {
                            case '[':
                                brackets++;
                                break;
                            case ']':
                                brackets--;
                                break;
                        }
                    } while (brackets != 0);
                    for (int j = 0; j < Integer.parseInt(number.toString()) - 1; j++)
                        // Рекурсивный вызов функции
                        result.append(parse(str.substring(i + 1, index)));
                    str = new StringBuilder(str).deleteCharAt(index).toString();
                    number = new StringBuilder();
                }
                else
                    result.append(str.charAt(i));
            }
            return result.toString();
        } else
            return str;
    }
}