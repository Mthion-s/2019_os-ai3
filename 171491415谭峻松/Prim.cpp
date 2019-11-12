#include<stdio.h>
#define OK 1
#define ERROR 0
#define Max_Int 32767
#define MVNum 100
 
typedef int Status;
typedef char VerTexType;
typedef int ArcType;
 
struct{
	VerTexType adjvex;
	ArcType lowcost;
}closedge[MVNum];
 
typedef struct{
	VerTexType vex[MVNum];
	ArcType arc[MVNum][MVNum];
	int vexnum, arcnum;
}AMGraph;

int LocateVex(AMGraph *G,VerTexType v)
{
	int i;
	for (i = 0; i < G->vexnum; i++)
	{
		if (G->vex[i] == v)
			return i;
	}
	return -1;
}
 
Status CreateUDN(AMGraph *G)
{
	int i, j, k;
	VerTexType v1, v2;
	ArcType w;
	printf("输入总节点数和总边数：");
	scanf("%d %d", &G->vexnum, &G->arcnum);
	fflush(stdin);
	printf("输入各个节点的值：");
	for (i = 0; i < G->vexnum; i++)
		scanf("%c", &G->vex[i]);
	for (i = 0; i < G->vexnum;i++)
	for (j = 0; j < G->vexnum; j++)
	{
		G->arc[i][j] = Max_Int;
	}
	for (k = 0; k < G->arcnum; k++)
	{
		fflush(stdin);
		printf("输入一条边的两个顶点以及该边的权值：");
		scanf("%c %c %d", &v1, &v2, &w);
		i = LocateVex(G, v1);
		j = LocateVex(G, v2);
		G->arc[i][j] = w;
		G->arc[j][i] = G->arc[i][j];
	}
	return OK;
}


int Min(AMGraph G)
{
	int i;
	int min = Max_Int;
	int index = -1;
	for (i = 0; i < G.vexnum; i++)
	{
		if (min>closedge[i].lowcost&&closedge[i].lowcost!=0)
		{
			min = closedge[i].lowcost;
			index = i;
		}
	}
	return index;
}


void MinSpanTree_Prim(AMGraph G, VerTexType v)
{
	int i, j, k;
	VerTexType u0, v0;
	k = LocateVex(&G, v);
	for (j = 0; j < G.vexnum; j++)
	{
		if (j != k)
		{
			closedge[j].adjvex = v;
			closedge[j].lowcost = G.arc[k][j];
		}
	}
	closedge[k].lowcost = 0;
	for (i = 1; i < G.vexnum; i++)
	{
		k = Min(G);
		u0 = G.vex[k];
		v0 = closedge[k].adjvex;
		printf("%c->%c\n", v0, u0);
		closedge[k].lowcost = 0;
		for (j = 0; j < G.vexnum; j++)
		{
			if (closedge[j].lowcost>G.arc[k][j])
			{
				closedge[j].adjvex = G.vex[k];
				closedge[j].lowcost = G.arc[k][j];
			}
		}
	}
}



int main(void)
{
	AMGraph G;
	CreateUDN(&G);
	MinSpanTree_Prim(G, '1');
	printf("\n");
	return 0;
}

