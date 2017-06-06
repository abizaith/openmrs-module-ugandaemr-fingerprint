package org.openmrs.module.ugandaemrfingerprint.core;

import java.io.Serializable;

/**
 * Created by lubwamasamuel on 30/05/2017.
 */
public class Facility implements Serializable {
    String name;
    String uuid;

    public Facility() {
    }

    public Facility(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacilityId() {
        return uuid;
    }

    public void setFacilityId(String uuid) {
        this.uuid = uuid;
    }
}
