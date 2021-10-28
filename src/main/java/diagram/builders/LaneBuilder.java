package diagram.builders;

import diagram.*;
import diagram.infos.ConnectorGraphicsInfo;
import diagram.infos.NodeGraphicsInfo;
import lombok.Getter;
import utils.DiagramGlobals;

import java.util.ArrayList;

public class LaneBuilder {

    @Getter
    private final Lane lane;

    @Getter
    private final Participant performer;

    private final WorkflowProcess workflowProcess;
    private final NodeGraphicsInfo laneNodeGraphicsInfo;

    private Integer activityCounter = 0;
    private Integer transitionCounter = 0;

    @Getter
    private BaseActivityBuilder lastActivity;

    @Getter
    private final ArrayList<Activity> activities = new ArrayList<>();

    @Getter
    private final ArrayList<Transition> transitions = new ArrayList<>();

    public LaneBuilder(WorkflowProcess workflowProcess, String id, String name, String performer) {
        this.workflowProcess = workflowProcess;
        this.lane = new Lane(id, name);
        this.performer = new Participant(performer);

        laneNodeGraphicsInfo = new NodeGraphicsInfo();
        laneNodeGraphicsInfo.setBorderColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        laneNodeGraphicsInfo.setFillColor(DiagramGlobals.POOL_LANE_FILL_COLOUR);
        laneNodeGraphicsInfo.setIsVisible(true);
        laneNodeGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);

        lane.getNodeGraphicsInfosList().add(laneNodeGraphicsInfo);
        lane.getPerformersList().add(performer);
    }

    public void setBorderColor(String red, String green, String blue) {
        laneNodeGraphicsInfo.setBorderColor(red + "," + green + "," + blue);
    }

    public void setFillColor(String red, String green, String blue) {
        laneNodeGraphicsInfo.setBorderColor(red + "," + green + "," + blue);
    }

    public void setIsVisible(Boolean visible) {
        laneNodeGraphicsInfo.setIsVisible(visible);
    }

    public void setPerformer(String performer) {
        lane.getPerformersList().clear();
        lane.getPerformersList().add(performer);
        this.performer.setId(performer);
    }

    // Transitions

    private void addTransition(Activity to) {
        Transition transition = new Transition(lane.getId() + "_transition" + transitionCounter++);
        transition.setFrom(lastActivity.getActivity().getId());
        transition.setTo(to.getId());

        ConnectorGraphicsInfo connectorGraphicsInfo = new ConnectorGraphicsInfo();
        connectorGraphicsInfo.setFillColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        connectorGraphicsInfo.setIsVisible(true);
        connectorGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);
        connectorGraphicsInfo.setStyle(DiagramGlobals.TRANSITION_STYLE);

        transition.getConnectorGraphicsInfosList().add(connectorGraphicsInfo);

        transitions.add(transition);
        workflowProcess.getTransitionsList().add(transition);
    }

    // Activities

    public StartActivityBuilder addStartActivity(Integer position, String name) {
        StartActivityBuilder startActivityBuilder = new StartActivityBuilder(lane.getId() + "_activity" + activityCounter++, name, lane.getId(), position);
        activities.add(startActivityBuilder.getActivity());
        workflowProcess.getActivitiesList().add(startActivityBuilder.getActivity());
        lastActivity = startActivityBuilder;
        return startActivityBuilder;
    }

    public EndActivityBuilder addEndActivity(Integer position) {
        EndActivityBuilder endActivityBuilder = new EndActivityBuilder(lane.getId() + "_activity" + activityCounter++, lane.getId(), position);
        activities.add(endActivityBuilder.getActivity());
        addTransition(endActivityBuilder.getActivity());
        workflowProcess.getActivitiesList().add(endActivityBuilder.getActivity());
        return endActivityBuilder;
    }


}
