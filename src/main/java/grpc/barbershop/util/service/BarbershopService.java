package grpc.barbershop.util.service;

public class BarbershopService {
    private int mId;
    private final String mName;
    private final int mMinutesDuration;
    private final int mRublePrice;

    public BarbershopService(String name, int minutesDuration, int rublePrice) {
        this(0, name, minutesDuration, rublePrice);
    }
    public BarbershopService(int id, String name, int minutesDuration, int rublePrice) {
        mId = id;
        mName = name;
        mMinutesDuration = minutesDuration;
        mRublePrice = rublePrice;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getMinutesDuration() {
        return mMinutesDuration;
    }

    public int getRublePrice() {
        return mRublePrice;
    }
}
