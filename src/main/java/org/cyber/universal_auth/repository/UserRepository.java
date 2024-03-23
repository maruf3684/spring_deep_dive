package org.cyber.universal_auth.repository;

import org.cyber.universal_auth.model.user.Status;
import org.cyber.universal_auth.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    List<User> findAllByStatus(Status status);
}
