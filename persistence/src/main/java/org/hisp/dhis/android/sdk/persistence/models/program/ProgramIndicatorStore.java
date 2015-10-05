/*
 * Copyright (c) 2015, University of Oslo
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

package org.hisp.dhis.android.sdk.persistence.models.program;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.hisp.dhis.android.sdk.models.program.IProgramIndicatorStore;
import org.hisp.dhis.android.sdk.models.program.Program;
import org.hisp.dhis.android.sdk.models.program.ProgramIndicator;
import org.hisp.dhis.android.sdk.persistence.models.flow.ProgramIndicator$Flow;
import org.hisp.dhis.android.sdk.persistence.models.flow.ProgramIndicator$Flow$Table;

import java.util.List;

public final class ProgramIndicatorStore implements IProgramIndicatorStore {

    public ProgramIndicatorStore() {
        //empty constructor
    }

    @Override
    public void insert(ProgramIndicator object) {
        ProgramIndicator$Flow programIndicatorFlow = ProgramIndicator$Flow.fromModel(object);
        programIndicatorFlow.insert();

        object.setId(programIndicatorFlow.getId());
    }

    @Override
    public void update(ProgramIndicator object) {
        ProgramIndicator$Flow.fromModel(object).update();
    }

    @Override
    public void save(ProgramIndicator object) {
        ProgramIndicator$Flow programIndicatorFlow =
                ProgramIndicator$Flow.fromModel(object);
        programIndicatorFlow.save();

        object.setId(programIndicatorFlow.getId());
    }

    @Override
    public void delete(ProgramIndicator object) {
        ProgramIndicator$Flow.fromModel(object).delete();
    }

    @Override
    public List<ProgramIndicator> queryAll() {
        List<ProgramIndicator$Flow> programIndicatorFlows = new Select()
                .from(ProgramIndicator$Flow.class)
                .queryList();
        return ProgramIndicator$Flow.toModels(programIndicatorFlows);
    }

    @Override
    public ProgramIndicator queryById(long id) {
        ProgramIndicator$Flow programIndicatorFlow = new Select()
                .from(ProgramIndicator$Flow.class)
                .where(Condition.column(ProgramIndicator$Flow$Table.ID).is(id))
                .querySingle();
        return ProgramIndicator$Flow.toModel(programIndicatorFlow);
    }

    @Override
    public ProgramIndicator queryByUid(String uid) {
        ProgramIndicator$Flow programIndicatorFlow = new Select()
                .from(ProgramIndicator$Flow.class)
                .where(Condition.column(ProgramIndicator$Flow$Table.UID).is(uid))
                .querySingle();
        return ProgramIndicator$Flow.toModel(programIndicatorFlow);
    }

    @Override
    public List<ProgramIndicator> query(Program program) {
        List<ProgramIndicator$Flow> programIndicatorFlows = new Select()
                .from(ProgramIndicator$Flow.class).where(Condition.
                        column(ProgramIndicator$Flow$Table.PROGRAM).is(program.getUId()))
                .queryList();
        return ProgramIndicator$Flow.toModels(programIndicatorFlows);
    }
}