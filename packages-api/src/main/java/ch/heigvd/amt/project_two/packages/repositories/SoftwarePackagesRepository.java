package ch.heigvd.amt.project_two.packages.repositories;

import ch.heigvd.amt.project_two.packages.entities.SoftwarePackageEntity;
import org.springframework.data.repository.CrudRepository;

public interface SoftwarePackagesRepository extends CrudRepository<SoftwarePackageEntity, String> {
}
