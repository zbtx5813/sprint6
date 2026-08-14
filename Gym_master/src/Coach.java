public class Coach {
    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coach coach = (Coach) o;

        return (surname.equals(coach.surname) && name.equals(coach.name) && middleName.equals(coach.middleName));
    }

    public int hashCode () {
        return surname.hashCode() + name.hashCode() + middleName.hashCode();
    }
}
