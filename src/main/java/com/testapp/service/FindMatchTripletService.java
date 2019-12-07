package com.testapp.service;

import com.testapp.model.Item;
import com.testapp.model.Result;

import java.util.Arrays;
import java.util.List;

public class FindMatchTripletService implements FindMatchService {

    public Result findClosestMatch(List<Item> sortedItemList, int targetPrice, int depth) {
        return findMatchTriplet(sortedItemList, targetPrice);
    }

    /**
     * Optimized solution for depth == 2. Complexity O(n)
     * Search of closest match to target price
     *
     * @param sortedItemList Input list of Items
     * @param targetPrice    target price
     * @return {@link Result}
     */
    private Result findMatchTriplet(final List<Item> sortedItemList, final Integer targetPrice) {
        int diff = Integer.MAX_VALUE;
        // result indexes
        int outerLoopResult = 0;
        int resultStart = 0;
        int resultEnd = 0;
        for (int i = 0; i < sortedItemList.size() - 2; i++) {

            int startIndex = i + 1;
            int endIndex = sortedItemList.size() - 1;
            Integer outerLoopPrice = sortedItemList.get(i).getPrice();
            // While there could be more pairs to check
            while (startIndex < endIndex) {

                Integer startPrice = sortedItemList.get(startIndex).getPrice();
                Integer endPrice = sortedItemList.get(endIndex).getPrice();
                int currentDiff = outerLoopPrice + startPrice + endPrice - targetPrice;
                int absOfCurrentDiff = Math.abs(currentDiff);
                // Check if current items are closer to targetPrice then the closest items before
                if (absOfCurrentDiff < diff && currentDiff <= 0) {
                    resultStart = startIndex;
                    resultEnd = endIndex;
                    outerLoopResult = i;
                    diff = absOfCurrentDiff;
                }

                // If items price more than target -> move end index down. Opposite - move start index up
                if (outerLoopPrice + startPrice + endPrice > targetPrice) {
                    endIndex--;
                } else {
                    startIndex++;
                }

            }
        }
        if (diff == Integer.MAX_VALUE) {
            return new Result(null, null);
        }
        return new Result(Arrays.asList(sortedItemList.get(outerLoopResult), sortedItemList.get(resultStart), sortedItemList.get(resultEnd)), 0);
    }
}
