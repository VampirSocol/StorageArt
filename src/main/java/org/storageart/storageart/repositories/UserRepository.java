package org.storageart.storageart.repositories;

import org.springframework.data.repository.CrudRepository;
import org.storageart.storageart.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByNickname(String nickname);

    User findById(long id);
}
