package org.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class Test {

    public static void main(String [] args) throws IOException {
//        System.out.println("Hello");
//        JsonNode jsonNode = new ObjectMapper().readTree(new File("src/main/java/org/example/controller/test.json"));
//        System.out.println(jsonNode.get("gggg").asText(null));

        String countryCode = "IN";
        try {
            System.out.println(Arrays.asList(Locale.getISOCountries()).contains(countryCode));
            System.out.println(Arrays.stream(Locale.getISOCountries()).collect(Collectors.joining(",")));

        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("error");


        }

    }
}
