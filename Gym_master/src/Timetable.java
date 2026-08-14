import java.util.*;

public class Timetable {
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    Comparator<TimeOfDay> timeOfDayComparator = new Comparator<TimeOfDay>() {
        @Override
        public int compare(TimeOfDay o1, TimeOfDay o2) {
            if (o1.getHours() != o2.getHours()) {
                return Integer.compare(o1.getHours(), o2.getHours());
            }
            return Integer.compare(o1.getMinutes(), o2.getMinutes());
        }
    };

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (trainingSession == null) {
            return;
        }

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);
        if (daySchedule == null) {
            daySchedule = new TreeMap<>(timeOfDayComparator);
            timetable.put(day, daySchedule);
        }

        List<TrainingSession> sessionsAtTime = daySchedule.get(time);
        if (sessionsAtTime == null) {
            sessionsAtTime = new ArrayList<>();
            daySchedule.put(time, sessionsAtTime);
        }

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null) {
            return null;
        }
        return daySchedule.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachWorkload = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachWorkload.put(coach, coachWorkload.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> resultList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachWorkload.entrySet()) {
            resultList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        resultList.sort(new Comparator<>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return Integer.compare(o2.getCount(), o1.getCount());
            }
        });

        return resultList;
    }
}
