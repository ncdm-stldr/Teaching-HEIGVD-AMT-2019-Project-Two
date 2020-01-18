package ch.heigvd.amt.project_two.packages.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * This entity represent a label that will be used to tag something
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class TagLabelEntity implements Serializable {
    private @Id String tagLabel;

    @ManyToMany(mappedBy = "tags")
    private Set<SoftwarePackageEntity> taggedPackages;

    public TagLabelEntity(String tagLabel) {
        this.tagLabel = tagLabel;
    }
}
