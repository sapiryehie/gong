package io.gong;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CsvUtils {
    public HashMap<String,ArrayList<FullSlot>> parseCsv(String fileName) throws Exception {
        HashMap<String,ArrayList<FullSlot>> meetingInfos = new HashMap();
        URL url = this.getClass().getClassLoader().getResource(fileName);
        Path pathToFile = Paths.get(url.getPath());
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                String person = attributes[0].toLowerCase(Locale.ROOT);
                if (meetingInfos.containsKey(person)) {
                    ArrayList<FullSlot> fullSlots = meetingInfos.get(person);
                    fullSlots.add(FullSlot.builder().startTime(convertHourToMinutes(attributes[2])).endTime(convertHourToMinutes(attributes[3])).build());
                    meetingInfos.put(person, fullSlots);
                } else {
                    ArrayList<FullSlot> fullSlots = new ArrayList<>();
                    fullSlots.add(FullSlot.builder().startTime(convertHourToMinutes(attributes[2])).endTime(convertHourToMinutes(attributes[3])).build());
                    meetingInfos.put(person, fullSlots);
                }
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return meetingInfos;
    }

    private static int convertHourToMinutes(String timeInHour) throws Exception {
        String[] timeParts = timeInHour.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        if ( 7 <= hour && hour <= 19 && 0 <= minutes && minutes <= 59)
            return hour * 60 + minutes;
        else throw new Exception("time format is incorrect: " + timeInHour);

    }
}
