package Ex1;

public class ComplexFunction implements complex_function {

	private function f1, f2;
	private Operation op;
	public ComplexFunction(){
		return;
	}
	public ComplexFunction(function f1, function f2, Operation op) {
		this.f1 = f1;
		this.f2 = f2;
		this.op = op;
	}
// 	plus(x, x^2 + 3)
	public ComplexFunction(String opString,function f1, function f2) {
		this.op = operationStringToOperation(opString);
	}
	@Override
	public double f(double x) {
		double f1Value = f1.f(x);
		double f2Value = f2 != null ? f2.f(x) : -1;
		
		switch(op) {
			case Plus:
				return f1Value + f2Value;
			case Times:
				return f1Value * f2Value;
			case Divid:
				return f1Value/f2Value;
			case Max:
				if(f1Value>f2Value) {
					return f1Value;
				}else {
					return f2Value;
				}
			case Min:
				if(f1Value<f2Value) {
					return f1Value;
				}else {
					return f2Value;
				}
			case Error:
				return -1;
			case None:				
			default:
				// handle null
				return f1Value;
		}
	}
	
	@Override
	public function initFromString(String s) { // plus(Times(Plus(x^2, x),x^7),x+5) 
		String operationSubstring = s.substring(0, s.indexOf("("));
		Operation op = operationStringToOperation(operationSubstring);
		
		String functionsSubstring = s.substring(s.indexOf("(") + 1, s.lastIndexOf(")"));
		
		int outerCommaIndex = findOuterComma(functionsSubstring);
		String leftFunctionString = functionsSubstring.substring(0, outerCommaIndex);
		String rightFunctionString = functionsSubstring.substring(outerCommaIndex + 1);
		
		function left;
		function right;
		
		
		if(leftFunctionString.contains(",")) {
			left = initFromString(leftFunctionString);
		} else {
			left = new Polynom(leftFunctionString);
		}
		
		if(rightFunctionString.contains(",")) {
			right = initFromString(rightFunctionString);
		} else {
			right = new Polynom(rightFunctionString);
		}

		
		return new ComplexFunction(left, right, op);
	}
	
	private static Operation operationStringToOperation(String operationString) {
		return Operation.valueOf(Character.toUpperCase(operationString.charAt(0)) + operationString.substring(1));
	}
	
	private int findOuterComma(String complexFunctionString) {
		int openParan = 0;
		
		for(int i = 0; i < complexFunctionString.length(); i++) {
			if(complexFunctionString.charAt(i)== '(') {
				++openParan;
			} else if(complexFunctionString.charAt(i) == ')') {
				--openParan;
			} else if (complexFunctionString.charAt(i) == ',') {
				if(openParan == 0) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	@Override
	public function copy() {
		// TODO Auto-generated method stub

		return new ComplexFunction(this.f1.copy(),this.f2.copy(),this.op);
	}
	//Plus(x, x^2) +  f1 = left= Plus(x, x^2)  right = Plus(x, x^2) op = +
	@Override
	public void plus(function f3) {
		// TODO Auto-generated method stub
		this.f1 = this.copy();
		this.f2 = f3;
		this.op = Operation.Plus;
	}
	@Override
	public void mul(function f3) {
		// TODO Auto-generated method stub
		this.f1=this.copy();
		this.f2=f3;
		this.op=Operation.Times;
	}

	@Override
	public void div(function f3) {
		// TODO Auto-generated method stub
		this.f1=this.copy();
		this.f2=f3;
		this.op=Operation.Divid;
	}

	@Override
	public void max(function f3) {
		// TODO Auto-generated method stub
		this.f1=this.copy();
		this.f2=f3;
		this.op=Operation.Max;
		
	}

	@Override
	public void min(function f3) {
		// TODO Auto-generated method stub
		this.f1=this.copy();
		this.f2=f3;
		this.op=Operation.Min;
		
	}

	@Override
	public void comp(function f3) {
		// TODO Auto-generated method stub
		this.f1=this.copy();
		this.f2=f3;
		this.op=Operation.Comp;
		
	}

	@Override
	public function left() {
		
		return f1;
	}

	@Override
	public function right() {
		// TODO Auto-generated method stub
		return f2;
	}

	@Override
	public Operation getOp() {
		// TODO Auto-generated method stub
		return this.op;
	}
	
	@Override
	public String toString() {
		if(this.f2 != null) {
			return this.op.name().toLowerCase() + "(" + this.f1.toString() + "," + this.f2.toString() + ")";
		} else {
			return this.f1.toString();
		}
	}
}

