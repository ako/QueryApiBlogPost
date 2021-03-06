// This file was generated by Mendix Modeler 7.17.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package csvservices.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;

public class Microflows
{
	// These are the microflows for the CsvServices module
	public static boolean mF_StartCsvServices(IContext context)
	{
		try
		{
			Map<java.lang.String, Object> params = new HashMap<java.lang.String, Object>();
			return (java.lang.Boolean)Core.execute(context, "CsvServices.MF_StartCsvServices", params);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}
	public static boolean mF_StartCsvServices_DefaultRole(IContext context, java.lang.String _user_Role)
	{
		try
		{
			Map<java.lang.String, Object> params = new HashMap<java.lang.String, Object>();
			params.put("User_Role", _user_Role);
			return (java.lang.Boolean)Core.execute(context, "CsvServices.MF_StartCsvServices_DefaultRole", params);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}
}