package action.bean;

/**
 * Created by USER on 09.06.2016.
 */
public class ActionResponse {

    private final String url;
    private final ActionType actionType;

    public ActionResponse(String url, ActionType actionType) {
        this.url = url;
        this.actionType = actionType;
    }

    public String getUrl() {
        return url;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
