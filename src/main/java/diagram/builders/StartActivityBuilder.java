package diagram.builders;

import diagram.events.Event;
import diagram.events.StartEvent;
import utils.DiagramGlobals;

public class StartActivityBuilder extends BaseActivityBuilder {

    public StartActivityBuilder(String id, String name, String owner, Integer position) {
        super(id, name, owner, position);

        Event event = new Event();
        event.setStartEvent(new StartEvent());
        activity.setEvent(event);

        activityNodeGraphicsInfo.setHeight(DiagramGlobals.START_ACTIVITY_SIZE);
        activityNodeGraphicsInfo.setWidth(DiagramGlobals.START_ACTIVITY_SIZE);
    }
}
