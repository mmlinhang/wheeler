package lh.wheel.servlet;

import java.util.Map;

public class Param {
    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String get(String parameterName) {
        return params.get(parameterName);
    }
}
