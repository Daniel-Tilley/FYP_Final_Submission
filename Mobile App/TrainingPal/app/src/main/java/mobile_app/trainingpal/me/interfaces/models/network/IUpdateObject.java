package mobile_app.trainingpal.me.interfaces.models.network;

import java.util.List;

public interface IUpdateObject {

    public List<Object> getMemberKeys();

    public void setMemberKeys(List<Object> memberKeys);

    public List<Object> getMemberValues();

    public void setMemberValues(List<Object> memberValues);
}
