package org.openmrs.module.ugandaemrfingerprint.core;


import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

/**
 * Installs the most common metadata
 * <p/>
 * Created Lubwama Samuel
 */
@Component
public class FingerPrintMetadataBundle extends AbstractMetadataBundle {

    public FingerPrintMetadataBundle() {
    }

    /**
     * @see #install()
     */

    public void install() throws Exception {
        // install the Person Attribute Type For Finger Print
        log.info("Installing Finger Print Person Attribute");
        install(PersonAttributeTypes.FINGER_PRINT);
    }
}
