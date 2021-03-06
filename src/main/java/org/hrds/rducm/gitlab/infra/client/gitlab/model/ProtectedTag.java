package org.hrds.rducm.gitlab.infra.client.gitlab.model;

import java.util.List;

import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.utils.JacksonJson;

public class ProtectedTag {

    public static class CreateAccessLevel {

        private org.gitlab4j.api.models.AccessLevel access_level;
        private String accessLevelDescription;

        public org.gitlab4j.api.models.AccessLevel getAccess_level() {
            return access_level;
        }

        public void setAccess_level(AccessLevel access_level) {
            this.access_level = access_level;
        }

        public String getAccessLevelDescription() {
            return accessLevelDescription;
        }

        public void setAccessLevelDescription(String accessLevelDescription) {
            this.accessLevelDescription = accessLevelDescription;
        }
    }

    private String name;
    private List<CreateAccessLevel> createAccessLevels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CreateAccessLevel> getCreateAccessLevels() {
        return createAccessLevels;
    }

    public void setCreateAccessLevels(List<CreateAccessLevel> createAccessLevels) {
        this.createAccessLevels = createAccessLevels;
    }

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }
}
