
#include <iostream>
#include <windows.h>
#include <conio.h>
#include <string>
#include <ctime>
 
using namespace std;
#define  MAX_NUM 1000    //�������ʤ�ı�־
#define  NO_BLANK -1001  //�˻�ʤ�ı�־
#define  TREE_DEPTH 3    //�ݹ����
#define  NIL 1001      //���ڵ�ĺ����߲�����ֵ
void show_meun();
class State  //����״̬�ڵ㣬һ��Stateʵ������һ�����̵�״̬�ڵ㣬�Ӷ��γ�һ����״�ṹ
{
public: 
	int QP[3][3]; //��ǰ��������
	int e_fun; //���ֽ�� 
	int child[9]; //��ǰ����״̬�µĺ�һ��������״̬�ڵ�
	int parent;  //��ǰ����״̬�µĸ�ĸ�ڵ��±�
	int bestChild;//��child[9]��e_fun���ŵĽڵ��±�
};
 
class Tic
{
public:
	int tmpQP[3][3]; //����3��ݹ����ʱ����
	
	static int s_count;//Ҷ�ӽڵ�ľ�̬����
	State States[MAX_NUM];//����״̬�ڵ�����
	
	Tic()
	{
 
	}
 
	void init() //��ʼ�����̣�������λ�õ����̶���Ϊ0
	{
	
		s_count=0;  
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				States[0].QP[i][j] = 0;
			} 
		States[0].parent = NIL;
	}
 
	void PrintQP()//���̽�����ʾ  
	{
		system("cls");
		cout<<endl<<endl<<endl;
		cout<<"          *****************һ����С��Ϸ*****************"<<endl<<endl;
		for(int i=0;i<3;i++)
		{
			cout<<"			";
			for(int j=0;j<3;j++)
			{	
				if(States[0].QP[i][j]==1)
					cout<<"��"<<'\t';
				else if(States[0].QP[i][j]==-1)
					cout<<"��"<<'\t';
				else
					cout<<"��"<<'\t';
			}
			if(i==1)
				cout<<"   ������	 ";
			if(i==2)
				cout<<"   ���ԣ���";
			cout<<endl;
			cout<<endl;
		}
	}
 
	int IsWin(State s) //�жϵ�ǰ������״̬�Ƿ������κ�һ����ʤ
	{
		int i;
		for(i=0;i<3;i++)
		{
			if(s.QP[i][0]==1&&s.QP[i][1]==1&&s.QP[i][2]==1)		return 1;
			if(s.QP[i][0]==-1&&s.QP[i][1]==-1&&s.QP[i][2]==-1)	return -1;
		}
		for(i=0;i<3;i++)
		{
			if(s.QP[0][i]==1&&s.QP[1][i]==1&&s.QP[2][i]==1)		return 1;
			if(s.QP[0][i]==-1&&s.QP[1][i]==-1&&s.QP[2][i]==-1)	return -1;
		}
		if((s.QP[0][0]==1&&s.QP[1][1]==1&&s.QP[2][2]==1)||(s.QP[2][0]==1&&s.QP[1][1]==1&&s.QP[0][2]==1))	return 1;
		if((s.QP[0][0]==-1&&s.QP[1][1]==-1&&s.QP[2][2]==-1)||(s.QP[2][0]==-1&&s.QP[1][1]==-1&&s.QP[0][2]==-1))	return -1;
		return 0;
	}
 
	int e_fun(State s)//���������ж����ۺ���
	{
		bool flag=true;
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(s.QP[i][j]==0) flag=false;
		if(flag)	return NO_BLANK;
		if(IsWin(s)==-1)	return -MAX_NUM;
		if(IsWin(s)==1)		return MAX_NUM;
		int count=0;
		for(i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(s.QP[i][j]==0)tmpQP[i][j]=1;
				else tmpQP[i][j]=s.QP[i][j];
		for(i=0;i<3;i++)
			count+=(tmpQP[i][0]+tmpQP[i][1]+tmpQP[i][2])/3;
		for(i=0;i<3;i++)
			count+=(tmpQP[0][i]+tmpQP[1][i]+tmpQP[2][i])/3;
 
		count+=(tmpQP[0][0]+tmpQP[1][1]+tmpQP[2][2])/3;
		count+=(tmpQP[2][0]+tmpQP[1][1]+tmpQP[0][2])/3;
 
 
		for( i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(s.QP[i][j]==0)tmpQP[i][j]=-1;
				else tmpQP[i][j]=s.QP[i][j];
 
 
		for(i=0;i<3;i++)
			count+=(tmpQP[i][0]+tmpQP[i][1]+tmpQP[i][2])/3;
 
		for(i=0;i<3;i++)
			count+=(tmpQP[0][i]+tmpQP[1][i]+tmpQP[2][i])/3;
 
		count+=(tmpQP[0][0]+tmpQP[1][1]+tmpQP[2][2])/3;
		count+=(tmpQP[2][0]+tmpQP[1][1]+tmpQP[0][2])/3;
		return count;
	}
 
	 bool AutoDone() 
	{
		return false;
	}
	void UserInput()//��ȡ�û�������
	{
		int pos,x,y;
		L1:cout<<"���������ӵ����� (xy)(�����1�е�2������12): "; 
		cin>>pos;
		if(cin.fail())
		{
			cin.clear();
			cin.ignore();
			cin.sync();
			system("cls");
			cout<<"���ݶ������"<<endl;
			PrintQP();
			goto L1;
		}
		x=pos/10,y=pos%10;
		if(x>0&&x<4&&y>0&&y<4&&States[0].QP[x-1][y-1]==0)
			States[0].QP[x-1][y-1]=-1;
		else
		{
			cout<<"���ݶ������";
			goto L1;
		}
	}
};
int Tic::s_count = 0;//��ʼ������״̬�ڵ��������տ�ʼ��Ϊ0
 
class demo : public Tic
{
public:
	demo()
	{
	
	}
	bool Judge()
	{
		int i,j,a=0;
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
				if(States[0].QP[i][j]==0) a++;
		if(a==0)
			return true;
		return  false; 
	}
	 bool AutoDone() 
	{ 
		int a,b,i,j,m,n,max,min,x,y;
	 
		if(IsWin(States[0])==-1)
		{ 
			cout<<"             ��ϲ����ʤ���ð�Ŷ������o(�Rv�Q)o~~  "<<endl<<endl;
			return true;system("pause");
		}		
		a=0,b=0;
		max=-10000;
		for(x=0;x<3;x++)
			for(y=0;y<3;y++)
				States[11].QP[x][y]=States[0].QP[x][y];
		for(i=0;i<3;i++)
			for(j=0;j<3;j++)
			{
				if(States[0].QP[i][j]==0)
				{	a=1;
					for(x=0;x<3;x++)
						for(y=0;y<3;y++)
							States[a].QP[x][y]=States[0].QP[x][y];
					States[a].QP[i][j]=1;
					min=10000;
					for(m=0;m<3;m++)
						for(n=0;n<3;n++)
						{  
							if(States[a].QP[m][n]==0)
							{	b=1;
								for(x=0;x<3;x++)
									for(y=0;y<3;y++)
										States[10].QP[x][y]=States[a].QP[x][y];	
								States[10].QP[m][n]=-1; 
								States[10].e_fun=e_fun(States[10]);
								if(States[10].e_fun<min) min=States[10].e_fun;         
							}
						}
					States[a].e_fun=min; 
					if(States[a].e_fun>max) 
					{	
						max=States[a].e_fun;
						for(x=0;x<3;x++)
							for(y=0;y<3;y++)
								States[11].QP[x][y]=States[a].QP[x][y];
					} 
				}	
			}
	 
		for(x=0;x<3;x++)
			for(y=0;y<3;y++)
				States[0].QP[x][y]=States[11].QP[x][y];			
	    	//���������
	    PrintQP();
		if(IsWin(States[0])==1)
		{
		  cout<<"            ��Ǹ�����ˣ��I(^��^)�J ����Ŷ��"<<endl<<endl;
			return true;system("pause");
		}
		else if(IsWin(States[0])==-1)
	 {
	   cout<<"               ��ϲ����ʤ���ð�Ŷ������o(�Rv�Q)o~~  "<<endl<<endl;
	     return true;system("pause");
	 }
	  return false;
		
	}
};
void show_meun()	//��ʾ���˵�
{
	system("cls");
	cout<<endl<<endl;
	cout<<"     ************************��ӭ����һ������Ϸ************************"<<endl;
	cout<<"     *                                                                *"<<endl;
	cout<<"     * ��Ϸ����:                                                      *"<<endl;
	cout<<"     *   1.���̹��Ÿ�����                                             *"<<endl;
	cout<<"     *   2.�С��С������Խ���������������ͬ�ַ�һ��Ϊʤ��             *"<<endl;
	cout<<"     *   3.��Ϊһ���������Ϊһ��������ʱ�ַ� ��������ʱ�ַ� ��   *"<<endl;
	cout<<"     *                                                                *"<<endl;
	cout<<"     *                                                                *"<<endl;
	cout<<"     *             1-��ʼ��Ϸ    |   0-������Ϸ                       *"<<endl; 
	cout<<"     *                                                                *"<<endl; 
	cout<<"     ******************************************************************"<<endl;
}
int main()  
{
	system("title #һ��������С��Ϸ");
	system("color b6");
    char IsFirst;
	bool IsFinish;
	int choice=-1;//�˵�ѡ��
	do
	{
		show_meun();
		cout<<endl<<"����������ѡ�����: ";
		cin>>choice;
		if(cin.fail())
		{
			
			cin.clear();
			cin.ignore();
			cin.sync();	
			system("cls"); 
			cout<<"���ݶ������ʹ���"<<endl;
			choice=-1;
			continue;
		}
		switch(choice)
		{
			case 0:	
				break;
			case 1:
				{
				cout<<"����Ϊ���֣�������'y'��������������:"<<endl;
				cin>>IsFirst;
				demo *p=new demo();
				p->init();
				cout<<"���̵ĳ�ʼ״̬:"<<endl;
				p->PrintQP();
				do
				{ 
					if(!p->Judge())
					{
						if(IsFirst=='y')
						{ 
							p->UserInput();p->PrintQP();
							if(!p->Judge())
								IsFinish=p->AutoDone();
						}
						else 
						{	 IsFinish=p->AutoDone();
							 if(!p->Judge())
							{	if(!IsFinish) 
								{p->UserInput();p->PrintQP();}}
							}
					} 	
					if(p->Judge()) IsFinish=true;
				}while (!IsFinish);
				if((p->IsWin(p->States[0])==0)&&p->Judge())
						cout<<"	    	ƽ�֡�����Ŭ����Ŷ����"<<endl;system("pause");
				break;
				}
			default:
				cout<<"���󣺲���ѡ�����������ѡ��"<<endl;
				break;
		}
	}while(choice!=0);
	return 0;
}
