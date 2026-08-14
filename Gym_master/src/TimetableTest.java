import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TimetableTest {
    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> scheduleForMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int sessionAmount = 0;
        if (scheduleForMonday != null) {
            for (TimeOfDay key : scheduleForMonday.keySet()) {
                List<TrainingSession> value = scheduleForMonday.get(key);
                sessionAmount += value.size();
            }
        }
        Assertions.assertEquals(1, sessionAmount);

        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> scheduleForTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(scheduleForTuesday == null || scheduleForTuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> scheduleForMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int sessionAmount = 0;
        if (scheduleForMonday != null) {
            for (TimeOfDay key : scheduleForMonday.keySet()) {
                List<TrainingSession> value = scheduleForMonday.get(key);
                sessionAmount += value.size();
            }
        }
        Assertions.assertEquals(1, sessionAmount);

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> scheduleForThursday =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        sessionAmount = 0;
        if (scheduleForThursday != null) {
            for (TimeOfDay key : scheduleForThursday.keySet()) {
                List<TrainingSession> value = scheduleForThursday.get(key);
                sessionAmount += value.size();
            }
        }
        Assertions.assertEquals(2, sessionAmount);
        Assertions.assertNotNull(scheduleForThursday);
        List<TimeOfDay> actualKeys = new ArrayList<>(scheduleForThursday.navigableKeySet());
        List<TimeOfDay> expectedKeys = List.of(new TimeOfDay(13, 0), new TimeOfDay(20, 0));
        Assertions.assertIterableEquals(expectedKeys, actualKeys);

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> scheduleForTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(scheduleForTuesday == null || scheduleForTuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> sessionFor13Hours = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        Assertions.assertEquals(1, sessionFor13Hours.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> sessionFor14Hours = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        Assertions.assertTrue(sessionFor14Hours == null || sessionFor14Hours.isEmpty());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");
        TrainingSession mondayChildTrainingSession2 = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        TrainingSession thursdayChildTrainingSession2 = new TrainingSession(groupChild, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(9, 0));
        TrainingSession saturdayChildTrainingSession2 = new TrainingSession(groupChild, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(18, 0));
        TrainingSession wednesdayChildTrainingSession2 = new TrainingSession(groupChild, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession2);
        timetable.addNewTrainingSession(thursdayChildTrainingSession2);
        timetable.addNewTrainingSession(saturdayChildTrainingSession2);
        timetable.addNewTrainingSession(wednesdayChildTrainingSession2);

        List<CounterOfTrainings> resultList = timetable.getCountByCoaches();
        List<CounterOfTrainings> expectedList = List.of(new CounterOfTrainings(coach2, 4),
                new CounterOfTrainings(coach, 3));

        Assertions.assertIterableEquals(expectedList, resultList);
    }
}
