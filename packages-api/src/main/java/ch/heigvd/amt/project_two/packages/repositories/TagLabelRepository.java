package ch.heigvd.amt.project_two.packages.repositories;

import ch.heigvd.amt.project_two.packages.entities.TagLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagLabelRepository extends JpaRepository<TagLabelEntity, String> {
}
