package com.testapp.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class RecursiveResult {
    private final List<Item> resultList;
    private final Integer resultPrice;
}
