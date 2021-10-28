package diagram.builders;

import diagram.events.EndEvent;
import diagram.events.Event;
import utils.DiagramGlobals;

public class EndActivityBuilder extends BaseActivityBuilder {

    public EndActivityBuilder(String id, String owner, Integer position) {
        super(id, "End", owner, position);

        Event event = new Event();
        event.setEndEvent(new EndEvent());
        activity.setEvent(event);

        activityNodeGraphicsInfo.setHeight(DiagramGlobals.END_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(DiagramGlobals.END_ACTIVITY_SIZE);
    }
}
