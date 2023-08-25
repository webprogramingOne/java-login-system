package tech.candra.templates;


public class Template {

    public Boolean status;
    public String message;
    public Object data;

    public Template() {}

    public Template(Boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}