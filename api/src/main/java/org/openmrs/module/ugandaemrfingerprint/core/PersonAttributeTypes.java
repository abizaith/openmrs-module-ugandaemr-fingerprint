package org.openmrs.module.ugandaemrfingerprint.core;

import org.openmrs.PersonAttributeType;
import org.openmrs.module.metadatadeploy.descriptor.PersonAttributeTypeDescriptor;

import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.*;
import static org.openmrs.module.ugandaemrfingerprint.core.FingerPrintConstant.FINGER_PRINT_ATTRIBUTE_UUID;

/**
 * Constants for all defined person attribute types
 * <p/>
 * Created by Lubwama Samuel on 21/02/2016.
 */
public class PersonAttributeTypes {

    public static PersonAttributeTypeDescriptor FINGER_PRINT = new PersonAttributeTypeDescriptor() {
        @Override
        public double sortWeight() {
            return 0;
        }

        @Override
        public Class<?> format() {
            return PersonAttributeType.class;
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
