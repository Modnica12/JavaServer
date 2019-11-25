package com.faceless.handlers;

import com.faceless.Application;
import com.faceless.Utilities;
import com.faceless.containers.PropertyContainer;
import com.faceless.requests.Request;
import com.faceless.requests.RequestHandler;
import com.faceless.responses.Response;

import java.io.IOException;

public class ResetingHandler extends RequestHandler
{
    private int initialNumber;

    public ResetingHandler(int initialNumber)
    {
        this.initialNumber = initialNumber;
    }

    @Override
    public void handle(Request request, Response response, PropertyContainer container) throws IOException
    {
		if (!assertRightMethod("PUT", request, response))
            return;

		container.setProperty("counter", initialNumber);
        Utilities.applyPropertyContainer(Application.server.mainPageDocument, container);
        response.setStatus("200");
        response.setDescription("OK");
        response.writeResponse(Application.server.mainPageDocument.toString());
    }
}
