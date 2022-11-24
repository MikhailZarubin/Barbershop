package grpc.barbershop.util.scheduler;

public class Date {
    private final int mYear;
    private final int mMonth;
    private final int mDay;

    public Date(int day, int month, int year) {
        mDay = day;
        mMonth = month;
        mYear = year;
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDay() {
        return mDay;
    }

    @Override
    public boolean equals(Object obj) {
        Date equalingDate = (Date) obj;
        return mDay == equalingDate.getDay() && mMonth == equalingDate.getMonth() && mYear == equalingDate.getYear();
    }

    @Override
    public String toString() {
        return String.format("%o.%o.%o", mDay, mMonth, mYear);
    }

    @Override
    public int hashCode() {
        return (String.valueOf(mDay) + String.valueOf(mMonth) + String.valueOf(mYear)).hashCode();
    }
}
