

import java.util.*;
public class AStar8Num {
	List<Struct> open = new ArrayList<Struct>();//open��
	List<Struct> closed = new ArrayList<Struct>();//closed��
	List<Struct> spring = new ArrayList<Struct>();//spring��
	
	int start[] = new int[9];
	int target[] = new int[9];
	
	Struct structOfStart = new Struct();//��ʼ״̬
	Struct structOfTarget = new Struct();//Ŀ��״̬
	public void init()
	{
		int i = 0;
		System.out.println("�����ʼ״̬:");
		Scanner io = new Scanner(System.in);
		String s = io.nextLine();
		//�зֶ�ȡ��һ���ַ������Կո�Ϊ���
		String str[] = s.split(" ");
		for(String st : str)
		{
			if(!st.equals(""))
			{
				start[i++]=Integer.parseInt(st);
			}
		}
		System.out.println("����Ŀ��״̬:");
		Scanner io1 = new Scanner(System.in);
		String s1 = io1.nextLine();
		//�зֶ�ȡ��һ���ַ������Կո�Ϊ���
		String str1[] = s1.split(" ");
		i=0;//��ԭiֵ
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
		//��ʼ״̬
		
		for(i = 0;i<9;i++)
		{
			structOfStart.num[i]=start[i];
		}
		structOfStart.gvalue=0;
		structOfStart.hvalue=getHvalue(structOfStart);
		structOfStart.fvalue = structOfStart.gvalue+structOfStart.hvalue;
		structOfStart.parent=null;
		structOfStart.next=null;
		open.add(structOfStart);//��ʼ״̬����open����
		
		//Ŀ��״̬
		
		for(i=0;i<9;i++)
		{
			structOfTarget.num[i]=target[i];
		}
		structOfTarget.hvalue=getHvalue(structOfTarget);
	}
	
	//����ĳ��״̬��hֵ
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
	//��ĳ��״̬���뵽open���У���Ҫ���ǵݼ������
	public void add(Struct status , List<Struct> list)
	{
		list.add(status);
		//��Ҫ�����µıȽ���NewComparator
		Collections.sort(list, new NewComparator());
	}
	//��������Ƿ�����ͬ��״̬
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
	//����������Ƚ���Ƿ�����ͬ��״̬
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
			ancester = ancester.parent;//Ѱ�����Ƚ��
		}
		return flag;
	}
	//������b��ֵ���Ƹ�����a
	public void copySnumToTnum(int a[],int b[])
	{
		int len = b.length;
		for(int i = 0;i<len;i++)
		{
			a[i]=b[i];
		}
	}
	//�ƶ��������̽��
	public void getShift(Struct status,int index,int pos)
	{
		int medium = 0;//�н�ֵ
		Struct temp = new Struct();
		//temp.num = status.num;�����ǵ�ַ
		
		//���������ֵ
		copySnumToTnum(temp.num,status.num);
		//outputStatus(status);
		
		//����
		if(index==1)
		{
			//����λ��
			medium = temp.num[pos];
			temp.num[pos] = temp.num[pos-1];
			temp.num[pos-1] = medium;
			
			//����븸�����û����ͬ��״̬
			if(!hasAnceSameStatus(temp,status.parent))
			{
				temp.gvalue = status.gvalue+1;
				temp.hvalue = getHvalue(temp);
				temp.fvalue = temp.gvalue+temp.hvalue;
				temp.parent = status;
				temp.next=null;
				//����spring����
				spring.add(0,temp);
			}
			
		}
		//����
		else if(index==2)
		{
			//����λ��
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
				//����spring����
				spring.add(0,temp);
			}
		}
		//����
		else if(index==3)
		{
			//����λ��
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
				//����spring����
				spring.add(0,temp);
			}
		}
		//����
		else
		{
			//����λ��
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
				//����spring����
				spring.add(0,temp);
			}
		}
	}
	//������̽��
	public void getNexts(Struct status)
	{
		int pos = 0;
		int i;
		//�ҵ��ո�λ��
		for(i=0;i<9;i++)
		{
			if(status.num[i]==0)
			{
				pos=i;
				break;
			}
		}
		//����
		if(pos%3!=0)
		{
			getShift(status,1,pos);
		}
		//����
		if(pos>2)
		{
			getShift(status, 2, pos);
		}
		//����
		if(pos%3!=2)
		{
			getShift(status, 3, pos);
		}
		//����
		if(pos<6)
		{
			getShift(status, 4, pos);
		}
	}
	//�õ�·��
	public void getPath(Struct status)
	{
		int deepnum = status.gvalue;
		if(status.parent!=null)
		{
			getPath(status.parent);
		}
		System.out.println("��"+deepnum+"��״̬Ϊ:");
		deepnum--;
		outputStatus(status);
	}
	
	//���״̬
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
	//�ж��Ƿ��ܽ��
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
		//�ܲ��ܽ��
		if(!icansolve())
		{
			System.out.println("���ܽ������");
			System.exit(0);
		}
			
		System.out.println("�ӱ����ó��Ľ���״̬����Ӧ��ֵ:");
		while(!open.isEmpty())
		{
			getOfOpen = open.get(0);
			closed.add(getOfOpen);
			open.remove(0);//��ȥ���뵽closed���еĽ��
			
			System.out.println("��"+numcount+++"��״̬��:");
			outputStatus(getOfOpen);
			System.out.println("��fֵΪ:"+getOfOpen.fvalue);
			System.out.println("��gֵΪ:"+getOfOpen.gvalue);
			System.out.println("��hֵΪ:"+getOfOpen.hvalue);
			if(hasSameStatus(getOfOpen,structOfTarget))
			{
				flag = true;
				break;
			}
			getNexts(getOfOpen);//������̽��	
			
			while(!spring.isEmpty())
			{
				//�õ�spring���еĽ��
				Struct struct = spring.get(0);
				if(open.contains(struct))
				{
					//�õ�open������ͬ�Ľ��,ע��������д��equals��hashcode����
					Struct structInOpen = open.get(open.indexOf(struct));
					//�ı�open���нڵ��parentָ�뼰��ز���
					if(struct.gvalue<structInOpen.gvalue)
					{
						structInOpen.parent = struct.parent;
						structInOpen.fvalue = struct.fvalue;
						structInOpen.gvalue = struct.gvalue;
						//�������ǲ���Ӧ����������open������������
						
						Collections.sort(open, new NewComparator());
						
						
					}
					//ɾ��spring���еĸýڵ�
					spring.remove(struct);
				}
				else if(closed.contains(struct))
				{
					//�õ�closed������ͬ�Ľ��,ע��������д��equals��hashcode����
					Struct structInClosed = closed.get(closed.indexOf(struct));
					//�ı�closed���нڵ��parentָ�뼰��ز���
					if(struct.gvalue<structInClosed.gvalue)
					{
						structInClosed.parent = struct.parent;
						structInClosed.fvalue = struct.fvalue;
						structInClosed.gvalue = struct.gvalue;
						//������open����
						add(structInClosed,open);
					}
					//ɾ��spring���еĸýڵ�
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
			System.out.println("·������Ϊ:"+getOfOpen.gvalue);
			getPath(getOfOpen);
		}
			
		
	}
	public static void main(String[] args) {
		new AStar8Num().reslove();
	}
}

