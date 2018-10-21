package lh.wheel.helper;

public class Handler {
    private String method;
    private String url;

    public Handler(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Handler) {
            Handler handler = (Handler)object;
            if(handler.getMethod().equals(method) && handler.getUrl().equals(url))
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return method.hashCode() + url.hashCode();
    }
}
