package qow.framework.util;

import java.util.ArrayList;
import java.util.List;

public class ActionKeyManager {
    List<ActionKey> keyList;
    public ActionKeyManager(){
        keyList = new ArrayList<>();
    }
    public void add(ActionKey actionKey){
        keyList.add(actionKey);
    }
    public void remove(int index){
        keyList.remove(index);
    }
    public void remove(ActionKey actionKey){
        keyList.remove(actionKey);
    }
    public void clear(){
        keyList.clear();
    }
}
