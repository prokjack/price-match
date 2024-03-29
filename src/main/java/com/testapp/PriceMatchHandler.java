package com.testapp;

import com.testapp.model.Item;
import com.testapp.model.Result;
import com.testapp.service.*;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.testapp.Utils.tryParseInt;

@Log
@NoArgsConstructor
class PriceMatchHandler {
    private FileService fileService = new FileService();
    private FindMatchService findMatchTripletService = new FindMatchTripletService();
    private FindMatchService findMatchRecursiveService = new FindMatchRecursiveService();
    private FindMatchService findMatchPairService = new FindMatchPairService();

    /**
     * Price match handler
     * @param args - array of args (1 - path to file, 2 - target price, 3 (optional) - depth, how many items should be in response)
     * @return {@link Result}
     * @throws IOException Can throw exception if file can not be parsed, if incorrect format of numbers (allowed only integers), if not enough arguments. Test-cases described in PriceMatchHandlerTest
     */
    Result handle(String[] args) throws IOException {
        log.fine(String.join(" ", args));
        if (args.length >= 2) {
            List<Item> sortedPricesList = fileService.readFile(args[0]);

            String targetPriceStr = args[1];
            if (tryParseInt(targetPriceStr)) {
                int lengthOfGroup = 2;
                if (args.length >= 3 && tryParseInt(args[2])) {
                    lengthOfGroup = Integer.parseInt(args[2]);
                }

                Result result = getFindMatchService(lengthOfGroup).findClosestMatch(sortedPricesList, Integer.parseInt(targetPriceStr), lengthOfGroup);
                String resultString = getStringResult(result);
                log.info(resultString);
                return result;
            }
            String error = "Can't parse price " + targetPriceStr + ". Correct format: integer";
            log.severe(error);
            throw new IllegalArgumentException(error);
        }
        String error = "There should be at least two arguments: path to file and target price";
        log.severe(error);
        throw new IllegalArgumentException(error);
    }

    private FindMatchService getFindMatchService(int lengthOfGroup) {
        switch (lengthOfGroup) {
            case 2: return findMatchPairService;
            case 3: return findMatchTripletService;
            default: return findMatchRecursiveService;
        }
    }

    String getStringResult(Result result) {
        String resultString;
        if (result.getResultList() == null) {
            resultString = "Not possible";
        } else {
            resultString = result.getResultList().stream().map(Item::toString).collect(Collectors.joining(", "));
        }
        return resultString;
    }
}
