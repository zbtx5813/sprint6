public class CounterOfTrainings {
    private Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CounterOfTrainings counter = (CounterOfTrainings) o;

        return (coach.equals(counter.coach) && count == counter.count);
    }

    public int hashCode () {
        return coach.hashCode() + count;
    }
}
