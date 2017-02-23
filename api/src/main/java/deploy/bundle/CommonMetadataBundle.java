package deploy.bundle;


import core.PersonAttributeTypes;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

/**
 * Installs the most common metadata
 * <p/>
 * Created by ssmusoke on 06/01/2016.
 */
@Component
public class CommonMetadataBundle extends AbstractMetadataBundle {

    /**
     * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
     */
    public void install() throws Exception {
        // install the Person Attribute Type For Finger Print
        log.info("Installing Finger Print Person Attribute");
        install(PersonAttributeTypes.FINGER_PRINT);


    }
}
