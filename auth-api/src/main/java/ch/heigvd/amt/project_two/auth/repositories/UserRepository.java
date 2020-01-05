package ch.heigvd.amt.project_two.auth.repositories;

import ch.heigvd.amt.project_two.auth.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
