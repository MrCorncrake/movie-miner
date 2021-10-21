package diagram;

import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.Date;

@Setter
public class WorkflowProcess {

    private String Id;
    private String Name;
    private ProcessHeader processHeader;
    private ArrayList<Activity> activitiesList;
    private ArrayList<Transition> transitionsList;

    public WorkflowProcess() {
        this.processHeader = new ProcessHeader();
    }

    @XmlAttribute(name="Id")
    public String getId() {
        return Id;
    }

    @XmlAttribute(name="Name")
    public String getName() {
        return Name;
    }

    @XmlElement(name = "ProcessHeader", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public ProcessHeader getProcessHeader() {
        return processHeader;
    }

    public ArrayList<Activity> getActivitiesList() {
        return activitiesList;
    }

    @XmlElementWrapper(name = "Activities", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "Activity", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setActivitiesList(ArrayList<Activity> activitiesList) {
        this.activitiesList = activitiesList;
    }

    public ArrayList<Transition> getTransitionsList() {
        return transitionsList;
    }

    @XmlElementWrapper(name = "Transitions", namespace="http://www.wfmc.org/2008/XPDL2.1")

    @XmlElement(name = "Transition", namespace="http://www.wfmc.org/2008/XPDL2.1")
    public void setTransitionsList(ArrayList<Transition> transitionsList) {
        this.transitionsList = transitionsList;
    }

    @Setter
    private static class ProcessHeader {
        private Date created;

        @XmlElement(name = "Created", namespace="http://www.wfmc.org/2008/XPDL2.1")
        public Date getCreated() {
            return created;
        }

        public ProcessHeader() {
            this.created = new Date(System.currentTimeMillis());
        }
    }
}
