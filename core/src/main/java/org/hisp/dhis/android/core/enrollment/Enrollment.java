/*
 * Copyright (c) 2016, University of Oslo
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.android.core.enrollment;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.core.common.Coordinates;

import java.util.Date;

@AutoValue
public abstract class Enrollment {
    private static final String UID = "enrollment";
    private static final String CREATED = "created";
    private static final String LAST_UPDATED = "lastUpdated";
    private static final String ORGANISATION_UNIT = "orgUnit";
    private static final String PROGRAM = "program";
    private static final String DATE_OF_ENROLLMENT = "enrollmentDate";
    private static final String DATE_OF_INCIDENT = "incidentDate";
    private static final String FOLLOW_UP = "followup";
    private static final String ENROLLMENT_STATUS = "status";
    private static final String TRACKED_ENTITY_INSTANCE = "trackedEntityInstance";
    private static final String COORDINATE = "coordinate";

    @JsonProperty(UID)
    public abstract String uid();

    @Nullable
    @JsonProperty(CREATED)
    public abstract Date created();

    @Nullable
    @JsonProperty(LAST_UPDATED)
    public abstract Date lastUpdated();

    @Nullable
    @JsonProperty(ORGANISATION_UNIT)
    public abstract String organisationUnit();

    @Nullable
    @JsonProperty(PROGRAM)
    public abstract String program();

    @Nullable
    @JsonProperty(DATE_OF_ENROLLMENT)
    public abstract Date dateOfEnrollment();

    @Nullable
    @JsonProperty(DATE_OF_INCIDENT)
    public abstract Date dateOfIncident();

    @Nullable
    @JsonProperty(FOLLOW_UP)
    public abstract Boolean followUp();

    @Nullable
    @JsonProperty(ENROLLMENT_STATUS)
    public abstract EnrollmentStatus enrollmentStatus();

    @Nullable
    @JsonProperty(TRACKED_ENTITY_INSTANCE)
    public abstract String trackedEntityInstance();

    @Nullable
    @JsonProperty(COORDINATE)
    public abstract Coordinates coordinate();

    @JsonCreator
    public static Enrollment create(
            @JsonProperty(UID) String uid,
            @JsonProperty(CREATED) Date created,
            @JsonProperty(LAST_UPDATED) Date lastUpdated,
            @JsonProperty(ORGANISATION_UNIT) String organisationUnit,
            @JsonProperty(PROGRAM) String program,
            @JsonProperty(DATE_OF_ENROLLMENT) Date dateOfEnrollment,
            @JsonProperty(DATE_OF_INCIDENT) Date dateOfIncident,
            @JsonProperty(FOLLOW_UP) Boolean followUp,
            @JsonProperty(ENROLLMENT_STATUS) EnrollmentStatus enrollmentStatus,
            @JsonProperty(TRACKED_ENTITY_INSTANCE) String trackedEntityInstance,
            @JsonProperty(COORDINATE) Coordinates coordinate) {
        return new AutoValue_Enrollment(uid, created, lastUpdated, organisationUnit, program,
                dateOfEnrollment, dateOfIncident, followUp, enrollmentStatus, trackedEntityInstance,
                coordinate);
    }
}