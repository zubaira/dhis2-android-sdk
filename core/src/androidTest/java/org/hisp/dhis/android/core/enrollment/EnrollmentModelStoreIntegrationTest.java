package org.hisp.dhis.android.core.enrollment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.runner.AndroidJUnit4;

import org.hisp.dhis.android.core.AndroidTestUtils;
import org.hisp.dhis.android.core.common.BaseIdentifiableObject;
import org.hisp.dhis.android.core.common.State;
import org.hisp.dhis.android.core.data.database.AbsStoreTestCase;
import org.hisp.dhis.android.core.data.database.DbOpenHelper.Tables;
import org.hisp.dhis.android.core.organisationunit.CreateOrganisationUnitUtils;
import org.hisp.dhis.android.core.program.CreateProgramUtils;
import org.hisp.dhis.android.core.trackedentity.CreateTrackedEntityInstanceUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static org.hisp.dhis.android.core.data.database.CursorAssert.assertThatCursor;

@RunWith(AndroidJUnit4.class)
public class EnrollmentModelStoreIntegrationTest extends AbsStoreTestCase {
    private static final String[] EVENT_PROJECTION = {
            EnrollmentModel.Columns.UID,
            EnrollmentModel.Columns.CREATED,
            EnrollmentModel.Columns.LAST_UPDATED,
            EnrollmentModel.Columns.ORGANISATION_UNIT,
            EnrollmentModel.Columns.PROGRAM,
            EnrollmentModel.Columns.DATE_OF_ENROLLMENT,
            EnrollmentModel.Columns.DATE_OF_INCIDENT,
            EnrollmentModel.Columns.FOLLOW_UP,
            EnrollmentModel.Columns.ENROLLMENT_STATUS,
            EnrollmentModel.Columns.TRACKED_ENTITY_INSTANCE,
            EnrollmentModel.Columns.LATITUDE,
            EnrollmentModel.Columns.LONGITUDE,
            EnrollmentModel.Columns.STATE
    };
    private EnrollmentModelStore enrollmentModelStore;

    private static final Long ID = 3L;
    private static final String UID = "test_uid";
    private static final String ORGANISATION_UNIT = "test_orgUnit";
    private static final String PROGRAM = "test_program";
    private static final Boolean FOLLOW_UP = true;
    private static final EnrollmentStatus ENROLLMENT_STATUS = EnrollmentStatus.COMPLETED;
    private static final String TRACKED_ENTITY_INSTANCE = "test_trackedEntityInstance";
    private static final String LATITUDE = "10.832152";
    private static final String LONGITUDE = "59.345231";

    private static final State STATE = State.TO_UPDATE;

    // timestamp
    private static final String DATE = "2017-01-12T11:31:00.000";


    @Override
    @Before
    public void setUp() throws IOException {
        super.setUp();
        this.enrollmentModelStore = new EnrollmentModelStoreImpl(database());
    }

    @Test
    public void insert_shouldPersistInDatabase() throws Exception {
        ContentValues organisationUnit = CreateOrganisationUnitUtils.create(1L, ORGANISATION_UNIT);
        ContentValues program = CreateProgramUtils.create(1L, PROGRAM);
        ContentValues trackedEntityInstance = CreateTrackedEntityInstanceUtils.create(TRACKED_ENTITY_INSTANCE, ORGANISATION_UNIT);

        database().insert(Tables.ORGANISATION_UNIT, null, organisationUnit);
        database().insert(Tables.PROGRAM, null, program);
        database().insert(Tables.TRACKED_ENTITY_INSTANCE, null, trackedEntityInstance);

        Date date = BaseIdentifiableObject.DATE_FORMAT.parse(DATE);

        long rowId = enrollmentModelStore.insert(
                UID,
                date,
                date,
                ORGANISATION_UNIT,
                PROGRAM,
                date,
                date,
                FOLLOW_UP,
                ENROLLMENT_STATUS,
                TRACKED_ENTITY_INSTANCE,
                LATITUDE,
                LONGITUDE,
                STATE
        );

        Cursor cursor = database().query(Tables.ENROLLMENT, EVENT_PROJECTION,
                null, null, null, null, null);

        assertThat(rowId).isEqualTo(1L);

        assertThatCursor(cursor).hasRow(
                UID,
                DATE,
                DATE,
                ORGANISATION_UNIT,
                PROGRAM,
                DATE,
                DATE,
                AndroidTestUtils.toInteger(FOLLOW_UP),
                ENROLLMENT_STATUS,
                TRACKED_ENTITY_INSTANCE,
                LATITUDE,
                LONGITUDE,
                STATE
        ).isExhausted();
    }

    @Test(expected = SQLiteConstraintException.class)
    public void insertMissingForeignKey_shouldThrowSqliteConstraintException() throws Exception {
        Date date = BaseIdentifiableObject.DATE_FORMAT.parse(DATE);

        enrollmentModelStore.insert(
                UID,
                date,
                date,
                ORGANISATION_UNIT,
                PROGRAM,
                date,
                date,
                FOLLOW_UP,
                ENROLLMENT_STATUS,
                TRACKED_ENTITY_INSTANCE,
                LATITUDE,
                LONGITUDE,
                STATE
        );
    }

    @Test
    public void close_shouldNotCloseDatabase() {
        enrollmentModelStore.close();

        assertThat(database().isOpen()).isTrue();
    }
}
