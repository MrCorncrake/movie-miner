package utils;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class XPDLNamespaceMapper extends NamespacePrefixMapper {

    private static final String XPDL_PREFIX = "xpdl";
    private static final String XPDL_URI = "http://www.wfmc.org/2008/XPDL2.1";

    private static final String XSI_PREFIX = "xsi";
    private static final String XSI_URI = "http://www.w3.org/2001/XMLSchema-instance";

    @Override
    public String getPreferredPrefix(String uri, String suggestion, boolean requirePrefix) {
        if (XPDL_URI.equals(uri)) {
            return XPDL_PREFIX;
        }
        else if (XSI_URI.equals(uri)) {
            return XSI_PREFIX;
        }
        else return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { XPDL_URI, XSI_URI };
    }

}
