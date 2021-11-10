package diagram.builders;

import diagram.xpdl.Activity;
import diagram.xpdl.infos.NodeGraphicsInfo;
import lombok.Getter;
import utils.DiagramGlobals;

/**
 * Basic activity builder class. Sets up all the common elements of different types of activity
 */
public abstract class BaseActivityBuilder {

    @Getter
    protected final Activity activity;

    protected final NodeGraphicsInfo activityNodeGraphicsInfo;

    @Getter
    protected Integer position;

    public BaseActivityBuilder(String id, String name, String owner, Integer position) {
        activity = new Activity(id, name);

        activityNodeGraphicsInfo = new NodeGraphicsInfo();
        activity.getNodeGraphicsInfosList().add(activityNodeGraphicsInfo);

        activityNodeGraphicsInfo.setBorderColor(DiagramGlobals.DEFAULT_BORDER_COLOUR);
        activityNodeGraphicsInfo.setFillColor(DiagramGlobals.ACTIVITY_FILL_COLOUR);
        activityNodeGraphicsInfo.setIsVisible(true);
        activityNodeGraphicsInfo.setLaneId(owner);
        activityNodeGraphicsInfo.setToolId(DiagramGlobals.TOOL_ID);

        setPosition(position);
    }

    public void setPosition(Integer position) {
        this.position = position;
        setCoordinates(DiagramGlobals.ACTIVITY_X_BASE + position * DiagramGlobals.ACTIVITY_SPACING, DiagramGlobals.ACTIVITY_Y_BASE);
    }

    public void setCoordinates(Integer x, Integer y) {
        activityNodeGraphicsInfo.setCoordinates(x, y);
    }

    public void offsetCoordinates(Integer xOffset, Integer yOffset) {
        activityNodeGraphicsInfo.offsetCoordinates(xOffset, yOffset);
    }
}
