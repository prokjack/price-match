package com.testapp;

import com.testapp.model.Item;
import com.testapp.model.RecursiveResult;
import com.testapp.service.FileService;
import com.testapp.service.FindMatchRecursiveService;
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
    private FindMatchRecursiveService findMatchRecursiveService = new FindMatchRecursiveService();

    /**
     * Price match handler
     * @param args - array of args (1 - path to file, 2 - target price, 3 (optional) - depth, how many items should be in response)
     * @return {@link RecursiveResult}
     * @throws IOException Can throw exception if file can not be parsed, if incorrect format of numbers (allowed only integers), if not enough arguments. Test-cases described in PriceMatchHandlerTest
     */
    RecursiveResult handle(String[] args) throws IOException {
        log.fine(String.join(" ", args));
        if (args.length >= 2) {
            List<Item> sortedPricesList = fileService.readFile(args[0]);

            String targetPriceStr = args[1];
            if (tryParseInt(targetPriceStr)) {
                int lengthOfGroup = 2;
                if (args.length >= 3 && tryParseInt(args[2])) {
                    lengthOfGroup = Integer.parseInt(args[2]);
                }

                RecursiveResult result = findMatchRecursiveService.findClosestMatch(sortedPricesList, Integer.valueOf(targetPriceStr), lengthOfGroup);
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

    String getStringResult(RecursiveResult result) {
        String resultString;
        if (result.getResultList() == null) {
            resultString = "Not possible";
        } else {
            resultString = result.getResultList().stream().map(Item::toString).collect(Collectors.joining(", "));
        }
        return resultString;
    }
}
