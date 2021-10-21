package diagram;

import diagram.restrictions.Join;
import diagram.restrictions.Split;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

@Setter
@NoArgsConstructor
public class TransitionRestriction {

    private Split split;
    private Join join;

    @XmlElement(name = "Split", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Split getSplit() {
        return split;
    }

    @XmlElement(name = "Join", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public Join getJoin() {
        return join;
    }
}
