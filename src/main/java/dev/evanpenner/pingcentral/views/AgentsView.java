package dev.evanpenner.pingcentral.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridSubMenu;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.evanpenner.pingcentral.entity.Agent;
import dev.evanpenner.pingcentral.entity.Target;
import dev.evanpenner.pingcentral.service.AgentService;
import dev.evanpenner.pingcentral.service.TargetService;

import java.util.List;
import java.util.Optional;

@Route("agents")
public class AgentsView extends VerticalLayout {
    private final AgentService agentService;
    private final TargetService targetService;

    public AgentsView(AgentService agentService, TargetService targetService) {
        this.agentService = agentService;
        this.targetService = targetService;

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

        var item = gridContextMenu.addItem("Add Target");
        var subMenu = item.getSubMenu();
        for (Target target : targetService.queryTargets(0, 100).toList()) {
            subMenu.addItem(target.getName(), event -> {
                Optional<Agent> optAgent = event.getItem();
                if (optAgent.isPresent()) {
                    Agent agent = optAgent.get();
                    agent.setVerified(true);
                    List<Target> agentTargets = agentService.getAgentTargets(agent.getId());
                    agentTargets.add(target);
                    agent.setTargets(agentTargets);

                    event.getGrid().getLazyDataView().refreshItem(agentService.saveAgent(agent));
                }
            });
        }

        add(agentGrid);
    }
}
