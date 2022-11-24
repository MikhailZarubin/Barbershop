package grpc.barbershop.service.common;

import com.google.gson.Gson;
import grpc.barbershop.util.InitializeBarbershopData;
import grpc.barbershop.util.scheduler.Date;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class BarbershopManager {
    private final HashMap<Date, TreeSet<Integer>> mFreeIdsByDate = new HashMap<>();

    public BarbershopManager() {}

    public int getFreeIdByDate(Date date) {
        TreeSet<Integer> freeIds = mFreeIdsByDate.get(date) == null ? initializeByDate(date) : mFreeIdsByDate.get(date);
        return freeIds.first();
    }

    public void setFreeIdByDate(Date date, int freeId) {
        mFreeIdsByDate.get(date).add(freeId);
    }

    public void removeFreeIdByDate(Date date, int freeId) {
        mFreeIdsByDate.get(date).remove(freeId);
    }

    private TreeSet<Integer> initializeByDate(Date date) {
        TreeSet<Integer> freeIds = new TreeSet<>(IntStream.range(0, InitializeBarbershopData.MAX_APPOINTMENT_COUNT).boxed().toList());
        mFreeIdsByDate.put(date, freeIds);
        return freeIds;
    }

    public static String parseGlobalAppointmentId(Date date, int localId) {
        Gson gson = new Gson();
        return gson.toJson(new GlobalAppointmentId(date, localId));
    }

    public static GlobalAppointmentId unparseLocalAppointmentId(String globalAppointmentId) {
        Gson gson = new Gson();
        return gson.fromJson(globalAppointmentId, GlobalAppointmentId.class);
    }

    static class GlobalAppointmentId {
        private final Date mDate;
        private final int mLocalId;

        public GlobalAppointmentId(Date date, int localId) {
            mDate = date;
            mLocalId = localId;
        }

        public Date getDate() {
            return mDate;
        }

        public int getLocalId() {
            return mLocalId;
        }
    }
}
