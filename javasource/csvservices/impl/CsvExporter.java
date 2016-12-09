package csvservices.impl;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.IMendixObjectMember;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ako on 2/10/2015.
 */
public class CsvExporter {
    private static ILogNode logger = Core.getLogger(CsvExporter.class.getName());

    public void entityToCsv(IContext context, Writer writer, String moduleName, String entityName) throws CoreException, IOException {
        int recordIdx = 0;
        int pageSize = 10;
        int offset = 0;
        long lastId = 0;

        String xpath = "//" + moduleName + "." + entityName;
        /*
         * Determine number of records to fetch
         */

        long recordCount = Core.retrieveXPathQueryAggregate(context, "count(" + xpath + ")");
        LinkedHashMap<String, String> sortIdAsc = new LinkedHashMap<String, String>();
        sortIdAsc.put("ID", "Asc");

        /*
         * number of records may have changed during last fetch, check if have retrieved full pageSize of records
         * otherwise we have already fetched all records
         */
        while ((recordIdx < recordCount) && (recordIdx % pageSize == 0)) {
            /*
             * Retrieve small set of records to avoid running out of memory
             */
            String offsetXpath = xpath;
            if (lastId > 0) {
                offsetXpath += "[ID > 'ID_" + lastId + "']";
            }
            List<IMendixObject> objects = Core.retrieveXPathQuery(context, offsetXpath, pageSize, 0, sortIdAsc, 2);
            /*
             * write one line per object, comma separated values
             */
            for (IMendixObject obj : objects) {
                Map<String, ? extends IMendixObjectMember<?>> members = obj.getMembers(context);
                int memberCount = members.size();
                /*
                 * Before first record, print header names
                 */
                if (recordIdx == 0) {
                    String prefix = "Id,";
                    for (IMendixObjectMember objMember : members.values()) {
                        /*
                         * Include name of attribute. In case of association, remove module name
                         */
                        writer.write(prefix +
                                (objMember.getName().indexOf(".") > -1
                                        ? objMember.getName().split("[.]")[1]
                                        : objMember.getName()));
                        prefix = ",";
                    }
                    writer.write("\n");
                }
                /*
                 * Output all attributes
                 */
                String prefix = "";
                writer.write(String.format("%d,", obj.getId().toLong()));
                for (IMendixObjectMember objMember : members.values()) {
                    writeAttributeValue(context, writer, prefix, objMember);
                    prefix = ",";
                }
                writer.write("\n");
                recordIdx++;
                lastId = obj.getId().toLong();
            }
            offset += pageSize;
        }
    }

    private void writeAttributeValue(IContext context, Writer writer, String prefix, IMendixObjectMember objMember) throws IOException {
        Object value = objMember.getValue(context);
        String csvValue = "";
        if (value == null) {
            csvValue = "";
        } else {
            logger.debug("value: " + value + "," + value.getClass());
            if (value.getClass().equals(Date.class)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                csvValue = dateFormat.format(value);
            } else if (value.getClass().equals(String.class)) {
                csvValue = String.format("\"%s\"", ((String)value).replace("\"","\"\""));
            } else if (value.getClass().equals(ArrayList.class)) {
                csvValue = "";
                logger.debug("arrayList.size: " + ((ArrayList) value).size());
                ListIterator iter = ((ArrayList) value).listIterator();
                String arrayPrefix = "";
                while (iter.hasNext()) {
                    IMendixIdentifier o = (IMendixIdentifier) iter.next();
                    logger.debug(" - arraylist: " + o);
                    csvValue += arrayPrefix + o.toLong();
                    arrayPrefix = ";";
                }
                csvValue += "";
            } else if (value.getClass().equals(Integer.class)
                    || value.getClass().equals(Long.class)
                    ) {
                logger.debug("int/long = " + value);
                csvValue = String.format("%d", value);
            } else if (value.getClass().equals(IMendixIdentifier.class)) {
                logger.debug("mxid: " + ((IMendixIdentifier) value).toLong());
                csvValue = String.format("%d", ((IMendixIdentifier) value).toLong());
            } else if (value.getClass().equals(Boolean.class)) {
                logger.debug("boolean = " + value);
                csvValue = String.format("%b", value);
            } else {
                csvValue = String.format("%g", value);
            }
        }
        writer.write(prefix + csvValue);
    }
}
