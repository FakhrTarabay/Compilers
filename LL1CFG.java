package csen1002.main.task7;

import java.util.*;

/**
 * @name Fakhreldin Hussein Soliman Tarabay
 * @id 43-11215
 * @labNumber 19
 */
public class LL1CFG {
	String [] rules;
	String [] first;
	String [] follow;
    ArrayList alpha;
    HashSet terminals;
	public LL1CFG(String description) {
		this.rules = description.split("#")[0].split(";");
		this.first = description.split("#")[1].split(";");
		this.follow = description.split("#")[2].split(";");
		this.alpha = new ArrayList();
        for (int i = 0; i < rules.length; i++) {
            alpha.add(rules[i].split(",")[0]);
        }
        this.terminals = new HashSet();
	}
	public String parse(String input) {
		input+="$";
		String [] in = input.split("");
		for(int o = 0;o<in.length;o++){
			terminals.add(in[o]);
		}
		HashMap table = createTable();
		Stack pda = new Stack();
		String res = "";
		String t = "";
		pda.push("$");
		pda.push("S");
        for(int i = 0;i<in.length;){
        	if(in[i].equals("$")){
        		if(pda.size()>2){
					return res+(t+stackToString((Stack)pda.clone())+",ERROR");
				}
        		String t1 = pda.pop()+"";
        		if(t1.equals("$")){
					res+=t;
				}else{
					res+=(t+t1);
					res+=","+t;
				}
        		break;
			}
        	while(true){
				String currItem = pda.peek()+"";
				if(currItem.equals(in[i])){
					t+=currItem;
					pda.pop();
					i++;
					break;
				}else{
					if(table.get(currItem+in[i])==null){
						return res+(t+stackToString((Stack)pda.clone())+",ERROR");
					}
					res+=(t+stackToString((Stack)pda.clone()))+",";
					pda = reverseAndPut((String)table.get(pda.pop()+in[i]+""),(Stack)pda.clone());
					if(pda.peek().equals(in[i])){
						res+=(t+stackToString((Stack)pda.clone()))+",";
					}
				}
			}
		}
		return res;
	}
    public HashMap createTable(){
	    HashMap res = new HashMap();
	    boolean flag = false;
	    for(int i = 0 ;i<alpha.size();i++){
			for(int j = 1;j<rules[i].split(",").length;j++){
				String [] section = first[i].split(",")[j].split("");
				for(int k = 0;k<section.length;k++){
					if(section[k].equals("e")){
						flag = true;
						continue;
					}
					res.put((String)alpha.get(i)+section[k],rules[i].split(",")[j]);
				}
			}
			if(flag){
				flag = false;
				Iterator<String> it = terminals.iterator();
				while (it.hasNext()) {
					String t = it.next();
					res.put((String)alpha.get(i)+t,res.get((String)alpha.get(i)+t)!=null?res.get((String)alpha.get(i)+t):"");
				}
			}
        }
		return res;
    }
    public String stackToString(Stack x){
		int y = x.size();
		String res = "";
		for(int i = 0;i<y-1;i++){
			res+=x.pop();
		}
		return res;
	}
	public Stack reverseAndPut(String x ,Stack y){
		char[] try1 = x.toCharArray();
		for (int i = try1.length - 1; i >= 0; i--){
			y.push(try1[i]);
		}
		return y;
	}
}
