package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.Utilities;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class ClickButtonHandler extends RequestHandler
{
	private int diff;

	public ClickButtonHandler(int diff)
	{
		this.diff = diff;
	}

	@Override
	public void handle(Request request, Response response, PropertyContainer container) throws IOException
	{
		if (!assertRightMethod("POST", request, response))
			return;
		int counter = Integer.parseInt(container.getProperty("counter"));
		container.setProperty("counter", counter + diff);
		Utilities.applyPropertyContainer(Application.server.mainPageDocument, container);
		response.setStatus("200");
		response.setDescription("OK");
		response.writeResponse(Application.server.mainPageDocument.toString());
	}
}
