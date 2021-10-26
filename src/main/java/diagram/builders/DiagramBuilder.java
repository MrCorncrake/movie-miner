package diagram.builders;

import diagram.*;
import diagram.Package;
import lombok.Getter;
import utils.DiagramGlobals;

import java.util.ArrayList;

public class DiagramBuilder {

    @Getter
    private final Package diagram;

    private final ExtendedAttribute editingTool;
    private final ExtendedAttribute editingToolVersion;
    private final ExtendedAttribute JaWEConfiguration;

    @Getter
    private final WorkflowProcess process;
    @Getter
    private final Pool pool;

    private final Boolean vertical;

    public DiagramBuilder(String id, String packageName, String diagramName, Boolean vertical) {
        this.vertical = vertical;
        diagram = new Package(id, packageName);
        diagram.setConformanceClass(new ConformanceClass());
        // Diagram attributes
        ArrayList<ExtendedAttribute> extendedAttributes = diagram.getExtendedAttributesList();
        editingTool = new ExtendedAttribute("EDITING_TOOL", "Movie-Miner");
        editingToolVersion = new ExtendedAttribute("EDITING_TOOL_VERSION", DiagramGlobals.EDITING_TOOL_VERSION);
        JaWEConfiguration = new ExtendedAttribute("JaWE_CONFIGURATION", "default");
        extendedAttributes.add(editingTool);
        extendedAttributes.add(editingToolVersion);
        extendedAttributes.add(JaWEConfiguration);
        // Main diagram classes
        process = new WorkflowProcess("Movie", diagramName);
        pool = new Pool(id + "_pool", diagramName, true, true, vertical ? "VERTICAL" : "HORIZONTAL", "Movie");
        diagram.getWorkflowProcessesList().add(process);
        diagram.getPoolsList().add(pool);
    }

    public void setEditingTool(String editingTool) {
        this.editingTool.setValue(editingTool);
    }

    public void setEditingToolVersion(String editingToolVersion) {
        this.editingToolVersion.setValue(editingToolVersion);
    }

    public void setJaWEConfiguration(String jaWEConfiguration) {
        this.JaWEConfiguration.setValue(jaWEConfiguration);
    }




}
