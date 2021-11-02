package diagram.builders;

import diagram.xpdl.Activity;

public class EndActivityBuilder extends BaseActivityBuilder {

    public EndActivityBuilder(String id, String owner, Integer position) {
        super(id, "End", owner, position);

        activity.setType(Activity.Type.END_EVENT);

        activityNodeGraphicsInfo.setHeight(Globals.END_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(Globals.END_ACTIVITY_SIZE);
    }
}
