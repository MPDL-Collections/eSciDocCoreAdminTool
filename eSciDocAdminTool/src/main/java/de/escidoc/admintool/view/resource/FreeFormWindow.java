package de.escidoc.admintool.view.resource;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import de.escidoc.admintool.view.ViewConstants;

public class FreeFormWindow extends Window {

    private final class AddMoreFieldListener implements Button.ClickListener {
        @Override
        public void buttonClick(final ClickEvent event) {
            final HorizontalLayout freeForm = new HorizontalLayout();

            final TextField key = new TextField();
            key.setWidth(100, Sizeable.UNITS_PIXELS);

            final TextField value = new TextField();
            value.setWidth(200, Sizeable.UNITS_PIXELS);

            freeForm.addComponent(key);
            freeForm.addComponent(value);

            final Button addMoreField = new Button("+");
            addMoreField.addListener(new AddMoreFieldListener());
            final Button removeField = new Button("-");
            removeField.addListener(new RemoveFieldListener(freeForm));
            freeForm.addComponent(addMoreField);
            freeForm.addComponent(removeField);
            fl.addComponent(freeForm);
        }
    }

    private final class RemoveFieldListener implements Button.ClickListener {
        private final HorizontalLayout freeForm;

        public RemoveFieldListener(final HorizontalLayout freeForm) {
            this.freeForm = freeForm;
        }

        @Override
        public void buttonClick(final ClickEvent event) {
            fl.removeComponent(freeForm);
        }
    }

    private final class CancelButtonListener implements ClickListener {
        private static final long serialVersionUID = -1211409730229979129L;

        @Override
        public void buttonClick(final ClickEvent event) {
            closeWindow();
        }
    }

    private static final long serialVersionUID = -4691275024835350852L;

    private static final String MODAL_DIALOG_WIDTH = "460px";

    private static final String MODAL_DIALOG_HEIGHT = "450px";

    private final Button okButton = new Button(ViewConstants.OK_LABEL);

    private final Button cancelBtn = new Button(ViewConstants.CANCEL_LABEL);

    Window mainWindow;

    FormLayout fl = new FormLayout();

    public FreeFormWindow(final Window mainWindow) {
        this.mainWindow = mainWindow;
        init();
    }

    private void init() {
        setContent(fl);
        fl.setMargin(true);
        configureWindow();

        configureTextArea();
        addButtons();
    }

    final HorizontalLayout freeForm = new HorizontalLayout();

    private void configureTextArea() {
        final TextField key = new TextField();
        key.setWidth(100, Sizeable.UNITS_PIXELS);
        final TextField value = new TextField();
        value.setWidth(200, Sizeable.UNITS_PIXELS);
        freeForm.addComponent(key);
        freeForm.addComponent(value);
        final Button addMoreField = new Button("+");
        freeForm.addComponent(addMoreField);

        addMoreField.addListener(new AddMoreFieldListener());
        fl.addComponent(freeForm);
    }

    private final HorizontalLayout buttons = new HorizontalLayout();

    private void addButtons() {
        fl.addComponent(buttons);
        fl.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);
        addOkButton();
        addCancelButton();
    }

    private void addCancelButton() {
        cancelBtn.addListener(new CancelButtonListener());
        buttons.addComponent(cancelBtn);
    }

    void closeWindow() {
        mainWindow.removeWindow(this);
    }

    private void addOkButton() {
        buttons.addComponent(okButton);
    }

    private void configureWindow() {
        setModal(true);
        setCaption("Enter Metadata as Raw XML");
        setHeight(MODAL_DIALOG_HEIGHT);
        setWidth(MODAL_DIALOG_WIDTH);
    }
}
