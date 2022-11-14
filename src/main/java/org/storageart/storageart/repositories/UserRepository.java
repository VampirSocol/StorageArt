package org.storageart.storageart.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.storageart.storageart.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByNickname(String nickname);

    //Optional<User> findById(long id);
}
