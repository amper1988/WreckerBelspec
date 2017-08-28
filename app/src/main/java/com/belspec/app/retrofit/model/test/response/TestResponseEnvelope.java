package com.belspec.app.retrofit.model.test.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")

public class TestResponseEnvelope {
    @Element(name = "Header", required = false)
    String header;

    @Element(name = "return")
    @Path("Body/TestConnectionsResponse")
    TestResponseData testResponseData;

    public TestResponseData getTestData() {
        return testResponseData;
    }
}
