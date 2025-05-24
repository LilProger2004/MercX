package com.diploma.MrcX.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FormatParser {


    public static String urlEncodedToJson(String urlEncodedString) throws JsonProcessingException {

        // Парсим данные в список NameValuePair
        List<NameValuePair> params = URLEncodedUtils.parse(urlEncodedString, StandardCharsets.UTF_8);

        // Преобразуем в карту
        Map<String, String> map = params.stream()
                .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        // Преобразуем в JSON
        return new ObjectMapper().writeValueAsString(map);
    }
}
