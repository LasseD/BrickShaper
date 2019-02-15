package compiler;

import java.util.*;

public interface Expression {
	public Object eval(Map<String,Variable> state);
}
