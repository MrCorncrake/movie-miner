package diagram.builders;

import diagram.xpdl.TransitionRestriction;
import diagram.xpdl.restrictions.Join;
import diagram.xpdl.restrictions.Split;
import lombok.Getter;

public class ActivityBuilder extends BaseActivityBuilder {

    @Getter
    private final TransitionRestriction transitionRestriction;

    public ActivityBuilder(String id, String name, String owner, String performer, Integer position) {
        super(id, name, owner, position);

        activityNodeGraphicsInfo.setWidth(Globals.ACTIVITY_WIDTH);
        activityNodeGraphicsInfo.setHeight(Globals.ACTIVITY_HEIGHT);

        this.offsetCoordinates(Globals.ACTIVITY_WIDTH/-2, Globals.ACTIVITY_HEIGHT/-4);

        activity.getPerformersList().add(performer);

        transitionRestriction = new TransitionRestriction();
        transitionRestriction.setJoin(new Join("Exclusive"));
        activity.getTransitionRestrictionsList().add(transitionRestriction);
    }
    
    public void setPerformer(String performer) {
        activity.getPerformersList().clear();
        activity.getPerformersList().add(performer);
    }

    public void addTransitionRef(String transitionId) {
        if(transitionRestriction.getSplit() == null) {
            transitionRestriction.setSplit(new Split("Parallel"));
        }
        transitionRestriction.getSplit().addTransitionRef(transitionId);
    }

    public void removeTransitionRef(String transitionId) {
        transitionRestriction.getSplit().removeTransitionRef(transitionId);
        if(transitionRestriction.getSplit().getTransitionRefsList().isEmpty()) {
            transitionRestriction.setSplit(null);
        }
    }
}
