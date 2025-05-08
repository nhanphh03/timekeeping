package com.proton.face_enroll.utils;


import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpUtil {
    private static HttpClient client;

    public HttpUtil() {
    }

    private static HttpClient createClient() throws Exception {
        TrustStrategy ts = (arg0, arg1) -> true;
        SSLContext sslContext = (new SSLContextBuilder()).loadTrustMaterial(null, ts).build();
        int timeout = '\uea60';
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).
                setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
        return HttpClients.custom().setSSLContext(sslContext).
                setDefaultRequestConfig(config).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    }

    public static HttpClient getClient() throws Exception {
        if (client == null) {
            client = createClient();
        }

        return client;
    }
}
