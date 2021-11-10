package diagram.builders;

import diagram.xpdl.Transition;
import diagram.xpdl.TransitionRestriction;
import lombok.Getter;
import utils.DiagramGlobals;

import java.util.ArrayList;

public class ActivityBuilder extends BaseActivityBuilder {

    @Getter
    private final TransitionRestriction transitionRestriction;

    private final ArrayList<String> transitions = new ArrayList<>();
    private int joins = 0;

    public ActivityBuilder(String id, String name, String owner, String performer, Integer position) {
        super(id, name, owner, position);

        activityNodeGraphicsInfo.setWidth(DiagramGlobals.ACTIVITY_WIDTH);
        activityNodeGraphicsInfo.setHeight(DiagramGlobals.ACTIVITY_HEIGHT);

        this.offsetCoordinates(DiagramGlobals.ACTIVITY_WIDTH/-2, DiagramGlobals.ACTIVITY_HEIGHT/-4);

        activity.getPerformersList().add(performer);

        transitionRestriction = new TransitionRestriction();
    }
    
    public void setPerformer(String performer) {
        activity.getPerformersList().clear();
        activity.getPerformersList().add(performer);
    }

    public void addTransition(Transition transition) {
        // Adjusts joins and splits with every added transition
        if (transition.getTo().equals(this.activity.getId())) {
            joins++;
            if (joins > 1) transitionRestriction.setJoin("Exclusive");
        }
        else {
            transitions.add(transition.getId());
            if (transitions.size() > 1) transitionRestriction.setSplit("Parallel", transitions);
        }
        if (joins > 1 || transitions.size() > 1) {
            activity.getTransitionRestrictionsList().add(transitionRestriction);
        }
    }

    public void removeTransition(Transition transition) {
        // Adjusts joins and splits with every removed transition
        if (transition.getTo().equals(this.activity.getId())) {
            joins--;
            if (joins < 2) transitionRestriction.setJoin(null);
        }
        else {
            transitions.remove(transition.getId());
            if (transitions.size() < 2) transitionRestriction.setSplit(null, null);
        }
        if (joins < 2 && transitions.size() < 2) activity.getTransitionRestrictionsList().remove(transitionRestriction);
    }
}
