package lh.wheel.servlet;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String view;

    private Map<String, Object> objects = new HashMap<String, Object>();

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Object> objects) {
        this.objects = objects;
    }

    public void addObject(String name, Object object) {
        objects.put(name, object);
    }
}
