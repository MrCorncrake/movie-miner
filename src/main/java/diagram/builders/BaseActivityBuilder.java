package diagram.builders;

import diagram.xpdl.Activity;
import diagram.xpdl.infos.NodeGraphicsInfo;
import lombok.Getter;

/**
 * Basic activity builder class. Sets up all the common elements of different types of activity
 */
public abstract class BaseActivityBuilder {

    @Getter
    protected final Activity activity;

    protected final NodeGraphicsInfo activityNodeGraphicsInfo;

    public BaseActivityBuilder(String id, String name, String owner, Integer position) {
        activity = new Activity(id, name);

        activityNodeGraphicsInfo = new NodeGraphicsInfo();
        activity.getNodeGraphicsInfosList().add(activityNodeGraphicsInfo);

        activityNodeGraphicsInfo.setBorderColor(Globals.DEFAULT_BORDER_COLOUR);
        activityNodeGraphicsInfo.setFillColor(Globals.ACTIVITY_FILL_COLOUR);
        activityNodeGraphicsInfo.setIsVisible(true);
        activityNodeGraphicsInfo.setLaneId(owner);
        activityNodeGraphicsInfo.setToolId(Globals.TOOL_ID);
        activityNodeGraphicsInfo.setCoordinates(Globals.ACTIVITY_X_BASE + position * Globals.ACTIVITY_SPACING, Globals.ACTIVITY_Y_BASE);
    }

    public void setCoordinates(Integer x, Integer y) {
        activityNodeGraphicsInfo.setCoordinates(x, y);
    }

    public void offsetCoordinates(Integer xOffset, Integer yOffset) {
        activityNodeGraphicsInfo.offsetCoordinates(xOffset, yOffset);
    }
}
