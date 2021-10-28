package diagram.builders;

import diagram.Activity;
import diagram.infos.NodeGraphicsInfo;
import lombok.Getter;
import utils.DiagramGlobals;

public abstract class BaseActivityBuilder {

    @Getter
    protected final Activity activity;

    protected final NodeGraphicsInfo activityNodeGraphicsInfo;

    public BaseActivityBuilder(String id, String name, String owner, Integer position) {
        activity = new Activity();
        activity.setId(id);
        activity.setName(name);

        activityNodeGraphicsInfo = new NodeGraphicsInfo();
        activity.getNodeGraphicsInfosList().add(activityNodeGraphicsInfo);

        activityNodeGraphicsInfo.setBorderColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        activityNodeGraphicsInfo.setFillColor(DiagramGlobals.ACTIVITY_FILL_COLOUR);
        activityNodeGraphicsInfo.setIsVisible(true);
        activityNodeGraphicsInfo.setLaneId(owner);
        activityNodeGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);
        activityNodeGraphicsInfo.setCoordinates(DiagramGlobals.ACTIVITY_X_BASE + position * DiagramGlobals.ACTIVITY_SPACING, DiagramGlobals.ACTIVITY_Y_BASE);
    }

    public void setCoordinates(Integer x, Integer y) {
        activityNodeGraphicsInfo.setCoordinates(x, y);
    }

    public void offsetCoordinates(Integer xOffset, Integer yOffset) {
        activityNodeGraphicsInfo.offsetCoordinates(xOffset, yOffset);
    }
}
