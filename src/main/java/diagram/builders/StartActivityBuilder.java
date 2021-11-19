package diagram.builders;

import diagram.xpdl.Activity;
import utils.DiagramGlobals;

public class StartActivityBuilder extends ActivityBuilder {

    protected StartActivityBuilder(String id, String name, String owner, Integer position) {
        super(id, name, owner, position);

        activity.setType(Activity.Type.START_EVENT);
        activity.setPerformersList(null);
        activity.setTransitionRestrictionsList(null);

        offsetCoordinates(DiagramGlobals.START_ACTIVITY_X_OFFSET, DiagramGlobals.START_ACTIVITY_Y_OFFSET);

        activityNodeGraphicsInfo.setHeight(DiagramGlobals.START_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(DiagramGlobals.START_ACTIVITY_SIZE);
    }
}
