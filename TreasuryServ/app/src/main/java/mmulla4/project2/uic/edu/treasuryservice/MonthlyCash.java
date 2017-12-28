package mmulla4.project2.uic.edu.treasuryservice;

/**
 * Created by noman on 05/12/17.
 */

public class MonthlyCash {

    private String date;
    private String year;
    private String month;
    private String day;
    private String weekday;
    private String is_total;
    private String open_today;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getIs_total() {
        return is_total;
    }

    public void setIs_total(String is_total) {
        this.is_total = is_total;
    }

    public String getOpen_today() {
        return open_today;
    }

    public void setOpen_today(String open_today) {
        this.open_today = open_today;
    }

    @Override
    public String toString() {
        return "MonthlyCash{" +
                "date='" + date + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", weekday='" + weekday + '\'' +
                ", is_total='" + is_total + '\'' +
                ", open_today='" + open_today + '\'' +
                '}';
    }
}
