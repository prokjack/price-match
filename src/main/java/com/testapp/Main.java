package com.testapp;

import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class Main {

    public static void main(String[] args) throws IOException {
        PriceMatchHandler priceMatchHandler = new PriceMatchHandler();
        priceMatchHandler.handle(args);
    }

}
