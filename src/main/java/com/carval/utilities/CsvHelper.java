package com.carval.utilities;

import com.carval.model.Vehicle;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvHelper {
    private static final Logger log = LoggerFactory.getLogger(CsvHelper.class);

    /**
     *
     * @param outputFileName
     * @return Map with key as registration Number with vehicle information
     */
    public static Map<String,Vehicle> getVehicleListFromOutputFile(String outputFileName) {
        String fileName = "src/main/resources/testdata/"+outputFileName+"";
        try {
            List<Vehicle> vehicleList = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(Vehicle.class)
                    .build()
                    .parse();
            return vehicleList.stream().collect(Collectors.toMap(Vehicle::getRegistration, vehicle -> vehicle));
        }catch (FileNotFoundException fe){
            log.error("error Message:"+fe.getMessage());
        }
        return null;
    }

    /**
     *
     * @param filePath
     * @return Converting whole text in a file to String
     */
    public static String convertInputFileAsString(String filePath) {
        StringBuilder vehicleInformationBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> vehicleInformationBuilder.append(s).append("\n"));
        } catch (IOException e) {
            log.error("error Message:"+e.getMessage());
        }
        return vehicleInformationBuilder.toString();
    }

    /**
     *
     * @param inputFileName
     * @return Getting vehicle registration Numbers from input file
     */
    public static List<String> getVehicleRegistrationNumbersFromInputFile(String inputFileName) {
        Pattern regex = Pattern.compile("[A-Z][A-Z]\\s?([0][2-9]|[1-9][0-9])\\s?[A-Z]{3}");
        String fileName = "src/main/resources/testdata/"+inputFileName+"";
        Matcher regexMatcher = regex.matcher(convertInputFileAsString(fileName));
        List<String> registrationNumbers = new ArrayList<>();
        while (regexMatcher.find()) {
            if (regexMatcher.group(0) != null) {
                registrationNumbers.add(regexMatcher.group(0).replaceAll("\\s", ""));
            }
        }
        return registrationNumbers;
    }
}
