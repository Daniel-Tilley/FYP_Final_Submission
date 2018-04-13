package mobile_app.trainingpal.me.models.network;

import mobile_app.trainingpal.me.interfaces.models.network.IHeader;

public class Header implements IHeader{

    private String headerKey;
    private String headerValue;

    public Header(String headerKey, String headerValue) {
        this.headerKey = headerKey;
        this.headerValue = headerValue;
    }

    @Override
    public String getHeaderKey() {
        return headerKey;
    }

    @Override
    public String getHeaderValue() {
        return headerValue;
    }
}
