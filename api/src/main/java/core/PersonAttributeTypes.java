package core;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.module.metadatadeploy.descriptor.PersonAttributeTypeDescriptor;

import static core.FingerPrintConstant.*;
import static core.FingerPrintConstant.FINGER_PRINT_ATTRIBUTE_UUID;

/**
 * Constants for all defined person attiribute types
 * <p/>
 * Created by ssmusoke on 09/01/2016.
 */
public class PersonAttributeTypes {

    public static PersonAttributeTypeDescriptor FINGER_PRINT = new PersonAttributeTypeDescriptor() {
        @Override
        public double sortWeight() {
            return 0;
        }

        @Override
        public Class<?> format() {
            return Concept.class;
        }

        @Override
        public String name() {
            return FINGER_PRINT_ATTRIBUTE_NAME;
        }

        @Override
        public String description() {
            return FINGER_PRINT_ATTRIBUTE_DESCRIPTION;
        }

        public String uuid() {
            return FINGER_PRINT_ATTRIBUTE_UUID;
        }
    };
}
