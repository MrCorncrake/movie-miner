package diagram.builders;

import diagram.xpdl.*;
import diagram.xpdl.infos.NodeGraphicsInfo;
import diagram.xpdl.Package;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Locale;

public class DiagramBuilder {

    @Getter
    private final Package diagram;

    // Package Attributes
    private final ExtendedAttribute editingTool;
    private final ExtendedAttribute editingToolVersion;
    private final ExtendedAttribute JaWEConfiguration;

    // Pool Attributes
    private final NodeGraphicsInfo poolNodeGraphicInfo;

    @Getter
    private final WorkflowProcess workflowProcess;
    @Getter
    private final Pool pool;

    private int laneCounter = 0;

    private final ArrayList<LaneBuilder> laneBuildersList = new ArrayList<>();

    public DiagramBuilder(String id, String packageName, String diagramName) {
        diagram = new Package(id, packageName);
        // Diagram attributes
        ArrayList<ExtendedAttribute> extendedAttributes = diagram.getExtendedAttributesList();
        editingTool = new ExtendedAttribute("EDITING_TOOL", "Movie-Miner");
        editingToolVersion = new ExtendedAttribute("EDITING_TOOL_VERSION", Globals.EDITING_TOOL_VERSION);
        JaWEConfiguration = new ExtendedAttribute("JaWE_CONFIGURATION", "default");
        extendedAttributes.add(editingTool);
        extendedAttributes.add(editingToolVersion);
        extendedAttributes.add(JaWEConfiguration);
        // Main diagram classes
        workflowProcess = new WorkflowProcess("Movie", diagramName);
        pool = new Pool(id + "_pool", diagramName, true, true, "HORIZONTAL", "Movie");
        poolNodeGraphicInfo = new NodeGraphicsInfo();
        poolNodeGraphicInfo.setBorderColor(Globals.DEFAULT_BORDER_COLOUR);
        poolNodeGraphicInfo.setFillColor(Globals.POOL_DEFAULT_FILL_COLOUR);
        poolNodeGraphicInfo.setIsVisible(true);
        poolNodeGraphicInfo.setToolId(Globals.TOOL_ID);
        pool.getNodeGraphicsInfosList().add(poolNodeGraphicInfo);

        diagram.getWorkflowProcessesList().add(workflowProcess);
        diagram.getPoolsList().add(pool);
    }

    public LaneBuilder addLane(String performer) {
        String performer_id = performer.replace(" ", "_").toLowerCase(Locale.ROOT).replace("'", "");
        LaneBuilder laneBuilder = new LaneBuilder(workflowProcess, pool.getId() + "_lane" + ++laneCounter, performer_id, performer);
        pool.getLanesList().add(laneBuilder.getLane());
        diagram.getParticipantsList().add(laneBuilder.getPerformer());
        laneBuildersList.add(laneBuilder);
        return laneBuilder;
    }

    public void removeLane(LaneBuilder laneBuilder) {
        pool.getLanesList().remove(laneBuilder.getLane());
        workflowProcess.getActivitiesList().removeAll(laneBuilder.getActivities());
        workflowProcess.getTransitionsList().removeAll(laneBuilder.getTransitions());
        diagram.getParticipantsList().remove(laneBuilder.getPerformer());
        laneBuildersList.remove(laneBuilder);
        laneBuildersList.forEach((LaneBuilder lb) -> lb.disconnectFromLane(laneBuilder));
    }

    // Diagram config

    public void setEditingTool(String editingTool) {
        this.editingTool.setValue(editingTool);
    }

    public void setEditingToolVersion(String editingToolVersion) {
        this.editingToolVersion.setValue(editingToolVersion);
    }

    public void setJaWEConfiguration(String jaWEConfiguration) {
        this.JaWEConfiguration.setValue(jaWEConfiguration);
    }

    public void setGraphConformance(String graphConformance) {
        this.diagram.setGraphConformance(graphConformance);
    }

    // Pool config

    public void setPoolBorderColor(String red, String green, String blue) {
        poolNodeGraphicInfo.setBorderColor(red + "," + green + "," + blue);
    }

    public void setPoolFillColor(String red, String green, String blue) {
        poolNodeGraphicInfo.setBorderColor(red + "," + green + "," + blue);
    }

    public void setPoolIsVisible(Boolean visible) {
        poolNodeGraphicInfo.setIsVisible(visible);
    }

}
