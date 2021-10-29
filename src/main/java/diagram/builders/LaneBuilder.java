package diagram.builders;

import diagram.xpdl.infos.ConnectorGraphicsInfo;
import diagram.xpdl.infos.NodeGraphicsInfo;
import diagram.xpdl.*;
import lombok.Getter;
import utils.DiagramGlobals;

import java.util.ArrayList;
import java.util.HashMap;

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

    @Getter
    private final HashMap<Integer, ActivityBuilder> activityBuildersMap = new HashMap<>();

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
        for(Integer i : activityBuildersMap.keySet()) {
                activityBuildersMap.get(i).setPerformer(performer);
        }
    }

    // Transitions

    private void addTransition(BaseActivityBuilder from, BaseActivityBuilder to) {
        Transition transition = new Transition(lane.getId() + "_transition" + transitionCounter++);
        transition.setFrom(from.getActivity().getId());
        transition.setTo(to.getActivity().getId());

        ConnectorGraphicsInfo connectorGraphicsInfo = new ConnectorGraphicsInfo();
        connectorGraphicsInfo.setFillColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        connectorGraphicsInfo.setIsVisible(true);
        connectorGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);
        connectorGraphicsInfo.setStyle(DiagramGlobals.TRANSITION_STYLE);

        transition.getConnectorGraphicsInfosList().add(connectorGraphicsInfo);

        if(to instanceof ActivityBuilder) {
            ((ActivityBuilder) to).addTransitionRef(transition.getId());
        }

        transitions.add(transition);
        workflowProcess.getTransitionsList().add(transition);
    }

    public boolean connectToLaneAt(LaneBuilder lane, Integer position) {
        if (activityBuildersMap.containsKey(position) && lane.activityBuildersMap.containsKey(position)) {
            addTransition(activityBuildersMap.get(position), lane.activityBuildersMap.get(position));
            lane.addTransition(lane.activityBuildersMap.get(position), activityBuildersMap.get(position));
            return true;
        }
        return false;
    }

    public void disconnectFromLane(LaneBuilder lane) {
        ArrayList<Transition> toRemove = new ArrayList<>();
        transitions.forEach((Transition t) -> {
            if(t.getTo().contains(lane.getLane().getId()))  {
                workflowProcess.getTransitionsList().remove(t);
                toRemove.add(t);
            }
        });
        transitions.removeAll(toRemove);
        for(Integer i : activityBuildersMap.keySet()) {
            toRemove.forEach((Transition t) -> activityBuildersMap.get(i).removeTransitionRef(t.getId()));
            lane.getTransitions().forEach((Transition t) -> activityBuildersMap.get(i).removeTransitionRef(t.getId()));
        }
    }

    // Activities

    private void addAnyActivity(BaseActivityBuilder activityBuilder) {
        activities.add(activityBuilder.getActivity());
        workflowProcess.getActivitiesList().add(activityBuilder.getActivity());
        lastActivity = activityBuilder;
    }

    public boolean addStartActivity(Integer position, String name) {
        if(activityBuildersMap.containsKey(position)) return false;
        StartActivityBuilder startActivityBuilder = new StartActivityBuilder(lane.getId() + "_activity" + activityCounter++, name, lane.getId(), position);
        addAnyActivity(startActivityBuilder);
        return true;
    }

    public boolean addEndActivity(Integer position) {
        if(activityBuildersMap.containsKey(position)) return false;
        EndActivityBuilder endActivityBuilder = new EndActivityBuilder(lane.getId() + "_activity" + activityCounter++, lane.getId(), position);
        addTransition(lastActivity, endActivityBuilder);
        addAnyActivity(endActivityBuilder);
        return true;
    }

    public boolean addActivity(Integer position, String name) {
        if(activityBuildersMap.containsKey(position)) return false;
        ActivityBuilder activityBuilder = new ActivityBuilder(lane.getId() + "_activity" + activityCounter++, name, lane.getId(), performer.getId(),position);
        addTransition(lastActivity, activityBuilder);
        addAnyActivity(activityBuilder);
        activityBuildersMap.put(position, activityBuilder);
        return true;
    }

}
