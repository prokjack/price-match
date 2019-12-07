package com.testapp.service;

import com.testapp.model.Item;
import com.testapp.model.RecursiveResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FindMatchRecursiveService {
    private List<Item> result;
    private int resultPrice = 0;

    public RecursiveResult findClosestMatch(List<Item> sortedItemList, Integer targetPrice, Integer depth) {
            return findMatchRecursive(sortedItemList, targetPrice, 0, new ArrayList<>(), depth);
    }

    /**
     * Recursive search of closest match to target price
     * @param sortedItemList Input list of Items
     * @param targetPrice target price
     * @param startIndex from this index new iteration will start (to avoid duplicates in response)
     * @param prevItems already added Items in this iteration
     * @param depth restriction in depth
     * @return {@link RecursiveResult}
     */
    private RecursiveResult findMatchRecursive(final List<Item> sortedItemList, final Integer targetPrice, int startIndex, final List<Item> prevItems, final Integer depth) {
        for (int j = startIndex; j < sortedItemList.size(); j++) {
            Item nextItem = sortedItemList.get(j);

            List<Item> candidateList = new ArrayList<>(prevItems);
            candidateList.add(nextItem);
            // if depth is more than already added Items - call recursive
            if (depth > prevItems.size() + 1) {
                RecursiveResult matchRecursive = findMatchRecursive(sortedItemList, targetPrice, j + 1, candidateList, depth);
                if (Objects.equals(matchRecursive.getResultPrice(), targetPrice)) {
                    return matchRecursive;
                }
            } else {
                int combinedPriceCandidate = candidateList.stream().map(Item::getPrice).mapToInt(Integer::intValue).sum();
                if (combinedPriceCandidate == targetPrice) {
                    return new RecursiveResult(candidateList, combinedPriceCandidate);
                }
                if (combinedPriceCandidate > targetPrice) {
                    break;
                }
                if (combinedPriceCandidate > resultPrice) {
                    resultPrice = combinedPriceCandidate;
                    result = candidateList;
                }
            }
        }
        return new RecursiveResult(result, resultPrice);
    }
}
