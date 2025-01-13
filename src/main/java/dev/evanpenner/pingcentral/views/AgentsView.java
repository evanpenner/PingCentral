package dev.evanpenner.pingcentral.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.service.AgentService;

@Route("agents")
public class AgentsView extends VerticalLayout {
    private final AgentService agentService;

    public AgentsView(AgentService agentService) {
        this.agentService = agentService;
        buildBody();
    }

    private void buildBody() {
        Grid<Agent> agentGrid = new Grid<>();
        agentGrid.addColumn(Agent::getName).setHeader("Name").setSortable(true);
        agentGrid.addColumn(Agent::getId).setHeader("ID").setSortable(true);
        agentGrid.addColumn(Agent::getOs).setHeader("OS").setSortable(true);
        agentGrid.addColumn(Agent::getVersion).setHeader("Version").setSortable(true);
        agentGrid.addColumn(Agent::getSignedInUsername).setHeader("Logged in User").setSortable(true);
        agentGrid.addColumn(Agent::isVerified).setHeader("Verified").setSortable(true);
        agentGrid.setItems(provider -> agentService.pageAgents(provider.getPage(), provider.getPageSize()));
        agentGrid.setSizeFull();

        GridContextMenu<Agent> gridContextMenu = agentGrid.addContextMenu();
        gridContextMenu.addItem("Verify Agent", event -> {
            if (event.getItem().isPresent()) {
                Agent agent = event.getItem().get();
                agent.setVerified(true);

                event.getGrid().getLazyDataView().refreshItem(agentService.verifyAgent(agent));
            }
        });

        gridContextMenu.addItem("Un-verify Agent", event -> {
            if (event.getItem().isPresent()) {
                Agent agent = event.getItem().get();
                event.getGrid().getLazyDataView().refreshItem(agentService.unverifyAgent(agent));
            }
        });

        add(agentGrid);
    }
}
