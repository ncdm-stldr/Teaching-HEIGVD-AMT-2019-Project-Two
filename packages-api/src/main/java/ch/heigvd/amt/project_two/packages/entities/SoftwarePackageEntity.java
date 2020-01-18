package ch.heigvd.amt.project_two.packages.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * This Entity aims to represent a software package
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SoftwarePackageEntity implements Serializable {
    private @Id
    String URL;
    private String name;
    private String description;
    private boolean _private;

    @ManyToMany
    @JoinTable(name = "tag")
    private Set<TagLabelEntity> tags;

    public SoftwarePackageEntity(String URL, String name, String description, boolean _private) {
        this.URL = URL;
        this.name = name;
        this.description = description;
        this._private = _private;
    }

}
