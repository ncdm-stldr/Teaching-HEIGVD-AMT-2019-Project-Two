package ch.heigvd.amt.project_two.packages.api.endpoints;

import ch.heigvd.amt.project_two.packages.api.TagLabelApi;
import ch.heigvd.amt.project_two.packages.entities.TagLabelEntity;
import ch.heigvd.amt.project_two.packages.repositories.TagLabelRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TagLabelController implements TagLabelApi {

    @Autowired
    TagLabelRepository tagLabelRepository;

    @Override
    public ResponseEntity<Void> doesTagLabelExist(@ApiParam(value = "a name used to qualify a tag", required = true) @PathVariable("tag_label") String tagLabel) {
        if (tagLabelRepository.existsById(tagLabel)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> createTagLabel(@ApiParam(value = "a name used to qualify a tag", required = true) @PathVariable("tag_label") String tagLabel) {
        if (tagLabel.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        tagLabelRepository.save(new TagLabelEntity(tagLabel));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> renameTagLabel(@ApiParam(value = "a name used to qualify a tag", required = true) @PathVariable("tag_label") String tagLabel, @ApiParam(value = "the new tag label", required = true) @Valid @RequestBody String newTagLabel) {
        Optional<TagLabelEntity> otle = tagLabelRepository.findById(tagLabel);
        if (!otle.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TagLabelEntity tle = otle.get();
            tle.setTagLabel(newTagLabel);
            tagLabelRepository.save(tle);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

//    @Override
//    public ResponseEntity<Void> removeTagLabelsMerge(@ApiParam(value = "a name used to qualify a tag",required=true) @PathVariable("tag_label") String tagLabel) {
//
//    }
//
//    @Override
//    public ResponseEntity<Void> addTagLabelsMerge(@ApiParam(value = "a name used to qualify a tag",required=true) @PathVariable("tag_label") String tagLabel) {
//
//    }


}
