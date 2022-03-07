package csen1002.main.task1;
/**
 * Write your info here
 * 
 * Fakhreldin Hussein Soliman Tarabay
 * 43-11215
 * TODO
 */
public class DFA {
	public String[] states;
	public String accept_states;
	public String start_state;

	public DFA(String description) {
		this.states = description.split(";");
		this.accept_states = (states[states.length-1]).split("#")[1];
		this.start_state = "0";
	}

	public boolean run(String input) {
		String[] ninput = input.split("");
		String curr_state = "0";
		for(int i = 0;i<input.length();i++){
			int transition = Integer.parseInt(ninput[i]);
			curr_state = states[Integer.parseInt(curr_state)].split("#")[0].split(",")[transition+1];
		}
		if(accept_states.contains(curr_state)){
			return true;
		}
		return false;
	}
}
