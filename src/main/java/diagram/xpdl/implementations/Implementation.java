package diagram.xpdl.implementations;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

@Setter
public class Implementation {
    private No no;

    public Implementation() {
        no = new No();
    }

    @XmlElement(name = "No", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public No getNo() {
        return no;
    }
}
