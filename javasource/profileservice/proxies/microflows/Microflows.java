// This file was generated by Mendix Modeler 7.2.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package profileservice.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class Microflows
{
	// These are the microflows for the ProfileService module
	public static profileservice.proxies.UserProfile getUserProfile(IContext context, java.lang.String _openID, java.lang.String _environmentUUID, java.lang.String _environmentPassword)
	{
		try
		{
			Map<java.lang.String, Object> params = new HashMap<java.lang.String, Object>();
			params.put("OpenID", _openID);
			params.put("EnvironmentUUID", _environmentUUID);
			params.put("EnvironmentPassword", _environmentPassword);
			IMendixObject result = (IMendixObject)Core.execute(context, "ProfileService.GetUserProfile", params);
			return result == null ? null : profileservice.proxies.UserProfile.initialize(context, result);
		}
		catch (CoreException e)
		{
			throw new MendixRuntimeException(e);
		}
	}
}