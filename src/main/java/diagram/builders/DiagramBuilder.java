package diagram.builders;

import diagram.*;
import diagram.Package;
import diagram.infos.NodeGraphicsInfo;
import lombok.Getter;
import utils.DiagramGlobals;

import java.util.ArrayList;

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

    public DiagramBuilder(String id, String packageName, String diagramName) {
        diagram = new Package(id, packageName);
        diagram.setConformanceClass(new ConformanceClass());
        // Diagram attributes
        ArrayList<ExtendedAttribute> extendedAttributes = diagram.getExtendedAttributesList();
        editingTool = new ExtendedAttribute("EDITING_TOOL", "Movie-Miner");
        editingToolVersion = new ExtendedAttribute("EDITING_TOOL_VERSION", DiagramGlobals.EDITING_TOOL_VERSION);
        JaWEConfiguration = new ExtendedAttribute("JaWE_CONFIGURATION", "default");
        extendedAttributes.add(editingTool);
        extendedAttributes.add(editingToolVersion);
        extendedAttributes.add(JaWEConfiguration);
        // Main diagram classes
        workflowProcess = new WorkflowProcess("Movie", diagramName);
        pool = new Pool(id + "_pool", diagramName, true, true, "HORIZONTAL", "Movie");
        poolNodeGraphicInfo = new NodeGraphicsInfo();
        poolNodeGraphicInfo.setBorderColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        poolNodeGraphicInfo.setFillColor(DiagramGlobals.POOL_DEFAULT_FILL_COLOUR);
        poolNodeGraphicInfo.setIsVisible(true);
        poolNodeGraphicInfo.setToolId(DiagramGlobals.TOOL_ID);
        pool.getNodeGraphicsInfosList().add(poolNodeGraphicInfo);

        diagram.getWorkflowProcessesList().add(workflowProcess);
        diagram.getPoolsList().add(pool);
    }

    public LaneBuilder addLane(String performer) {
        LaneBuilder laneBuilder = new LaneBuilder(workflowProcess, pool.getId() + "_lane" + ++laneCounter, "Test_par" + laneCounter, performer);
        pool.getLanesList().add(laneBuilder.getLane());
        diagram.getParticipantsList().add(laneBuilder.getPerformer());
        return laneBuilder;
    }

    public void removeLane(LaneBuilder laneBuilder) {
        pool.getLanesList().remove(laneBuilder.getLane());
        workflowProcess.getActivitiesList().removeAll(laneBuilder.getActivities());
        workflowProcess.getTransitionsList().removeAll(laneBuilder.getTransitions());
        diagram.getParticipantsList().remove(laneBuilder.getPerformer());
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
