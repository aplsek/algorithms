package algorithms;

public class Regex {

	
	
	public boolean regex(String str, String regex) {
		
		State start = new State();
		buildAutomaton(start,regex);
		
		State cur = start;
		for (int i = 0 ; i < str.length();i++) {
			if (cur.isDollar && i != 0) {
				break;
			}
			char c = str.charAt(i);
			pln("char =" + c + ", state=" + cur.c);
			cur.print();
			
			State st = cur.get(c);
			if (st == null) {
				cur = start;
			} else {
				cur = st;
				if (cur.accepting) {
					pln("Match: idx" + i);
					break;
				}
				
			}
					
		}
		if (cur.c == '^') {
			pln("Match: idx end!" );
		}
		
		return true;
	}
	
	public static final void pln(String str) {
		System.out.println(str);
	}
	
	public State buildAutomaton(State start,String regex) {
		
		State cur = start;
		int i = 0;
		while (i < regex.length()) {
			char c = regex.charAt(i);
			if (c == '*')  {
				cur.add(cur);
			} else if (c == '$') { 
				cur.isDollar = true;
			} else if (c == '?') { 
				// skip this char
			}
			else {
				State st = new State(c);
				cur.add(st);
				cur = st;
			}
			i++;
		}
		cur.accepting = true;
		
		return start;
	}
	
	public static final void main(String[] args) {
		Regex reg = new Regex();
		reg.regex("zzzabbbcd","ab*c");
	}
	
}


class State {
	char c;
	State[] transitions;
	boolean accepting = false;
	boolean isDollar = false;
	
	public State() {
		boolean isDollar = false;
	}
	
	public State (char cc) {
		c = cc;
	}
	
	public void add (State nn) {
		if (transitions == null) {
			transitions = new State[1];
			transitions[0] = nn;
		}
		else {
			State[] n = new State[transitions.length+1];
			int i = 0;
			for (State st : transitions) {
				n[i] = st;
				i++;
			}
			n[i] = nn;
			transitions = n;
		}
	}
	
	public State get (char cc) {
		if (transitions == null) return null;
		for (State st : transitions) {
			if (st.c == cc)
				return st;
		}
		return null;
	}
	
	public void print(){
		pln("State = " + c);
		if (transitions == null) return;
		for (State st : transitions) {
			pln("\t\t tr="+ st.c);
		}
	}
	

	public static final void pln(String str) {
		System.out.println(str);
	}
	
}
