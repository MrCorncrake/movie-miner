package diagram.builders;

import diagram.xpdl.Activity;
import utils.DiagramGlobals;

public class StartActivityBuilder extends BaseActivityBuilder {

    public StartActivityBuilder(String id, String name, String owner, Integer position) {
        super(id, name, owner, position);

        activity.setType(Activity.Type.START_EVENT);
        activity.setPerformersList(null);
        activity.setTransitionRestrictionsList(null);

        activityNodeGraphicsInfo.setHeight(DiagramGlobals.START_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(DiagramGlobals.START_ACTIVITY_SIZE);
    }
}
