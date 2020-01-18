package ch.heigvd.amt.project_two.packages.repositories;

import ch.heigvd.amt.project_two.packages.entities.SoftwarePackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwarePackagesRepository extends JpaRepository<SoftwarePackageEntity, String> {
}
