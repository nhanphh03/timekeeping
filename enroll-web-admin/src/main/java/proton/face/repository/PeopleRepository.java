package proton.face.repository;

import proton.face.entity.People;

import java.util.List;

public interface PeopleRepository {

    List<People> getListPeople();

    void updatePeople(People people, boolean registerFace) throws Exception;

    boolean isExistPeople(String peopleId);

    void updateExistPeople(String peopleId);

    boolean registerPeople(People people);

    boolean registerPeopleRegImage(String peopleId, List<List<String>> imageList);

    boolean registerPeopleRegImage(String peopleId, String imagePath);

    boolean registerPeopleWhitelist(String peopleId, int userId);

    List<People> getPeopleList();

    List<People> searchPeopleList(String peopleId, String fullName ,String mobilePhone);

    List<String> getLast5Detection(String peopleId);

}
