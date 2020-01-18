package ch.heigvd.amt.project_two.packages.api.endpoints;

import ch.heigvd.amt.project_two.packages.api.PackageApi;
import ch.heigvd.amt.project_two.packages.api.model.SoftwarePackage;
import ch.heigvd.amt.project_two.packages.entities.SoftwarePackageEntity;
import ch.heigvd.amt.project_two.packages.entities.TagLabelEntity;
import ch.heigvd.amt.project_two.packages.repositories.SoftwarePackagesRepository;
import ch.heigvd.amt.project_two.packages.repositories.TagLabelRepository;
import io.swagger.annotations.ApiParam;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class PackageController implements PackageApi {

    private static UrlValidator urlValidator = new UrlValidator();

    @Autowired
    private SoftwarePackagesRepository softwarePackagesRepository;

    @Autowired
    private TagLabelRepository tagLabelRepository;

    @Override
    public ResponseEntity<String> addPackage(@ApiParam(value = "" ,required=true )  @Valid @RequestBody SoftwarePackage _package) {

        // we check if request is malformed
        String problem = findMalformedPackageProblem(_package);
        if(problem != null) return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);


        // we save the new SoftwarePackage in the database
        SoftwarePackageEntity spe = SoftwarePackageToSoftwarePackageEntity(_package);
        softwarePackagesRepository.save(spe);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> editPackage(@ApiParam(value = "" ,required=true )  @Valid @RequestBody SoftwarePackage _package) {
        // we check if request is malformed
        String problem = findMalformedPackageProblem(_package);
        if(problem != null) return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);

        Optional<SoftwarePackageEntity> optionalSoftwarePackageEntity = softwarePackagesRepository.findById(_package.getId());
        if(!optionalSoftwarePackageEntity.isPresent()) {
            return new ResponseEntity<>("The package to be updated does not exist", HttpStatus.NOT_FOUND);
        } else {
            SoftwarePackageEntity spe = SoftwarePackageToSoftwarePackageEntity(_package);
            softwarePackagesRepository.save(spe);
            return new ResponseEntity<>("The package was sucessfully updated", HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<SoftwarePackage> getPackage(@ApiParam(value = "an id used to identify and eventually locate the package",required=true) @PathVariable("id") String id) {
        Optional<SoftwarePackageEntity> ospe = softwarePackagesRepository.findById(id);
        if(!ospe.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(SoftwarePackageEntityToSoftwarePackage(ospe.get()), HttpStatus.OK);
        }
    }



    @Override
    public ResponseEntity<Void> addTag(@ApiParam(value = "an id used to identify and eventually locate the package",required=true) @PathVariable("id") String id,
                                       @ApiParam(value = "another tag label" ,required=true )  @Valid @RequestBody String tagLabel) {
        // check if software package exists
        Optional<SoftwarePackageEntity> ospe = softwarePackagesRepository.findById(id);
        if(!ospe.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // check if tag_label exists
        Optional<TagLabelEntity> otl = tagLabelRepository.findById(tagLabel);
        if(!otl.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SoftwarePackageEntity spe = ospe.get();
        TagLabelEntity tle = otl.get();

        spe.getTags().add(tle);
        softwarePackagesRepository.saveAndFlush(spe);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeTag(@ApiParam(value = "an id used to identify and eventually locate the package",required=true) @PathVariable("id") String id,@ApiParam(value = "another tag label" ,required=true )  @Valid @RequestBody String tagLabel) {
        // check if software package exists
        Optional<SoftwarePackageEntity> ospe = softwarePackagesRepository.findById(id);
        if(!ospe.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // check if tag_label exists
        Optional<TagLabelEntity> otl = tagLabelRepository.findById(tagLabel);
        if(!otl.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SoftwarePackageEntity spe = ospe.get();
        TagLabelEntity tle = otl.get();

        spe.getTags().remove(tle);

        softwarePackagesRepository.saveAndFlush(spe);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> getTags(@ApiParam(value = "an id used to identify and eventually locate the package",required=true) @PathVariable("id") String id) {
        // check if software package exists
        Optional<SoftwarePackageEntity> ospe = softwarePackagesRepository.findById(id);
        if(!ospe.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SoftwarePackageEntity spe = ospe.get();
        StringBuilder sb = new StringBuilder();
        Iterator<TagLabelEntity> it = spe.getTags().iterator();
        if(it.hasNext()) sb.append(it.next().getTagLabel());
        while(it.hasNext()) {
            sb.append(", ").append(it.next().getTagLabel());
        }

        return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
    }





        private static String findMalformedPackageProblem(SoftwarePackage _package) {
        if(_package.getId().isEmpty()) return "Invalid id";
        if(_package.getName().isEmpty()) return "Please provide a name for the package";
        return null;
    }

    private static SoftwarePackageEntity SoftwarePackageToSoftwarePackageEntity(SoftwarePackage sp) {
        SoftwarePackageEntity spe = new SoftwarePackageEntity();
        spe.setId(sp.getId());
        spe.setName(sp.getName());
        spe.setDescription(sp.getDescription());
        spe.set_private(sp.getPrivate());
        return spe;
    }

    private static SoftwarePackage SoftwarePackageEntityToSoftwarePackage(SoftwarePackageEntity spe) {
        SoftwarePackage sp = new SoftwarePackage();
        sp.setId(spe.getId());
        sp.setName(spe.getName());
        sp.setDescription(spe.getDescription());
        sp.setPrivate(spe.is_private());
        return sp;
    }

}
