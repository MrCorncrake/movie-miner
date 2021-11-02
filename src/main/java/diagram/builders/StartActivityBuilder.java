package diagram.builders;

import diagram.xpdl.Activity;

public class StartActivityBuilder extends BaseActivityBuilder {

    public StartActivityBuilder(String id, String name, String owner, Integer position) {
        super(id, name, owner, position);

        activity.setType(Activity.Type.START_EVENT);
        activity.setPerformersList(null);
        activity.setTransitionRestrictionsList(null);

        activityNodeGraphicsInfo.setHeight(Globals.START_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(Globals.START_ACTIVITY_SIZE);
    }
}
