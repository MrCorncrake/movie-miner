package diagram.builders;

import diagram.xpdl.Activity;
import utils.DiagramGlobals;

public class EndActivityBuilder extends BaseActivityBuilder {

    public EndActivityBuilder(String id, String owner, Integer position) {
        super(id, "End", owner, position);

        activity.setType(Activity.Type.END_EVENT);
        activity.setPerformersList(null);
        activity.setTransitionRestrictionsList(null);

        offsetCoordinates(DiagramGlobals.END_ACTIVITY_X_OFFSET, 0);

        activityNodeGraphicsInfo.setHeight(DiagramGlobals.END_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(DiagramGlobals.END_ACTIVITY_SIZE);
    }
}
