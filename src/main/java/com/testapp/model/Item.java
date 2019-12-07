package com.testapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Item {
    private final String name;
    private final Integer price;

    @Override
    public String toString() {
        return name + " " + price;
    }
}
