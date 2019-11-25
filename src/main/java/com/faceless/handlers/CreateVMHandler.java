package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.containers.PropertyContainer;
import com.faceless.containers.VirtualMachine;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class CreateVMHandler extends RequestHandler
{
	@Override
	public void handle(Request request, Response response, PropertyContainer propertyContainer) throws IOException
	{
		if (!assertRightMethod("PUT", request, response))
			return;
		VirtualMachine vm    = new VirtualMachine();
		String         login = propertyContainer.getProperty("login");
		vm.vm_name = request.getArgumentValue("vm_name");
		vm.cpu_vendor = request.getArgumentValue("cpu_vendor");
		vm.cpu_frequency = request.getArgumentValue("cpu_frequency");
		vm.cpu_core_count = request.getArgumentValue("cpu_core_count");
		vm.ram_volume = request.getArgumentValue("ram_volume");
		vm.hdd_volume = request.getArgumentValue("hdd_volume");
		vm.monitor_enabled = request.getArgumentValue("monitor_enabled");
		vm.os = request.getArgumentValue("os");

		vm.addToDatabase(login);

		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(Application.server.loginPageDocument.toString());
	}
}
