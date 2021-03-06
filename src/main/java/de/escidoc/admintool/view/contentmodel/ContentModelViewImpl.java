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
package de.escidoc.admintool.view.contentmodel;

import com.google.common.base.Preconditions;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.escidoc.admintool.domain.PdpRequest;
import de.escidoc.admintool.view.ViewConstants;
import de.escidoc.admintool.view.navigation.ActionIdConstants;
import de.escidoc.core.resources.Resource;

@SuppressWarnings("serial")
public class ContentModelViewImpl extends CustomComponent implements ContentModelView {

    private static final String CONTENT_MODELS_LABEL = "Content Models";

    private final HorizontalSplitPanel hSplitPanel = new HorizontalSplitPanel();

    private final VerticalLayout vLayout = new VerticalLayout();

    private final ContentModelListView listView;

    private final ContentModelAddView addView;

    private final ContentModelEditView editView;

    private final PdpRequest pdpRequest;

    public ContentModelViewImpl(final ContentModelListView listView, final ContentModelAddView addView,
        final ContentModelEditView editView, final PdpRequest pdpRequest) {

        Preconditions.checkNotNull(listView, "listView is null: %s", listView);
        Preconditions.checkNotNull(addView, "addView is null: %s", addView);
        Preconditions.checkNotNull(editView, "editView is null: %s", editView);
        Preconditions.checkNotNull(pdpRequest, "pdpRequest is null: %s", pdpRequest);

        setCompositionRoot(hSplitPanel);

        this.listView = listView;
        this.addView = addView;
        this.editView = editView;
        this.pdpRequest = pdpRequest;
    }

    @Override
    public void init() {
        configureLayout();
        addComponents();
    }

    private void configureLayout() {
        hSplitPanel.setSplitPosition(ViewConstants.SPLIT_POSITION_IN_PERCENT);
        hSplitPanel.setHeight(100, UNITS_PERCENTAGE);
        hSplitPanel.setSizeFull();
        listView.setSizeFull();
        vLayout.setHeight(100, UNITS_PERCENTAGE);
    }

    private void addComponents() {

        addHeader();
        addListView();

        hSplitPanel.addComponent(vLayout);
        hSplitPanel.addComponent(editView);
    }

    private void addListView() {
        vLayout.addComponent(listView);
        vLayout.addComponent(listView.createControls());
        vLayout.setExpandRatio(listView, 1.0f);
    }

    private void addHeader() {
        vLayout.addComponent(new Label("<b>" + CONTENT_MODELS_LABEL + "</b>", Label.CONTENT_XHTML));
    }

    @Override
    public void showAddView() {
        if (pdpRequest.isPermitted(ActionIdConstants.CREATE_CONTENT_MODEL)) {
            hSplitPanel.replaceComponent(editView, createAddView());
        }
        else {
            hSplitPanel.replaceComponent(editView, new BlankView());

        }
    }

    private Component createAddView() {
        addView.resetFields();
        return addView;
    }

    @Override
    public void showEditView(final Resource contentModel) {
        Preconditions.checkNotNull(contentModel, "contentModel is null: %s", contentModel);

        listView.setContentModel(contentModel);
        editView.setContentModel(contentModel);
        Item item = listView.getItem(contentModel);
        showEditView(item);
        hSplitPanel.setSecondComponent(editView);
    }

    @Override
    public void showEditView(final Item item) {
        Preconditions.checkNotNull(item, "item is null: %s", item);
        editView.bind(item);
    }

    @Override
    public void selectFirstItem() {
        Preconditions.checkNotNull(listView, "listView is null: %s", listView);
        Preconditions.checkNotNull(editView, "editView is null: %s", editView);

        if (listView.firstItemId() == null) {
            showAddView();
            return;
        }

        listView.selectFirstItem();
        editView.setContentModel(listView.firstItemId());
        editView.bind(listView.firstItem());
    }

    @Override
    public void select(Resource created) {
        listView.select(created);
    }
}
