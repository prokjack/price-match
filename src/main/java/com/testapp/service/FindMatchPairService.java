package com.testapp.service;

import com.testapp.model.Item;
import com.testapp.model.Result;

import java.util.Arrays;
import java.util.List;

public class FindMatchPairService implements FindMatchService {

    public Result findClosestMatch(List<Item> sortedItemList, int targetPrice, int depth) {
        return findMatchPair(sortedItemList, targetPrice);
    }

    /**
     * Optimized solution for depth == 2. Complexity O(n)
     * Search of closest match to target price
     *
     * @param sortedItemList Input list of Items
     * @param targetPrice    target price
     * @return {@link Result}
     */
    private Result findMatchPair(final List<Item> sortedItemList, final Integer targetPrice) {
        // result indexes
        int resultStart = 0;
        int resultEnd = 0;

        // initial indexes
        int startIndex = 0;
        int endIndex = sortedItemList.size() - 1;
        int diff = Integer.MAX_VALUE;

        while (endIndex > startIndex) {
            Integer startPrice = sortedItemList.get(startIndex).getPrice();
            Integer endPrice = sortedItemList.get(endIndex).getPrice();
            int currentDiff = startPrice + endPrice - targetPrice;
            int absOfCurrentDiff = Math.abs(currentDiff);
            // Check if current items are closer to targetPrice then the closest items before
            if (absOfCurrentDiff < diff && currentDiff < 0) {
                resultStart = startIndex;
                resultEnd = endIndex;
                diff = absOfCurrentDiff;
            }

            // If items price more than target -> move end index down. Opposite - move start index up
            if (startPrice + endPrice > targetPrice) {
                endIndex--;
            } else {
                startIndex++;
            }
        }
        if (diff == Integer.MAX_VALUE) {
            return new Result(null, null);
        }
        return new Result(Arrays.asList(sortedItemList.get(resultStart), sortedItemList.get(resultEnd)), 0);
    }
}
