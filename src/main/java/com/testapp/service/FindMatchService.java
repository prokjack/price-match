package com.testapp.service;

import com.testapp.model.Item;
import com.testapp.model.Result;

import java.util.List;

public interface FindMatchService {
    Result findClosestMatch(List<Item> sortedItemList, int targetPrice, int depth);
}
