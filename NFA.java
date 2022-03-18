package csen1002.main.task2;

import java.util.ArrayList;
/**
 * @name Fakhreldin Hussein Soliman
 * @id 43-11215
 * @labNumber 19
 */
public class NFA{
	public String[] zero;
	public String[] ones;
	public String[] epsilon;
	public String accept_states;

	public NFA(String description) {
		String[] states = description.split("#");
		this.zero = states[0].split(";");
		this.ones = states[1].split(";");
		this.epsilon = states[2].split(";");
		this.accept_states = states[3];
	}
	public boolean run(String input) {
		String[] ninput = input.split("");
		ArrayList<String> curr_state = new ArrayList<String>();
		curr_state.add("0");
		curr_state = st(curr_state);
		for(int i = 0;i<input.length();i++){
			curr_state = nd(curr_state,Integer.parseInt(ninput[i]));
			curr_state = st(curr_state);
		}
		String[] acc = accept_states.split(",");
		for(int j = 0;j<acc.length;j++) {
			if(curr_state.contains(acc[j])) {
				return true;
			}
		}
		return false;
	}
	public ArrayList<String> st(ArrayList<String> in){
		ArrayList<String> out = new ArrayList<String>();
		while (true) {
			for(int i = 0;i<in.size();i++){
				String t = in.get(i);
				if(!out.contains(t)){
					out.add(t);
				}
				for(int j = 0;j<epsilon.length;j++){
					String[] y =  epsilon[j].split(",");
					if(y[0].equals(t)){
						if(!out.contains(y[1])){
							out.add(y[1]);
						}
					}
				}
			}
			if(in.equals(out)){
				return out;
			}
			in = (ArrayList) out.clone();
		}
	}
	public ArrayList<String> nd(ArrayList<String> in ,  int transition){
		ArrayList<String> out = new ArrayList<String>();
		for(int i=0;i<in.size();i++){
			String[] m = transition==1?ones:zero;
			for(int j=0;j<m.length;j++){
				String[] t = m[j].split(",");
				if(t[0].equals(in.get(i))){
					if(!out.contains(m[j].split(",")[1])){
						out.add(m[j].split(",")[1]);
					}
				}
			}
		}
		return out;
	}
}
