

import java.util.*;
public class AStar8Num {
	List<Struct> open = new ArrayList<Struct>();//open表
	List<Struct> closed = new ArrayList<Struct>();//closed表
	List<Struct> spring = new ArrayList<Struct>();//spring表
	
	int start[] = new int[9];
	int target[] = new int[9];
	
	Struct structOfStart = new Struct();//初始状态
	Struct structOfTarget = new Struct();//目标状态
	public void init()
	{
		int i = 0;
		System.out.println("输入初始状态:");
		Scanner io = new Scanner(System.in);
		String s = io.nextLine();
		//切分读取的一行字符串，以空格为标杆
		String str[] = s.split(" ");
		for(String st : str)
		{
			if(!st.equals(""))
			{
				start[i++]=Integer.parseInt(st);
			}
		}
		System.out.println("输入目标状态:");
		Scanner io1 = new Scanner(System.in);
		String s1 = io1.nextLine();
		//切分读取的一行字符串，以空格为标杆
		String str1[] = s1.split(" ");
		i=0;//还原i值
		for(String st : str1)
		{
			if(!st.equals(""))
			{
				target[i++]=Integer.parseInt(st);
			}
		}
/*		for(i=0;i<9;i++)
		{
			System.out.print(start[i]+" ");
		}*/
		//初始状态
		
		for(i = 0;i<9;i++)
		{
			structOfStart.num[i]=start[i];
		}
		structOfStart.gvalue=0;
		structOfStart.hvalue=getHvalue(structOfStart);
		structOfStart.fvalue = structOfStart.gvalue+structOfStart.hvalue;
		structOfStart.parent=null;
		structOfStart.next=null;
		open.add(structOfStart);//初始状态加入open表中
		
		//目标状态
		
		for(i=0;i<9;i++)
		{
			structOfTarget.num[i]=target[i];
		}
		structOfTarget.hvalue=getHvalue(structOfTarget);
	}
	
	//计算某个状态的h值
	public int getHvalue(Struct status)
	{
		int i,num=0;
		for(i=0;i<9;i++)
		{
			if(status.num[i]!=target[i])
				num++;
		}
		status.hvalue = num;
		return status.hvalue;
	}
	//将某个状态加入到open表中，需要按非递减排序的
	public void add(Struct status , List<Struct> list)
	{
		list.add(status);
		//需要构造新的比较器NewComparator
		Collections.sort(list, new NewComparator());
	}
	//两个结点是否有相同的状态
	public Boolean hasSameStatus(Struct s1,Struct s2)
	{
		boolean flag = true;
		for(int i =0 ;i<9;i++)
		{
			if(s1.num[i]!=s2.num[i])
				flag = false;
		}
		return flag;
	}
	//结点与其祖先结点是否有相同的状态
	public Boolean hasAnceSameStatus(Struct origin,Struct ancester)
	{
		boolean flag = false;
		while(ancester!=null)
		{
			if(hasSameStatus(origin,ancester))
			{
				flag=true;
				return flag;
			}
			ancester = ancester.parent;//寻找祖先结点
		}
		return flag;
	}
	//把数组b的值复制给数组a
	public void copySnumToTnum(int a[],int b[])
	{
		int len = b.length;
		for(int i = 0;i<len;i++)
		{
			a[i]=b[i];
		}
	}
	//移动后产生后继结点
	public void getShift(Struct status,int index,int pos)
	{
		int medium = 0;//中介值
		Struct temp = new Struct();
		//temp.num = status.num;传的是地址
		
		//复制数组的值
		copySnumToTnum(temp.num,status.num);
		//outputStatus(status);
		
		//右移
		if(index==1)
		{
			//交换位置
			medium = temp.num[pos];
			temp.num[pos] = temp.num[pos-1];
			temp.num[pos-1] = medium;
			
			//如果与父辈结点没有相同的状态
			if(!hasAnceSameStatus(temp,status.parent))
			{
				temp.gvalue = status.gvalue+1;
				temp.hvalue = getHvalue(temp);
				temp.fvalue = temp.gvalue+temp.hvalue;
				temp.parent = status;
				temp.next=null;
				//加入spring表中
				spring.add(0,temp);
			}
			
		}
		//下移
		else if(index==2)
		{
			//交换位置
			medium = temp.num[pos];
			temp.num[pos] = temp.num[pos-3];
			temp.num[pos-3] = medium;
			
			if(!hasAnceSameStatus(temp,status.parent))
			{
				temp.gvalue = status.gvalue+1;
				temp.hvalue = getHvalue(temp);
				temp.fvalue = temp.gvalue+temp.hvalue;
				temp.parent = status;
				temp.next=null;
				//加入spring表中
				spring.add(0,temp);
			}
		}
		//左移
		else if(index==3)
		{
			//交换位置
			medium = temp.num[pos];
			temp.num[pos] = temp.num[pos+1];
			temp.num[pos+1] = medium;
			if(!hasAnceSameStatus(temp,status.parent))
			{
				temp.gvalue = status.gvalue+1;
				temp.hvalue = getHvalue(temp);
				temp.fvalue = temp.gvalue+temp.hvalue;
				temp.parent = status;
				temp.next=null;
				//加入spring表中
				spring.add(0,temp);
			}
		}
		//上移
		else
		{
			//交换位置
			medium = temp.num[pos];
			temp.num[pos] = temp.num[pos+3];
			temp.num[pos+3] = medium;
			if(!hasAnceSameStatus(temp,status.parent))
			{
				temp.gvalue = status.gvalue+1;
				temp.hvalue = getHvalue(temp);
				temp.fvalue = temp.gvalue+temp.hvalue;
				temp.parent = status;
				temp.next=null;
				//加入spring表中
				spring.add(0,temp);
			}
		}
	}
	//产生后继结点
	public void getNexts(Struct status)
	{
		int pos = 0;
		int i;
		//找到空格位置
		for(i=0;i<9;i++)
		{
			if(status.num[i]==0)
			{
				pos=i;
				break;
			}
		}
		//右移
		if(pos%3!=0)
		{
			getShift(status,1,pos);
		}
		//下移
		if(pos>2)
		{
			getShift(status, 2, pos);
		}
		//左移
		if(pos%3!=2)
		{
			getShift(status, 3, pos);
		}
		//上移
		if(pos<6)
		{
			getShift(status, 4, pos);
		}
	}
	//得到路径
	public void getPath(Struct status)
	{
		int deepnum = status.gvalue;
		if(status.parent!=null)
		{
			getPath(status.parent);
		}
		System.out.println("第"+deepnum+"层状态为:");
		deepnum--;
		outputStatus(status);
	}
	
