package csen1002.main.task4;
import java.util.*;
/**
 * @name Fakhreldin Hussein Soliman Tarabay
 * @id 43-11215
 * @labNumber 19
 */
public class FDFA {
    public String[] states;
    public String accept_states;

    public FDFA(String description) {
        this.states = description.split(";");
        this.accept_states = (states[states.length - 1]).split("#")[1];
    }
    public String run(String input) {
        Stack stk = new Stack();
        HashMap<String, String> map = new HashMap<>();
        String[] ninput = input.split("");
        String curr_state = "0";
        map.put(curr_state, ninput[0] + "," + states[0].split(",")[3]);
        stk.push(map);
        for (int i = 0; i < input.length(); i++) {
            map = new HashMap<>();
            int transition = Integer.parseInt(ninput[i]);
            curr_state = states[Integer.parseInt(curr_state)].split("#")[0].split(",")[transition + 1];
            String action = states[Integer.parseInt(curr_state)].split("#")[0].split(",")[3];
            map.put(curr_state, input.substring(0, i + 1) + "," + action);
            stk.push(map);
        }
        if (!stk.empty()) map = (HashMap) stk.pop();
        HashMap<String, String> finalState = (HashMap) map.clone();
        if (accept_states.contains(finalState.keySet().iterator().next()))
            return finalState.values().iterator().next() + ";";
        for (int i = 0; i < input.length(); i++) {
            if (stk.empty()) return finalState.values().iterator().next() + ";";
            map = (HashMap) stk.pop();
            if (accept_states.contains(map.keySet().iterator().next())) {
                int t = map.values().iterator().next().split(",")[0].length();
                return map.values().iterator().next() + ";" + run(input.substring(t));
            }
        }
        return finalState.values().iterator().next() + ";";
    }
}