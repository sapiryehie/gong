package io.gong;
;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class Logic {
    private static final String FILE_PATH = "io/gong/calendar.csv";
    private static final int START_TIME_7 = 420;
    private static final int END_TIME_19 = 1140;
    private static CsvUtils csvUtils = new CsvUtils();

    public static List<LocalTime> findAvailableSlots(List<String> personList, Duration eventDuration) throws Exception {
        HashMap<String,ArrayList<FullSlot>> meetingInfos = csvUtils.parseCsv(FILE_PATH);
        Set<FullSlot> allMeetings = new HashSet<>();
        Map<Integer,Integer> minutesMap = new HashMap();
        for (String person : personList) {
            String loweCasePerson = person.toLowerCase(Locale.ROOT);
            if (!meetingInfos.containsKey(loweCasePerson)) {
                throw new Exception("person: " + person + ", does not exist");
            }
            allMeetings.addAll(meetingInfos.get(loweCasePerson));

            for (FullSlot fullSlot : meetingInfos.get(loweCasePerson)) {
                if (!minutesMap.containsKey(fullSlot.getStartTime())) {
                    minutesMap.put(fullSlot.getStartTime(), fullSlot.getEndTime());
                } else {
                    int endTime = minutesMap.get(fullSlot.getStartTime());
                    if (endTime < fullSlot.getEndTime()) {
                        minutesMap.put(fullSlot.getStartTime(), fullSlot.getEndTime());
                    }
                }
            }
        }
        int intDuration = (int) eventDuration.toMinutes();
        List<LocalTime> localTimes = searchForEmptySlot(intDuration, minutesMap);

        return localTimes;
    }

    private static List<LocalTime> searchForEmptySlot(int intDuration, Map<Integer, Integer> minutesMap) {
        int startTime = START_TIME_7;
        List<LocalTime> localTimes = new ArrayList<>();
        while (startTime < END_TIME_19 - intDuration) {
            if (minutesMap.containsKey(startTime)) {
                startTime = minutesMap.get(startTime);
                continue;
            }
            int limit = startTime + intDuration;
            if (limit < END_TIME_19) {
                for (int i = startTime; i < limit; i++) {
                    if (minutesMap.containsKey(i)) {
                        startTime = minutesMap.get(i);
                        break;
                    }
                }
            }

            localTimes.add(LocalTime.of(startTime / 60, startTime % 60));
            startTime += intDuration;
        }
        return localTimes;
    }

}
