package com.testapp.service;

import com.testapp.model.Item;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.testapp.Utils.tryParseInt;

@NoArgsConstructor
@Log
public class FileService {

    public List<Item> readFile(String pathString) throws IOException {
        return Files.lines(Paths.get(pathString))
                .map(this::parseLine).collect(Collectors.toList());
    }

    private Item parseLine(String line) {
        String[] split = line.split(",");
        if (split.length == 2) {
            String name = split[0].trim();
            String priceStr = split[1].trim();
            if (tryParseInt(priceStr)) {
                Integer price = Integer.valueOf(priceStr);
                return new Item(name, price);
            }
        }
        String error = "Can't parse, line is incorrect " + line + ". Please enter each line like {name: String}, {price: Integer}";
        log.severe(error);
        throw new IllegalArgumentException(error);
    }
}
