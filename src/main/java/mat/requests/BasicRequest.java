package mat.requests;

public class BasicRequest
{
	// STOLEN FROM VANILLA ENHACEMENTS
    public static String getWebsite(final String url) {
        final HttpRequest req = new HttpRequest(url);
        return req.getResponse(req.buildConnection());
    }
}
