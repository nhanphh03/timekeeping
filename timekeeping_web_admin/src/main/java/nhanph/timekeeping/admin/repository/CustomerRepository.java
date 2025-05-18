package nhanph.timekeeping.admin.repository;

import nhanph.timekeeping.admin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select d.imagePath from Detection d where d.customerCode like %?1%")
    List<String> findListLast5Detection(String peopleId);


//    List<People> getListPeople();
//
//    void updatePeople(People people, boolean registerFace) throws Exception;
//
//    boolean isExistPeople(String peopleId);
//
//    void updateExistPeople(String peopleId);
//
//    boolean registerPeople(People people);
//
//    boolean registerPeopleRegImage(String peopleId, List<List<String>> imageList);
//
//    boolean registerPeopleRegImage(String peopleId, String imagePath);
//
//    boolean registerPeopleWhitelist(String peopleId, int userId);
//
//    List<People> getPeopleList();
//
//    List<People> searchPeopleList(String peopleId, String fullName ,String mobilePhone);
//
//    List<String> getLast5Detection(String peopleId);

}
