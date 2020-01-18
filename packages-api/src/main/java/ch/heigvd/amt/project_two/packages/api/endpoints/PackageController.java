package ch.heigvd.amt.project_two.packages.api.endpoints;

import ch.heigvd.amt.project_two.packages.api.PackageApi;
import ch.heigvd.amt.project_two.packages.api.model.SoftwarePackage;
import ch.heigvd.amt.project_two.packages.entities.SoftwarePackageEntity;
import ch.heigvd.amt.project_two.packages.repositories.SoftwarePackagesRepository;
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
import java.util.Optional;

@Controller
public class PackageController implements PackageApi {

    private static UrlValidator urlValidator = new UrlValidator();

    @Autowired
    private SoftwarePackagesRepository softwarePackagesRepository;

    @Override
    public ResponseEntity<String>
    addPackage(@ApiParam(value = "", required = true) @Valid @RequestBody SoftwarePackage _package) {

        // we check if request is malformed
        String problem = findMalformedPackageProblem(_package);
        if(problem != null) return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);


        // we save the new SoftwarePackage in the database
        SoftwarePackageEntity spe = SoftwarePackageToSoftwarePackageEntity(_package);
        softwarePackagesRepository.save(spe);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    public ResponseEntity<Void> addTag(@ApiParam(value = "an URL in urlencoded form", required = true) @PathVariable("URL") String URL,
//                                       @ApiParam(value = "another tag label", required = true) @Valid @RequestBody String tagLabel) {
//
//    }

    @Override
    public ResponseEntity<String> editPackage(@ApiParam(value = "" ,required=true )  @Valid @RequestBody SoftwarePackage _package) {
        // we check if request is malformed
        String problem = findMalformedPackageProblem(_package);
        if(problem != null) return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);

        Optional<SoftwarePackageEntity> optionalSoftwarePackageEntity = softwarePackagesRepository.findById(_package.getUrl());
        if(!optionalSoftwarePackageEntity.isPresent()) {
            return new ResponseEntity<>("The package to be updated does not exist", HttpStatus.NOT_FOUND);
        } else {
            SoftwarePackageEntity spe = SoftwarePackageToSoftwarePackageEntity(_package);
            softwarePackagesRepository.save(spe);
            return new ResponseEntity<>("The package was sucessfully updated", HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<SoftwarePackage> getPackage(@ApiParam(value = "an URL in urlencoded form",required=true) @PathVariable("URL") String URL) {
        String url2 = urlEncodedUrlToUrl(URL);
        Optional<SoftwarePackageEntity> ospe = softwarePackagesRepository.findById(url2);
        if(!ospe.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(SoftwarePackageEntityToSoftwarePackage(ospe.get()), HttpStatus.OK);
        }
    }

//    public ResponseEntity<Void> getTags() {
//
//    }
//
//    public ResponseEntity<Void> removeTag(@ApiParam(value = "an URL in urlencoded form", required = true) @PathVariable("URL") String URL,
//                                          @ApiParam(value = "another tag label", required = true) @Valid @RequestBody String tagLabel) {
//
//    }

    private static String findMalformedPackageProblem(SoftwarePackage _package) {
        if(!urlValidator.isValid(_package.getUrl())) return "Invalid URL";
        if(_package.getName().isEmpty()) return "Please provide a name for the package";
        return null;
    }

    private static SoftwarePackageEntity SoftwarePackageToSoftwarePackageEntity(SoftwarePackage sp) {
        SoftwarePackageEntity spe = new SoftwarePackageEntity();
        spe.setURL(sp.getUrl());
        spe.setName(sp.getName());
        spe.setDescription(sp.getDescription());
        spe.set_private(sp.getPrivate());
        return spe;
    }

    private static SoftwarePackage SoftwarePackageEntityToSoftwarePackage(SoftwarePackageEntity spe) {
        SoftwarePackage sp = new SoftwarePackage();
        sp.setUrl(spe.getURL());
        sp.setName(spe.getName());
        sp.setDescription(spe.getDescription());
        sp.setPrivate(spe.is_private());
        return sp;
    }

    private static String urlEncodedUrlToUrl(String urlEncodedUrl) {
        try {
            String result = java.net.URLDecoder.decode(urlEncodedUrl, StandardCharsets.UTF_8.name());
            System.out.println(result);
            return result;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


}
