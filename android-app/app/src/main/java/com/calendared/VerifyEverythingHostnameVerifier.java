package com.calendared;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class VerifyEverythingHostnameVerifier implements HostnameVerifier {

    public boolean verify(String string, SSLSession sslSession) {
        return true;
    }
}