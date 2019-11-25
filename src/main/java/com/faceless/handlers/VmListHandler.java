package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.containers.VirtualMachine;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

public class VmListHandler extends RequestHandler
{

	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("GET", request, response))
			return;

		String login = request.getArgumentValue("login");
		ResultSet rs = Application.server.database.executeQuery("SELECT owner,\n" +
																"       vmname,\n" +
																"       cpuvendor,\n" +
																"       cpufrequency,\n" +
																"       cpucorecount,\n" +
																"       ramvolume,\n" +
																"       hddvolume,\n" +
																"       monitor,\n" +
																"       os\n" +
																"from vms\n" +
																"WHERE owner = '" + login + "';");

		StringBuilder        result = new StringBuilder();
		List<VirtualMachine> vms    = VirtualMachine.getAllVirtualMachines(rs);

		int i = 1;
		for (VirtualMachine vm : vms)
		{
			result.append(vm.getHtmlTableRow(i++));
		}

		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(result.toString());
	}
}
