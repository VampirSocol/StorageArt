package org.storageart.storageart.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.storageart.storageart.entities.Art;
import org.storageart.storageart.entities.User;

import java.util.List;

@Repository
public interface ArtRepository extends CrudRepository<Art, Long> {

    List<Art> findByUser(User user);

}
