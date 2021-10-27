package diagram.builders;

import diagram.Lane;
import diagram.Participant;
import diagram.WorkflowProcess;
import diagram.infos.NodeGraphicsInfo;
import lombok.Getter;
import utils.DiagramGlobals;

public class LaneBuilder {

    @Getter
    private final Lane lane;

    @Getter
    private final Participant performer;

    private final WorkflowProcess workflowProcess;

    private final NodeGraphicsInfo laneNodeGraphicsInfo;

    public LaneBuilder(WorkflowProcess workflowProcess, String id, String name, String performer) {
        this.workflowProcess = workflowProcess;
        this.lane = new Lane(id, name);
        this.performer = new Participant(performer);

        laneNodeGraphicsInfo = new NodeGraphicsInfo();
        laneNodeGraphicsInfo.setBorderColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        laneNodeGraphicsInfo.setFillColor(DiagramGlobals.POOL_LANE_FILL_COLOUR);
        laneNodeGraphicsInfo.setIsVisible(true);
        laneNodeGraphicsInfo.setToolId("JaWE");

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


}