	//输出状态
	public void outputStatus(Struct status)
	{
		for(int i = 0;i<status.num.length;i++)
		{
			if(i%3==0)
				System.out.println();
			System.out.print(status.num[i]+" ");
		}
		System.out.println();
	}
	//判断是否能解决
	public Boolean icansolve()
	{
		boolean flag = false;
		int i ,j;
		int resultOfStart=0;
		int resultOfTarget = 0;
		for(i=0;i<9;i++)
		{
			for(j=0;j<i;j++)
			{
				if(start[j]<start[i]&&start[j]!=0)
					resultOfStart++;
				if(target[j]<target[i]&&target[j]!=0)
					resultOfTarget++;
			}
		}
		//System.out.println(resultOfStart);
		//System.out.println(resultOfTarget);
		if((resultOfStart+resultOfTarget)%2==0)
			flag=true;
		return flag;
	}
	public void reslove()
	{
		int numcount = 1;
		Struct getOfOpen = null;
		boolean flag = false;
		init();
		//能不能解决
		if(!icansolve())
		{
			System.out.println("不能解决！！");
			System.exit(0);
		}
			
		System.out.println("从表中拿出的结点的状态及相应的值:");
		while(!open.isEmpty())
		{
			getOfOpen = open.get(0);
			closed.add(getOfOpen);
			open.remove(0);//移去加入到closed表中的结点
			
			System.out.println("第"+numcount+++"个状态是:");
			outputStatus(getOfOpen);
			System.out.println("其f值为:"+getOfOpen.fvalue);
			System.out.println("其g值为:"+getOfOpen.gvalue);
			System.out.println("其h值为:"+getOfOpen.hvalue);
			if(hasSameStatus(getOfOpen,structOfTarget))
			{
				flag = true;
				break;
			}
			getNexts(getOfOpen);//产生后继结点	
			
			while(!spring.isEmpty())
			{
				//得到spring表中的结点
				Struct struct = spring.get(0);
				if(open.contains(struct))
				{
					//得到open表中相同的结点,注意这里重写了equals和hashcode方法
					Struct structInOpen = open.get(open.indexOf(struct));
					//改变open表中节点的parent指针及相关参数
					if(struct.gvalue<structInOpen.gvalue)
					{
						structInOpen.parent = struct.parent;
						structInOpen.fvalue = struct.fvalue;
						structInOpen.gvalue = struct.gvalue;
						//在这里是不是应该重新排序open表？？？？？？
						
						Collections.sort(open, new NewComparator());
						
						
					}
					//删除spring表中的该节点
					spring.remove(struct);
				}
				else if(closed.contains(struct))
				{
					//得到closed表中相同的结点,注意这里重写了equals和hashcode方法
					Struct structInClosed = closed.get(closed.indexOf(struct));
					//改变closed表中节点的parent指针及相关参数
					if(struct.gvalue<structInClosed.gvalue)
					{
						structInClosed.parent = struct.parent;
						structInClosed.fvalue = struct.fvalue;
						structInClosed.gvalue = struct.gvalue;
						//加入至open表中
						add(structInClosed,open);
					}
					//删除spring表中的该节点
					spring.remove(struct);
				}
				else
				{
					add(struct,open);
					spring.remove(struct);
				}
			}
		}
		if(flag)
		{
			System.out.println("*************************************");
			System.out.println("路径长度为:"+getOfOpen.gvalue);
			getPath(getOfOpen);
		}
			
		
	}
	public static void main(String[] args) {
		new AStar8Num().reslove();
	}
}

