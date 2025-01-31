package dev.evanpenner.pingcentral.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.service.TargetService;

@Route("targets")
public class TargetsView extends VerticalLayout {
    private final TargetService targetService;

    public TargetsView(TargetService targetService) {
        this.targetService = targetService;
        buildBody();
    }

    private void buildBody() {
        Grid<Target> targetGrid = new Grid<>();
        targetGrid.addColumn(Target::getName);
        targetGrid.setItems(q -> targetService.queryTargets(q.getPage(), q.getPageSize()));
        targetGrid.setSizeFull();

        setSizeFull();
        Button createTarget = new Button("Create Target", event -> {
            Dialog dialog = new Dialog();
            TextField nameField = new TextField("Name");
            TextField hostField = new TextField("Host");
            // TODO: Implement host/ip validation regex for hostField
            NumberField frequencyField = new NumberField("Frequency (ms)");
            frequencyField.setMin(1000);
            frequencyField.setMax(60000);

            dialog.add(new VerticalLayout(nameField, hostField, frequencyField));

            Button saveButton = new Button("Save", saveBtnEvent -> {
                
            });
        });

        add(createTarget, targetGrid);
    }
}
