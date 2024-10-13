package org.example.textingquest.filtres;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.getParameterMap().forEach((key, values) -> {
            // Для каждого параметра определяем его тип
            for (String value : values) {
                String type = determineParameterType(value);
                System.out.println(key + " : " + Arrays.toString(values) + " (Тип: " + type + ")");
            }
        });
        filterChain.doFilter(servletRequest,servletResponse);
    }

    // Метод для определения типа параметра
    private String determineParameterType(String value) {
        // Проверяем, является ли строка числом
        if (isInteger(value)) {
            return "Integer";
        }
        // Проверяем, является ли строка логическим значением
        else if (isBoolean(value)) {
            return "Boolean";
        }
        // Проверяем, является ли строка датой (можете изменить формат по необходимости)
        else if (isDate(value)) {
            return "Date";
        }
        // Если ничего из вышеперечисленного, то это строка
        else {
            return "String";
        }
    }

    // Проверка на целое число
    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка на булевый тип
    private boolean isBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

    // Проверка на дату (можете изменить формат по необходимости)
    private boolean isDate(String value) {
        // Пример формата: "yyyy-MM-dd"
        try {
            new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
