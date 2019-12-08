package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.containers.VirtualMachine;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class RemoveVMHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("DELETE", request, response))
			return;

		String         login = request.getArgumentValue("login");
		int            index = Integer.parseInt(request.getArgumentValue("index"));
		VirtualMachine vm    = VirtualMachine.getVmByUserAndId(login, index, Application.server.database);

		vm.removeFromDatabase(login);

		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse("");
	}
}
