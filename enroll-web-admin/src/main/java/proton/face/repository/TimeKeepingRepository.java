package proton.face.repository;

import proton.face.entity.People;
import proton.face.entity.Timekeeping;

import java.util.List;

public interface TimeKeepingRepository {
    List<Timekeeping> getListTimekeepingOfPeople(String peopleId, int groupId, String fromDate,
                                                 String toDate, boolean isMorningLate);

    List<Timekeeping> getListTimekeeping();

    List<People> getPeopeIdList(String peopleId);
}
