package ch.heigvd.amt.project_two.packages.repositories;

import ch.heigvd.amt.project_two.packages.entities.TagLabelEntity;
import org.springframework.data.repository.CrudRepository;

public interface TagLabelRepository extends CrudRepository<TagLabelEntity, String> {
}
