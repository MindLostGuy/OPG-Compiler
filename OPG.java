import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class OPG {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        FileReader reader = new FileReader(file);
        int length = (int) file.length();
        char buf[] = new char[length+1];
        reader.read(buf);
        reader.close();
       new OPG().Analyze(buf);


    }

    private int INF = 1024;
    private int[][] OPmartix=new int[][]{
            {1,-1,-1,-1,1},
            {1,1,-1,-1,1,},
            {1,1,INF,INF,1},
            {-1,-1,-1,-1,0},
            {1,1,INF,INF,1}
    };
    private char[] OPs={'+','*','i','(',')'};
    private Stack<Character> stack=new Stack();

    int Relat(char a,char b)
    {
        int aint,bint;
        switch (a){
            case '+':aint=0;break;
            case '*':aint=1;break;
            case 'i':aint=2;break;
            case '(':aint=3;break;
            case ')':aint=4;break;
            default:return -INF;
        }
        switch (b){
            case '+':bint=0;break;
            case '*':bint=1;break;
            case 'i':bint=2;break;
            case '(':bint=3;break;
            case ')':bint=4;break;
            default:return -INF;
        }
        return OPmartix[aint][bint];
    }

    boolean isOPs(char ch){
        for(int i=0;i<OPs.length;i++)
        {
            if(OPs[i]==ch)
                return true;
        }
        return false;
    }

    char FindOP(){
        char tmpa,tmpb;
        if(stack.size()==1){
            if(isOPs(stack.peek())){
                return  stack.peek();
            }
            else return 0;
        }
        else {
            if(isOPs(stack.peek())){
                return stack.peek();
            }
            else{
                tmpa=stack.pop();
                tmpb=stack.peek();
                stack.push(tmpa);
                return tmpb;
            }
        }
    }

    int Raction(){
        char tmpa,tmpb,tmpc;
        if(stack.peek()=='i'){
            System.out.println("R");
            stack.pop();
            stack.push('N');
        }
        else if(stack.peek()==')'){
            if(stack.size()<3){
                System.out.println("RE");
                return 0;
            }
            tmpa=stack.pop();
            tmpb=stack.pop();
            tmpc=stack.pop();
            if(tmpa==')'&&tmpb=='N'&&tmpc=='('){
                System.out.println("R");
                stack.push('N');
            }
            else {
                System.out.println("RE");
                return 0;
            }
        }
        else if(stack.peek()=='N'){
            if(stack.size()<3){
                System.out.println("RE");
                return 0;
            }
            tmpa=stack.pop();
            tmpb=stack.pop();
            tmpc=stack.pop();
            if(tmpa=='N'&&tmpb=='*'&&tmpc=='N'){
                System.out.println("R");
                stack.push('N');
            }
            else if(tmpa=='N'&&tmpb=='+'&&tmpc=='N'){
                System.out.println("R");
                stack.push('N');
            }
            else {
                System.out.println("RE");
                return 0;
            }
        }
        else {
            System.out.println("E");
            return 0;
        }
        return 1;
    }

    int Analyze(char[] chs)
    {
        char ch;
        char op;
        int mode;
        stack.push(chs[0]);//之后对入栈值进行优化
        if(chs[0]==')'){
            System.out.println("E");
            return 0;
        }
        System.out.println("I"+chs[0]);
        for(int i=1;i < chs.length;i++){
            ch=chs[i];
            if(ch == '\r'||ch == '\n'||ch=='\0'){
                break;
            }
            while (true){

                op=FindOP();
                if(op==0)
                    break;
                if(Relat(op,ch)==INF){
                    System.out.println("E");
                    return 0;
                }
                else if(Relat(op,ch)==-1||Relat(op,ch)==0){
                    break;
                }
                else {
                    mode=Raction();
                    if (mode==0)
                        return 0;
                }

            }
            if(op==0&&ch==')')
            {
                System.out.println("E");
                return 0;
            }
            System.out.println("I"+ch);
            stack.push(ch);
        }
        while (stack.size()>1||stack.peek()!='N'){
            op=FindOP();
            if(op=='('){
                System.out.println("E");
                return 0;
            }
            mode=Raction();
            if (mode==0)
                return 0;
        }

        return 0;
    }
}
