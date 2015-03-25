/*
 *  Copyright (c) 2015, University of Oslo
 *  * All rights reserved.
 *  *
 *  * Redistribution and use in source and binary forms, with or without
 *  * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright notice, this
 *  * list of conditions and the following disclaimer.
 *  *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *  * this list of conditions and the following disclaimer in the documentation
 *  * and/or other materials provided with the distribution.
 *  * Neither the name of the HISP project nor the names of its contributors may
 *  * be used to endorse or promote products derived from this software without
 *  * specific prior written permission.
 *  *
 *  * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.hisp.dhis2.android.sdk.utils.ui.rows;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.hisp.dhis2.android.sdk.R;
import org.hisp.dhis2.android.sdk.controllers.metadata.MetaDataController;
import org.hisp.dhis2.android.sdk.persistence.models.BaseValue;
import org.hisp.dhis2.android.sdk.persistence.models.ProgramStageDataElement;

public class TextRow implements Row {
    private final LayoutInflater inflater;
    private final BaseValue dataValue;
    private EditTextHolder holder;
    private boolean editable = true;
    private String label;
    
    public TextRow(LayoutInflater inflater, String label, BaseValue dataValue) {
        this.inflater = inflater;
        this.label = label;
        this.dataValue = dataValue;
    }

    @Override
    public View getView(View convertView) {
        View view;
        
        if (convertView == null) {
            ViewGroup rowRoot = (ViewGroup) inflater.inflate(R.layout.listview_row_text, null);
            TextView label = (TextView) rowRoot.findViewById(R.id.text_label);
            EditText editText = (EditText) rowRoot.findViewById(R.id.edit_text_row);
           
            EditTextWatcher watcher = new EditTextWatcher(dataValue);
            editText.addTextChangedListener(watcher);
            
            holder = new EditTextHolder(label, editText, watcher);
            rowRoot.setTag(holder);
            view = rowRoot;
        } else {
            view = convertView;
            holder = (EditTextHolder) view.getTag();
        }
        
        holder.textLabel.setText(label);
        
        holder.textWatcher.setDataValue(dataValue);
        holder.editText.addTextChangedListener(holder.textWatcher);
        holder.editText.setText(dataValue.value);
        holder.editText.clearFocus();
        setEditable(editable);

        return view;
    }

    @Override
    public TextView getEntryView() {
        return holder.editText;
    }

    @Override
    public void setEditable(boolean editable) {
        this.editable = editable;
        if(holder != null) {
            if(editable) {
                holder.editText.setEnabled(true);
            } else {
                holder.editText.setEnabled(false);
            }
        }
    }
}

class EditTextWatcher implements TextWatcher {
    private BaseValue dataValue;
    
    EditTextWatcher(BaseValue dataValue) {
        this.dataValue = dataValue;
    }
    
    public void setDataValue(BaseValue dataValue) {
        this.dataValue = dataValue;
    }

    @Override
    public void afterTextChanged(Editable arg) {
        dataValue.value = (arg.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    
}

class EditTextHolder {
    final TextView textLabel;
    final EditText editText;
    final EditTextWatcher textWatcher;

    EditTextHolder(TextView textLabel, EditText editText, EditTextWatcher textWatcher) {
        this.textLabel = textLabel;
        this.editText = editText;
        this.textWatcher = textWatcher;
    }
}