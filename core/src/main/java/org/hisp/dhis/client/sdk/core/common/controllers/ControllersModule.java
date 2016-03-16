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

package org.hisp.dhis.client.sdk.core.common.controllers;

import org.hisp.dhis.client.sdk.core.common.network.INetworkModule;
import org.hisp.dhis.client.sdk.core.common.persistence.IPersistenceModule;
import org.hisp.dhis.client.sdk.core.common.preferences.IPreferencesModule;
import org.hisp.dhis.client.sdk.core.dashboard.IDashboardController;
import org.hisp.dhis.client.sdk.core.enrollment.EnrollmentController;
import org.hisp.dhis.client.sdk.core.enrollment.IEnrollmentController;
import org.hisp.dhis.client.sdk.core.event.EventController;
import org.hisp.dhis.client.sdk.core.event.IEventController;
import org.hisp.dhis.client.sdk.core.organisationunit.IOrganisationUnitController;
import org.hisp.dhis.client.sdk.core.organisationunit.OrganisationUnitController;
import org.hisp.dhis.client.sdk.core.program.IProgramController;
import org.hisp.dhis.client.sdk.core.program.ProgramController;
import org.hisp.dhis.client.sdk.core.trackedentity.TrackedEntityAttributeController;
import org.hisp.dhis.client.sdk.core.trackedentity.TrackedEntityController;
import org.hisp.dhis.client.sdk.core.user.AssignedOrganisationUnitController;
import org.hisp.dhis.client.sdk.core.user.AssignedProgramsController;
import org.hisp.dhis.client.sdk.core.user.IAssignedOrganisationUnitsController;
import org.hisp.dhis.client.sdk.core.user.IAssignedProgramsController;
import org.hisp.dhis.client.sdk.core.user.IUserAccountController;
import org.hisp.dhis.client.sdk.core.user.UserAccountController;
import org.hisp.dhis.client.sdk.models.trackedentity.TrackedEntity;
import org.hisp.dhis.client.sdk.models.trackedentity.TrackedEntityAttribute;

import static org.hisp.dhis.client.sdk.models.utils.Preconditions.isNull;

public class ControllersModule implements IControllersModule {

    private final IProgramController programController;
    private final IAssignedProgramsController assignedProgramsController;

    private final IOrganisationUnitController organisationUnitController;
    private final IAssignedOrganisationUnitsController assignedOrganisationUnitsController;

    private final IUserAccountController userAccountController;
    private final IDashboardController dashboardController;
    private final IEventController eventController;
    private final IEnrollmentController enrollmentController;
    private final IDataController<TrackedEntityAttribute> trackedEntityAttributeController;
    private final IDataController<TrackedEntity> trackedEntityController;


    public ControllersModule(INetworkModule networkModule, IPersistenceModule persistenceModule,
                             IPreferencesModule preferencesModule) {
        isNull(networkModule, "networkModule must not be null");
        isNull(persistenceModule, "persistenceModule must not be null");
        isNull(preferencesModule, "preferencesModule must not be null");

        programController = new ProgramController(
                networkModule.getSystemInfoApiClient(), networkModule.getProgramApiClient(),
                networkModule.getUserApiClient(), persistenceModule.getProgramStore(),
                persistenceModule.getTransactionManager(),
                preferencesModule.getLastUpdatedPreferences());

        assignedProgramsController = new AssignedProgramsController(
                networkModule.getUserApiClient(), programController);

        organisationUnitController = new OrganisationUnitController(
                networkModule.getSystemInfoApiClient(),
                networkModule.getOrganisationUnitApiClient(),
                networkModule.getUserApiClient(),
                persistenceModule.getOrganisationUnitStore(),
                persistenceModule.getTransactionManager(),
                preferencesModule.getLastUpdatedPreferences());

        assignedOrganisationUnitsController = new AssignedOrganisationUnitController(
                networkModule.getUserApiClient(), organisationUnitController);

        /////////////////////////////////////////////////////////////////////////////////////
        // LEGACY IMPLEMENTATION
        /////////////////////////////////////////////////////////////////////////////////////

        userAccountController = new UserAccountController(networkModule.getUserApiClient(),
                persistenceModule.getUserAccountStore());

        eventController = new EventController(
                networkModule.getEventApiClient(),
                networkModule.getSystemInfoApiClient(),
                preferencesModule.getLastUpdatedPreferences(),
                persistenceModule.getTransactionManager(),
                persistenceModule.getStateStore(),
                persistenceModule.getEventStore(),
                persistenceModule.getTrackedEntityDataValueStore(),
                persistenceModule.getOrganisationUnitStore(),
                persistenceModule.getProgramStore(),
                persistenceModule.getFailedItemStore());

        dashboardController = null;

        enrollmentController = new EnrollmentController(
                networkModule.getEnrollmentApiClient(),
                networkModule.getSystemInfoApiClient(),
                preferencesModule.getLastUpdatedPreferences(),
                persistenceModule.getTransactionManager(),
                eventController,
                persistenceModule.getEnrollmentStore(),
                persistenceModule.getEventStore(),
                persistenceModule.getStateStore(),
                persistenceModule.getFailedItemStore()
        );

        trackedEntityAttributeController = new TrackedEntityAttributeController(
                networkModule.getTrackedEntityAttributeApiClient(),
                persistenceModule.getTransactionManager(),
                preferencesModule.getLastUpdatedPreferences(),
                persistenceModule.getTrackedEntityAttributeStore(),
                networkModule.getSystemInfoApiClient()
        );

        trackedEntityController = new TrackedEntityController(
                networkModule.getTrackedEntityApiClient(),
                persistenceModule.getTransactionManager(),
                preferencesModule.getLastUpdatedPreferences(),
                persistenceModule.getTrackedEntityStore(),
                networkModule.getSystemInfoApiClient());
    }

    @Override
    public IProgramController getProgramController() {
        return programController;
    }

    @Override
    public IAssignedProgramsController getAssignedProgramsController() {
        return assignedProgramsController;
    }

    @Override
    public IOrganisationUnitController getOrganisationUnitController() {
        return organisationUnitController;
    }

    @Override
    public IAssignedOrganisationUnitsController getAssignedOrganisationUnitsController() {
        return assignedOrganisationUnitsController;
    }

    @Override
    public IUserAccountController getUserAccountController() {
        return userAccountController;
    }

    @Override
    public IEventController getEventController() {
        return eventController;
    }

    @Override
    public IEnrollmentController getEnrollmentController() {
        return enrollmentController;
    }

    @Override
    public IDataController<TrackedEntityAttribute> getTrackedEntityAttributeController() {
        return trackedEntityAttributeController;
    }

    @Override
    public IDataController<TrackedEntity> getTrackedEntityController() {
        return trackedEntityController;
    }
}