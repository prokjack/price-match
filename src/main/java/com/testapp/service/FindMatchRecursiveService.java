package com.testapp.service;

import com.testapp.model.Item;
import com.testapp.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FindMatchRecursiveService implements FindMatchService {
    private List<Item> result;
    private int resultPrice = 0;

    public Result findClosestMatch(List<Item> sortedItemList, int targetPrice, int depth) {
        return findMatchRecursive(sortedItemList, targetPrice, 0, new ArrayList<>(), depth);
    }

    /**
     * Simple solution for all depth values. Complexity O(n^depth)
     * Recursive search of closest match to target price
     *
     * @param sortedItemList Input list of Items
     * @param targetPrice    target price
     * @param startIndex     from this index new iteration will start (to avoid duplicates in response)
     * @param prevItems      already added Items in this iteration
     * @param depth          restriction in depth
     * @return {@link Result}
     */
    private Result findMatchRecursive(final List<Item> sortedItemList, final Integer targetPrice, int startIndex, final List<Item> prevItems, final Integer depth) {
        for (int j = startIndex; j < sortedItemList.size(); j++) {
            Item nextItem = sortedItemList.get(j);

            List<Item> candidateList = new ArrayList<>(prevItems);
            candidateList.add(nextItem);
            // if depth is more than already added Items - call recursive
            if (depth > prevItems.size() + 1) {
                Result matchRecursive = findMatchRecursive(sortedItemList, targetPrice, j + 1, candidateList, depth);
                if (Objects.equals(matchRecursive.getResultPrice(), targetPrice)) {
                    return matchRecursive;
                }
            } else {
                int combinedPriceCandidate = candidateList.stream().map(Item::getPrice).mapToInt(Integer::intValue).sum();
                if (combinedPriceCandidate == targetPrice) {
                    return new Result(candidateList, combinedPriceCandidate);
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
        return new Result(result, resultPrice);
    }
}
