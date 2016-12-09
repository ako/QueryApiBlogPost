package system;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.mendix.core.actionmanagement.IActionRegistrator;

@Component(immediate = true)
public class UserActionsRegistrar
{
  @Reference
  public void registerActions(IActionRegistrator registrator)
  {
    registrator.bundleComponentLoaded();
    registrator.registerUserAction(appcloudservices.actions.GenerateRandomPassword.class);
    registrator.registerUserAction(appcloudservices.actions.LogOutUser.class);
    registrator.registerUserAction(appcloudservices.actions.StartSignOnServlet.class);
    registrator.registerUserAction(csvservices.actions.CsvExportInitializeAction.class);
    registrator.registerUserAction(csvservices.actions.ImportCsvData.class);
    registrator.registerUserAction(hr.actions.CreateDateRangeList.class);
    registrator.registerUserAction(hr.actions.RetrieveAdvancedOql.class);
    registrator.registerUserAction(hr.actions.RetrieveAdvancedSql.class);
    registrator.registerUserAction(hr.actions.RetrieveAdvancedXpath.class);
    registrator.registerUserAction(system.actions.VerifyPassword.class);
  }
}
