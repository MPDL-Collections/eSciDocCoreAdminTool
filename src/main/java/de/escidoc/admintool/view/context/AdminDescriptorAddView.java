/**
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE
 * or https://www.escidoc.org/license/ESCIDOC.LICENSE .
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at license/ESCIDOC.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *
 * Copyright 2011 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.admintool.view.context;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import de.escidoc.admintool.view.ViewConstants;

@SuppressWarnings("serial")
public class AdminDescriptorAddView extends AdminDescriptorView {

    public AdminDescriptorAddView(final Window mainWindow, final Accordion adminDescriptorAccordion) {
        super(mainWindow, adminDescriptorAccordion);
    }

    @Override
    protected void setWindowCaption() {
        setCaption(ViewConstants.ADD_A_NEW_ADMIN_DESCRIPTOR);
    }

    @Override
    protected void doSave() {
        final String adminDescriptorName = (String) adminDescNameField.getValue();
        if (isValid(adminDescriptorName)) {
            adminDescNameField.setComponentError(null);
            final String adminDescriptorContent = (String) adminDescContent.getValue();
            if (validate(adminDescriptorContent)) {
                adminDescriptorAccordion.addTab(new Label(adminDescriptorContent, Label.CONTENT_PREFORMATTED),
                    (String) adminDescNameField.getValue(), null);
                closeWindow();
            }
        }
        else {
            adminDescNameField.setComponentError(new UserError("Must not contain space"));
        }
    }
}