package proton.face.repository;

import proton.face.entity.Role;
import proton.face.entity.User;

public interface LoginRepository {
    User validate(String username, String password) ;
    Role findById(int id);
}
