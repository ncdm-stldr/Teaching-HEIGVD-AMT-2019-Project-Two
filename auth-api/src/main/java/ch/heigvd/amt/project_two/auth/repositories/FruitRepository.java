package ch.heigvd.amt.project_two.auth.repositories;

import ch.heigvd.amt.project_two.auth.entities.FruitEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
public interface FruitRepository extends CrudRepository<FruitEntity, Long>{
}
