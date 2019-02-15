package compiler;

import java.io.*;
import shapes.vector.*;

/**
 * Compiler for the interpreted language "shape". whitespace [ \t] is ignored 
 * file := line*
 * line := statement? comment? '\n'
 * comment := '//' .*
 * statement := assignment | fun_assignment | expression
 * 
 * assignment := variable '=' expression
 * fun_assignment := variable '=' arg_list '->' expression
 * expression := arithmetic | fun_call | block | branch | loop
 * arg_list := '(' variable_list ')'
 * variable_list = variable | variable ',' variable_list
 * variable := '[a-zA-Z_]+'
 *
 * arithmetic := variable | arithmetic binary_operator arithmetic | unary_operator arithmetic | '(' arithmetic ')'
 * binary_operator := '[+-/*%<>]|([><=]=)'
 * unary_operator := '[-!]'
 * fun_call := variable arg_list
 *
 * block := '{' '\n' block_line* '}'
 * block_line := block_statement? comment? '\n'
 * block_statement := assignment | expression
 * 
 * branch := 'if' '(' expression ')' block_statement rebranch?
 * rebranch := ('else' 'if' '(' expression ')' block_statement)* ('else' block_statement)?
 * 
 * loop := for_loop | while_loop
 * for_loop := 'for' '(' assignment ';' (arithmetic|fun_call) ';' assignment ')' block 
 * while_loop := 'while' '(' (arithmetic|fun_call) ')' block 
 * 
 * Example:
 * a = Ball(0.1, 0.1, 0.1)
 * moveto(0.5,0.5,0.5)
 * b = (c) -> {
 *   length(a)
 * }
 * show(a)
 * 
 * Design (Classes and interfaces):
 * Value: int, real, shape, function
 * Expression: 
 * Function: Params(list of vars), assignments/expressions, value(last expression).
 * Shape: occupies,getSize(vector),getStart(vector)
 * Line: Interface w. matches(String) method
 * 
 * Predefined constants:
 * true: 1
 * false: 0
 * 
 * Predefined functions:
 * show(shape) // Adds shape to scene
 * ball() // New ball with center ½,½,½, size 1x1x1
 * Box() // New box like ball
 * cylinder() // ^^
 * shape(fun(x,y,z)->int)
 * 
 * rotate(shape,x,y,z) // Returns rotated shape. shape.rotate(x,y,z) is shorthand for shape = rotate(shape,x,y,z)
 * stretch(shape,x,y,z) // stretches ^^
 * move(shape,x,y,z) // moves ^^
 * mirrorX(shape), mirrorY(shape), mirrorZ(shape)
 * invert(shape)
 * subtract(shape)
 * add(shape)
 * intersect(shape)
 * union(shape)
 */
public class Compiler {
	private int blockLevel;
	
	public Compiler() {
		blockLevel = 0;
	}
	
	public VectorShape parseFile(File f) throws IOException {
		blockLevel = 0;
		BufferedReader reader = new BufferedReader(new FileReader(f));
		VectorShape scene = new EmptyShape();
		
		String line;
		while((line = reader.readLine()) != null) {
			
		}
		
		return scene;		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
