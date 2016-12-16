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

package org.hisp.dhis.android.models.program;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hisp.dhis.android.models.Inject;
import org.hisp.dhis.android.models.common.BaseIdentifiableObject;
import org.hisp.dhis.android.models.common.ValueType;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProgramTrackedEntityAttributeIntegrationTests {

    @Test
    public void programIndicator_shouldMapFromJsonString() throws IOException, ParseException {
        ObjectMapper objectMapper = Inject.objectMapper();
        ProgramTrackedEntityAttribute programTrackedEntityAttribute = objectMapper.readValue("{\n" +
                "\"lastUpdated\": \"2016-10-11T10:41:40.401\",\n" +
                "\"id\": \"YhqgQ6Iy4c4\",\n" +
                "\"href\": \"https://play.dhis2.org/dev/api/programTrackedEntityAttributes/YhqgQ6Iy4c4\",\n" +
                "\"created\": \"2016-10-11T10:41:40.401\",\n" +
                "\"name\": \"Child Programme Gender\",\n" +
                "\"shortName\": \"Child Programme Gender\",\n" +
                "\"displayName\": \"Child Programme Gender\",\n" +
                "\"mandatory\": false,\n" +
                "\"displayShortName\": \"Child Programme Gender\",\n" +
                "\"externalAccess\": false,\n" +
                "\"valueType\": \"TEXT\",\n" +
                "\"allowFutureDate\": false,\n" +
                "\"dimensionItem\": \"IpHINAT79UW.cejWyOfXge6\",\n" +
                "\"displayInList\": false,\n" +
                "\"dimensionItemType\": \"PROGRAM_ATTRIBUTE\",\n" +
                "\"access\": {\n" +
                "\"read\": true,\n" +
                "\"update\": true,\n" +
                "\"externalize\": false,\n" +
                "\"delete\": true,\n" +
                "\"write\": true,\n" +
                "\"manage\": false\n" +
                "},\n" +
                "\"program\": {\n" +
                "\"id\": \"IpHINAT79UW\"\n" +
                "},\n" +
                "\"trackedEntityAttribute\": {\n" +
                "\"id\": \"cejWyOfXge6\"\n" +
                "},\n" +
                "\"translations\": [],\n" +
                "\"userGroupAccesses\": [],\n" +
                "\"attributeValues\": []\n" +
                "}", ProgramTrackedEntityAttribute.class);

        assertThat(programTrackedEntityAttribute.created()).isEqualTo(
                BaseIdentifiableObject.DATE_FORMAT.parse("2016-10-11T10:41:40.401"));
        assertThat(programTrackedEntityAttribute.lastUpdated()).isEqualTo(
                BaseIdentifiableObject.DATE_FORMAT.parse("2016-10-11T10:41:40.401"));
        assertThat(programTrackedEntityAttribute.uid()).isEqualTo("YhqgQ6Iy4c4");

        assertThat(programTrackedEntityAttribute.name()).isEqualTo("Child Programme Gender");
        assertThat(programTrackedEntityAttribute.displayName()).isEqualTo("Child Programme Gender");
        assertThat(programTrackedEntityAttribute.shortName()).isEqualTo("Child Programme Gender");
        assertThat(programTrackedEntityAttribute.displayShortName()).isEqualTo("Child Programme Gender");

        assertThat(programTrackedEntityAttribute.mandatory()).isEqualTo(false);
        assertThat(programTrackedEntityAttribute.trackedEntityAttribute().uid()).isEqualTo("cejWyOfXge6");
        assertThat(programTrackedEntityAttribute.valueType()).isEqualTo(ValueType.TEXT);
        assertThat(programTrackedEntityAttribute.allowFutureDate()).isEqualTo(false);
        assertThat(programTrackedEntityAttribute.displayInList()).isEqualTo(false);
    }
}