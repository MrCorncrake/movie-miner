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
    private final NodeGraphicsInfo laneNodeGraphicsInfo;

    private WorkflowProcess workflowProcess;

    private Integer activityCounter = 0;
    private Integer transitionCounter = 0;

    @Getter
    private ActivityBuilder lastActivity;

    @Getter
    private final ArrayList<Activity> activities = new ArrayList<>();

    @Getter
    private final ArrayList<Transition> transitions = new ArrayList<>();

    @Getter
    private final HashMap<Integer, ActivityBuilder> activityBuildersMap = new HashMap<>();

    protected LaneBuilder(WorkflowProcess workflowProcess, String id, String name, String performer) {
        this.workflowProcess = workflowProcess;
        this.lane = new Lane(id, name);
        this.performer = new Participant(name);

        laneNodeGraphicsInfo = new NodeGraphicsInfo();
        laneNodeGraphicsInfo.setBorderColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        laneNodeGraphicsInfo.setFillColor(DiagramGlobals.POOL_LANE_FILL_COLOUR);
        laneNodeGraphicsInfo.setIsVisible(true);
        laneNodeGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);

        lane.getNodeGraphicsInfosList().add(laneNodeGraphicsInfo);
        lane.getPerformersList().add(performer);
    }

    public void setBorderColor(String color) {
        laneNodeGraphicsInfo.setBorderColor(color);
    }

    public void setBorderColor(int red, int green, int blue) {
        setBorderColor(red + "," + green + "," + blue);
    }

    public void setFillColor(String color) {
        laneNodeGraphicsInfo.setFillColor(color);
    }

    public void setFillColor(int red, int green, int blue) {
        setFillColor(red + "," + green + "," + blue);
    }

    public void setIsVisible(Boolean visible) {
        laneNodeGraphicsInfo.setIsVisible(visible);
    }

    public void setPerformer(String performer) {
        lane.getPerformersList().clear();
        lane.getPerformersList().add(performer);
        this.performer.setId(performer);
        for(Integer i : activityBuildersMap.keySet()) {
                if (activityBuildersMap.get(i) instanceof NormalActivityBuilder) {
                    ((NormalActivityBuilder) activityBuildersMap.get(i)).setPerformer(performer);
                }
        }
    }

    protected void disconnect() {
        // If the object was disconnected it will no longer have an impact on the WorkflowProcess object of the parent
        workflowProcess = null;
    }

    // Transitions

    private void addTransition(ActivityBuilder from, ActivityBuilder to) {
        Transition transition = new Transition(lane.getId() + "_transition" + transitionCounter++);
        transition.setFrom(from.getActivity().getId());
        transition.setTo(to.getActivity().getId());

        ConnectorGraphicsInfo connectorGraphicsInfo = new ConnectorGraphicsInfo();
        connectorGraphicsInfo.setFillColor(DiagramGlobals.DEFAULT_TRANSITION_COLOUR);
        connectorGraphicsInfo.setIsVisible(true);
        connectorGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);
        connectorGraphicsInfo.setStyle(DiagramGlobals.TRANSITION_STYLE);

        transition.getConnectorGraphicsInfosList().add(connectorGraphicsInfo);

        if(from instanceof NormalActivityBuilder) {
            ((NormalActivityBuilder) from).addTransition(transition);
        }
        if(to instanceof NormalActivityBuilder) {
            ((NormalActivityBuilder) to).addTransition(transition);
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
            toRemove.forEach((Transition t) -> ((NormalActivityBuilder) activityBuildersMap.get(i)).removeTransition(t));
            lane.getTransitions().forEach((Transition t) -> ((NormalActivityBuilder) activityBuildersMap.get(i)).removeTransition(t));
        }
    }

    // Activities

    private void addAnyActivity(Integer position, ActivityBuilder activityBuilder) {
        // This has to be done for every activity when it's added
        activities.add(activityBuilder.getActivity());
        workflowProcess.getActivitiesList().add(activityBuilder.getActivity());
        lastActivity = activityBuilder;
        activityBuildersMap.put(position, activityBuilder);
    }

    public boolean addStartActivity(Integer position, String name) {
        if(activityBuildersMap.containsKey(position)) return false;
        StartActivityBuilder startActivityBuilder = new StartActivityBuilder(lane.getId() + "_activity" + activityCounter++, name, lane.getId(), position);
        addAnyActivity(position, startActivityBuilder);
        return true;
    }

    public boolean addEndActivity(Integer position) {
        if(activityBuildersMap.containsKey(position)) return false;
        EndActivityBuilder endActivityBuilder = new EndActivityBuilder(lane.getId() + "_activity" + activityCounter++, lane.getId(), position);
        addTransition(lastActivity, endActivityBuilder);
        addAnyActivity(position, endActivityBuilder);
        return true;
    }

    public boolean addActivity(Integer position, String name) {
        if(activityBuildersMap.containsKey(position)) return false;
        NormalActivityBuilder normalActivityBuilder = new NormalActivityBuilder(lane.getId() + "_activity" + activityCounter++, name, lane.getId(), performer.getId(),position);
        addTransition(lastActivity, normalActivityBuilder);
        addAnyActivity(position, normalActivityBuilder);
        return true;
    }

}
