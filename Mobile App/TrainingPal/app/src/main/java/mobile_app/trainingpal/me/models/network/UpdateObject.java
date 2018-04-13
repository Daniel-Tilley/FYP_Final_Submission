package mobile_app.trainingpal.me.models.network;

import java.util.List;

import mobile_app.trainingpal.me.interfaces.models.network.IUpdateObject;

public class UpdateObject implements IUpdateObject{
    private List<Object> MemberKeys;
    private List<Object> MemberValues;

    public List<Object> getMemberKeys() {
        return MemberKeys;
    }

    public void setMemberKeys(List<Object> memberKeys) {
        MemberKeys = memberKeys;
    }

    public List<Object> getMemberValues() {
        return MemberValues;
    }

    public void setMemberValues(List<Object> memberValues) {
        MemberValues = memberValues;
    }
}
