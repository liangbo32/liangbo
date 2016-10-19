package obor.Util;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 公式解析
 * 
 * @author zs
 *
 */
public class FormulaUtil {
	//sum
	private static String sum(String[] strs){
		BigDecimal big=new BigDecimal(0);
		if(null!=strs&&strs.length>0){
			for(String str:strs){
				BigDecimal temp=new BigDecimal(str);
				big=big.add(temp);
			}
		}
		return String.valueOf(big.doubleValue());
	}
	// 加
	private static String add(String[] strs) {
		BigDecimal big=new BigDecimal(0);
		if(null!=strs&&strs.length>0){
			for(String str:strs){
				BigDecimal temp=new BigDecimal(str);
				big=big.add(temp);
			}
		}
		return String.valueOf(big.doubleValue());
	}

	// 减
	private static String subtract(String[] strs) {
		BigDecimal big=new BigDecimal(0);
		if(null!=strs&&strs.length>0){
			big=new BigDecimal(strs[strs.length-1]);
			for(int i=strs.length-2;i>=0;i--){
				BigDecimal temp=new BigDecimal(strs[i]);
				big=big.subtract(temp);
			}
		}
		return String.valueOf(big.doubleValue());
	}

	// 乘
	private static String multiply(String[] strs) {
		BigDecimal big=new BigDecimal(0);
		if(null!=strs&&strs.length>0){
			big=new BigDecimal(strs[strs.length-1]);
			for(int i=strs.length-2;i>=0;i--){
				BigDecimal temp=new BigDecimal(strs[i]);
				big=big.multiply(temp);
			}
		}
		return String.valueOf(big.doubleValue());
	}

	// 除
	private static String divide(String[] strs) {
		BigDecimal big=new BigDecimal(0);
		if(null!=strs&&strs.length>0){
			big=new BigDecimal(strs[strs.length-1]);
			for(int i=strs.length-2;i>=0;i--){
				BigDecimal temp=new BigDecimal(strs[i]);
				big=big.divide(temp,10,BigDecimal.ROUND_HALF_UP);
			}
		}
		return String.valueOf(big.doubleValue());
	}
	/**
	 * 保留小数
	 * @param strs 0:保留位数   1:数值 
	 * @return
	 */
	private static String round(String[] strs) {
		BigDecimal b = new BigDecimal(strs[1]);
		BigDecimal bb = new BigDecimal(1);
		String result=String.valueOf(b.divide(bb,Integer.parseInt(strs[0]), BigDecimal.ROUND_HALF_UP).doubleValue());
		
		//需要保留的位数
		int size=Integer.parseInt(strs[0]);
		
		//结果的小数位
		String[] st=result.split("[.]");
		int nowsize=st.length==2?st[1].length():0;
		if(nowsize!=size){
			for(int i=0;i<size-nowsize;i++){
				result+="0";
			}
		}
		return result;
	}
	/**
	 * 解析公式，算出结果
	 * 
	 * @param str
	 *            传过来的公式
	 * @return
	 */
	public static String analysis(String str) {
		Stack<String> methodList = new Stack<String>(); // 保存方法名
		Stack<Character> bracketList = new Stack<Character>();// 保存对应括号
		Stack<String> numList = new Stack<String>(); // 保存数字
		Stack<Integer> countList = new Stack<Integer>(); // 保存参数个数

		StringBuffer zm = new StringBuffer(); // 添加字母
		StringBuffer num = new StringBuffer(); // 添加数字
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') {
				zm.append(c); // 拼凑方法名
			} else if (c == '(') {
				methodList.push(zm.toString()); // 方法名拼凑完成,添加到栈
				countList.push(0);
				zm.setLength(0); // 放入方法,并清空临时串
				bracketList.push(')');
			} else if (c >= '0' && c <= '9'||c=='.') {
				num.append(c); // 拼凑数字
			} else if (num.length() != 0) {
				numList.push(num.toString()); // 数字名拼凑完成,添加到栈
				countList.push(countList.pop()+1); // 参数个数加1
				num.setLength(0);
			}
			if (c == ')') {
				String m = methodList.pop(); // 得到方法
				String[] n = new String[countList.pop()]; // 依参数个数开辟数组
				for (int j = 0; j < n.length; j++) { // 得到每个参数
					n[j] = String.valueOf(numList.pop());
				}
				bracketList.pop(); // 去一层括号
				if ("add".equals(m)) {
					numList.push(add(n));
				} else if ("subtract".equals(m)) { // 减
					numList.push("" + subtract(n));
				} else if ("multiply".equals(m)) { // 乘
					numList.push("" + multiply(n));
				} else if ("divide".equals(m)) { // 除
					numList.push("" + divide(n));
				} else if("round".equals(m)){
					numList.push("" + round(n));
				} else if("sum".equals(m)){
					numList.push("" + sum(n));
				}
				if (null!=countList&&countList.size() != 0) { // 如果不是最外层
					countList.push(countList.pop() + 1);
				}
			}
		}
		return numList.pop(); // 返回最后的结果数据
	}
	public static void main(String[] args) {
		System.out.println(FormulaUtil.analysis("divide(multiply(subtract(add(11.11,1.1),1.1,1.11),1),5.0)"));
	}
}
