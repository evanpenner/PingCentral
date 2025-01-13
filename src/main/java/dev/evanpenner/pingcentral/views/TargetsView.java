package dev.evanpenner.pingcentral.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    }
}
